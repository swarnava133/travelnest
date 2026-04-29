package com.travelnest.payment_service.repository;

import com.travelnest.payment_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find all payments for a specific booking
    List<Payment> findByBookingId(Long bookingId);

    // Find all payments by a specific user
    List<Payment> findByUserEmail(String userEmail);

    // Find payment by transaction id
    Optional<Payment> findByTransactionId(String transactionId);
}