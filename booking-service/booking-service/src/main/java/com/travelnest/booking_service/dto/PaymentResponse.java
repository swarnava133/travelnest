package com.travelnest.booking_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponse {
    private Long id;
    private String status;        // "SUCCESS" or "FAILED"
    private Double amount;
    private String transactionId;
    private LocalDateTime paymentDate;
}
