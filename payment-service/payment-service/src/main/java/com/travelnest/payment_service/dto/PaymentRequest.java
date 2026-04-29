package com.travelnest.payment_service.dto;


import lombok.Data;

@Data
public class PaymentRequest {
    private String userEmail;
    private Double amount;
    private String bookingReference;   // ← changed from bookingId
    private String paymentMethod;      // ← make optional with default
}
