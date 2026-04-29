package com.travelnest.booking_service.kafka;

import com.travelnest.booking_service.dto.BookingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventProducer {

    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    // Kafka topic name — notification service listens to this
    private static final String TOPIC = "booking-confirmed";

    public void sendBookingEvent(BookingEvent event) {
        // Send event to Kafka topic
        kafkaTemplate.send(TOPIC, event);
        System.out.println("📨 Booking event sent to Kafka: " + event);
    }
}
