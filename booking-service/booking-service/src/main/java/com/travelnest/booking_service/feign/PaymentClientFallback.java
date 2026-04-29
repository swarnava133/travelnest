package com.travelnest.booking_service.feign;


import com.travelnest.booking_service.dto.PaymentRequest;
import com.travelnest.booking_service.dto.PaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentClientFallback implements PaymentClient {

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        // Payment service is down — return a safe fallback response
        PaymentResponse fallback = new PaymentResponse();
        fallback.setStatus("FAILED");
        fallback.setAmount(request.getAmount());
        return fallback;
    }
}