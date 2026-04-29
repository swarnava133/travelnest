package com.travelnest.booking_service.controller;


import com.travelnest.booking_service.dto.BookingRequest;
import com.travelnest.booking_service.model.Booking;
import com.travelnest.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // POST create booking
    // http://localhost:8080/api/bookings
    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    // GET all bookings for a user
    // http://localhost:8080/api/bookings/user/swarnava@gmail.com
    @GetMapping("/user/{email}")
    public ResponseEntity<List<Booking>> getUserBookings(
            @PathVariable String email) {
        return ResponseEntity.ok(bookingService.getUserBookings(email));
    }

    // GET booking by id
    // http://localhost:8080/api/bookings/1
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    // PUT cancel booking
    // http://localhost:8080/api/bookings/1/cancel
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    // GET health check
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Booking Service is running!");
    }
}