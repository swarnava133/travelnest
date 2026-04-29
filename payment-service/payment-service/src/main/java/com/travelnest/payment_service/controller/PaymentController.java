package com.travelnest.payment_service.controller;


import com.travelnest.payment_service.dto.PaymentRequest;
import com.travelnest.payment_service.dto.PaymentResponse;
import com.travelnest.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // POST process payment
    // http://localhost:8080/api/payments/process
    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(
            @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.processPayment(request));
    }

    // GET payment by id
    // http://localhost:8080/api/payments/1
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(
            @PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    // GET payments by booking
    // http://localhost:8080/api/payments/booking/1
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByBooking(
            @PathVariable Long bookingId) {
        return ResponseEntity.ok(
                paymentService.getPaymentsByBooking(bookingId)
        );
    }

    // GET payments by user email
    // http://localhost:8080/api/payments/user/swarnava@gmail.com
    @GetMapping("/user/{email}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByUser(
            @PathVariable String email) {
        return ResponseEntity.ok(paymentService.getPaymentsByUser(email));
    }

    // PUT refund payment
    // http://localhost:8080/api/payments/1/refund
    @PutMapping("/{id}/refund")
    public ResponseEntity<PaymentResponse> refundPayment(
            @PathVariable Long id) {
        return ResponseEntity.ok(paymentService.refundPayment(id));
    }

    // GET health check
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Payment Service is running!");
    }
}
