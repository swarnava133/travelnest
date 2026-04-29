package com.travelnest.payment_service.service;


import com.travelnest.payment_service.dto.PaymentRequest;
import com.travelnest.payment_service.dto.PaymentResponse;
import com.travelnest.payment_service.model.Payment;
import com.travelnest.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // 💳 Process a new payment
    public PaymentResponse processPayment(PaymentRequest request) {

        // Create payment record
        Payment payment = new Payment();
       // **payment.setBookingId(request.getBookingId());
        payment.setUserEmail(request.getUserEmail());
        payment.setAmount(request.getAmount());

        //**change
        payment.setBookingId(0L);   // temporary default
        payment.setUserEmail(request.getUserEmail());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(
                request.getPaymentMethod() != null ?
                        request.getPaymentMethod() : "ONLINE"   // default if not provided
        );


       //** payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentDate(LocalDateTime.now());

        // Generate unique transaction ID
        // UUID = universally unique identifier like "a3f4-b2c1-..."
        payment.setTransactionId(UUID.randomUUID().toString());

        // Mock payment processing
        // In real world → call Razorpay/Stripe API here
        // For now → always mark as SUCCESS
        payment.setStatus("SUCCESS");

        Payment saved = paymentRepository.save(payment);
        return mapToResponse(saved);
    }

    // 🔍 Get payment by id
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found with id: " + id));
        return mapToResponse(payment);
    }

    // 🔍 Get all payments for a booking
    public List<PaymentResponse> getPaymentsByBooking(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .stream()
                .map(this::mapToResponse)   // convert each Payment → PaymentResponse
                .collect(Collectors.toList());
    }

    // 🔍 Get all payments by user
    public List<PaymentResponse> getPaymentsByUser(String userEmail) {
        return paymentRepository.findByUserEmail(userEmail)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 💰 Refund payment
    public PaymentResponse refundPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found with id: " + paymentId));

        // Check if payment can be refunded
        if (!payment.getStatus().equals("SUCCESS")) {
            throw new RuntimeException(
                    "Only successful payments can be refunded!"
            );
        }

        payment.setStatus("REFUNDED");
        Payment saved = paymentRepository.save(payment);
        return mapToResponse(saved);
    }

    // 🔄 Helper method — convert Payment entity to PaymentResponse DTO
    // "this::mapToResponse" in stream above calls this method
    private PaymentResponse mapToResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getBookingId(),
                payment.getUserEmail(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getTransactionId(),
                payment.getPaymentDate()
        );
    }
}
