package com.travelnest.notification_service.service;


import com.travelnest.notification_service.dto.BookingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    //  Send booking confirmation email
    public void sendBookingConfirmation(BookingEvent event) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            // Who receives the email
            message.setTo(event.getUserEmail());

            // Email subject based on status
            if ("CONFIRMED".equals(event.getStatus())) {
                message.setSubject("🏨 Booking Confirmed — TravelNest");
            } else {
                message.setSubject("❌ Booking Cancelled — TravelNest");
            }

            // Email body
            message.setText(buildEmailBody(event));

            // Send!
            mailSender.send(message);

            System.out.println("✅ Email sent to: " + event.getUserEmail());

        } catch (Exception e) {
            // Don't crash if email fails — just log it
            System.out.println("❌ Email failed: " + e.getMessage());
        }
    }

    //  Build email body text
    private String buildEmailBody(BookingEvent event) {

        if ("CONFIRMED".equals(event.getStatus())) {
            return """
                Dear Guest,
                
                Your booking has been CONFIRMED! 🎉
                
                Booking Details:
                ─────────────────────────────
                Hotel      : %s
                Room       : %s
                Check-in   : %s
                Check-out  : %s
                Total Paid : ₹%.2f
                ─────────────────────────────
                
                Thank you for choosing TravelNest!
                We look forward to welcoming you.
                
                Best regards,
                TravelNest Team
                """.formatted(
                    event.getHotelName(),
                    event.getRoomNumber(),
                    event.getCheckInDate(),
                    event.getCheckOutDate(),
                    event.getTotalAmount()
            );
        } else {
            return """
                Dear Guest,
                
                Your booking has been CANCELLED.
                
                Cancelled Booking:
                ─────────────────────────────
                Hotel      : %s
                Room       : %s
                Check-in   : %s
                Check-out  : %s
                Amount     : ₹%.2f
                ─────────────────────────────
                
                If you have any questions please contact us.
                
                Best regards,
                TravelNest Team
                """.formatted(
                    event.getHotelName(),
                    event.getRoomNumber(),
                    event.getCheckInDate(),
                    event.getCheckOutDate(),
                    event.getTotalAmount()
            );
        }
    }
}
