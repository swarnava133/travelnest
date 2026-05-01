package com.travelnest.booking_service.service;

import com.travelnest.booking_service.dto.*;
import com.travelnest.booking_service.feign.HotelClient;
import com.travelnest.booking_service.feign.PaymentClient;
import com.travelnest.booking_service.kafka.BookingEventProducer;
import com.travelnest.booking_service.model.Booking;
import com.travelnest.booking_service.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final HotelClient hotelClient;
    private final PaymentClient paymentClient;
    private final BookingEventProducer bookingEventProducer;

    //  CREATE BOOKING — main flow
    public Booking createBooking(BookingRequest request) {

        // Step 1 — Get room details from Hotel Service via Feign
        RoomDto room = hotelClient.getRoomById(request.getRoomId());

        // Step 2 — Check if room exists and is available
        if (room == null) {
            throw new RuntimeException(
                    "Room not found or hotel service unavailable!"
            );
        }
        if (!room.getIsAvailable()) {
            throw new RuntimeException("Room is not available!");
        }

        // Step 3 — Calculate total amount
        // nights = checkOut - checkIn
        long nights = ChronoUnit.DAYS.between(
                request.getCheckInDate(),
                request.getCheckOutDate()
        );
        Double totalAmount = room.getPricePerNight() * nights;

        // Step 4 — Create booking with PENDING status
        Booking booking = new Booking();
        booking.setUserEmail(request.getUserEmail());
        booking.setHotelId(request.getHotelId());
        booking.setRoomId(request.getRoomId());
        booking.setRoomNumber(room.getRoomNumber());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setNumberOfGuests(request.getNumberOfGuests());
        booking.setTotalAmount(totalAmount);
        booking.setStatus("PENDING");
        booking.setBookedAt(LocalDateTime.now());

        // Get hotel name from hotel service
        booking.setHotelName("Hotel " + request.getHotelId());

        // Save pending booking first
        Booking savedBooking = bookingRepository.save(booking);

        // Step 5 — Process payment via Payment Service (with Circuit Breaker)
        PaymentRequest paymentRequest = new PaymentRequest(
                request.getUserEmail(),
                totalAmount,
                "BOOKING-" + savedBooking.getId()
        );
        PaymentResponse paymentResponse =
                paymentClient.processPayment(paymentRequest);

        // Step 6 — Check payment result
        if ("SUCCESS".equals(paymentResponse.getStatus())) {

            // Payment succeeded → confirm booking
            savedBooking.setStatus("CONFIRMED");
            savedBooking.setPaymentId(paymentResponse.getId());
            bookingRepository.save(savedBooking);

            // Step 7 — Update room availability → mark as NOT available
            hotelClient.updateRoomAvailability(request.getRoomId(), false);

            // Step 8 — Send Kafka event → Notification Service sends email
            BookingEvent event = new BookingEvent(
                    booking.getUserEmail(),
                    booking.getHotelName(),
                    room.getRoomNumber(),
                    request.getCheckInDate().toString(),
                    request.getCheckOutDate().toString(),
                    totalAmount,
                    "CONFIRMED"
            );
            bookingEventProducer.sendBookingEvent(event);

        } else {
            // Payment failed → cancel booking
            savedBooking.setStatus("CANCELLED");
            bookingRepository.save(savedBooking);
            throw new RuntimeException(
                    "Payment failed! Please try again later."
            );
        }

        return savedBooking;
    }

    // 📋 Get all bookings for a user
    public List<Booking> getUserBookings(String userEmail) {
        return bookingRepository.findByUserEmail(userEmail);
    }

    // 📋 Get booking by id
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found: " + id));
    }

    // ❌ Cancel booking
    public Booking cancelBooking(Long bookingId) {

        Booking booking = getBookingById(bookingId);

        if ("CANCELLED".equals(booking.getStatus())) {
            throw new RuntimeException("Booking already cancelled!");
        }

        // Update booking status
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);

        // Make room available again
        hotelClient.updateRoomAvailability(booking.getRoomId(), true);

        // Send cancellation event to Kafka
        BookingEvent event = new BookingEvent(
                booking.getUserEmail(),
                booking.getHotelName(),
                booking.getRoomNumber(),
                booking.getCheckInDate().toString(),
                booking.getCheckOutDate().toString(),
                booking.getTotalAmount(),
                "CANCELLED"
        );
        bookingEventProducer.sendBookingEvent(event);

        return booking;
    }
}
