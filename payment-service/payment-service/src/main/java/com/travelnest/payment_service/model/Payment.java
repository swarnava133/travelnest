package com.travelnest.payment_service.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bookingId;           // which booking this payment is for

    @Column(nullable = false)
    private String userEmail;         // who paid

    @Column(nullable = false)
    private Double amount;            // how much

    @Column(nullable = false)
    private String paymentMethod;     // "CREDIT_CARD", "DEBIT_CARD", "UPI", "NET_BANKING"

    @Column(nullable = false)
    private String status;            // "PENDING", "SUCCESS", "FAILED", "REFUNDED"

    private String transactionId;     // unique transaction reference

    @Column(nullable = false)
    private LocalDateTime paymentDate; // when payment was made
}
