package com.travelnest.payment_service.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long bookingId;
    private String userEmail;
    private Double amount;
    private String paymentMethod;   // "CREDIT_CARD", "UPI" etc.
}
