package com.travelnest.booking_service.feign;

import com.travelnest.booking_service.dto.PaymentRequest;
import com.travelnest.booking_service.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service",
        fallback = PaymentClientFallback.class)
public interface PaymentClient {

    @PostMapping("/api/payments/process")
    PaymentResponse processPayment(@RequestBody PaymentRequest request);
}