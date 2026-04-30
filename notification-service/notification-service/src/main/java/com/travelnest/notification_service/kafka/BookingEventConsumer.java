package com.travelnest.notification_service.kafka;

import com.travelnest.notification_service.dto.BookingEvent;
import com.travelnest.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventConsumer {

    private final EmailService emailService;

    // 🎧 Listen to "booking-confirmed" topic
    // Must match EXACTLY what booking-service publishes to!
    @KafkaListener(
            topics = "booking-confirmed",
            groupId = "notification-group"
    )
    public void consumeBookingEvent(BookingEvent event) {

        System.out.println("📨 Received booking event: " + event);

        // Send email based on booking status
        emailService.sendBookingConfirmation(event);
    }
}
