package com.travelnest.notification_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingEvent {
    private String userEmail;
    private String hotelName;
    private String roomNumber;
    private String checkInDate;
    private String checkOutDate;
    private Double totalAmount;
    private String status;        // "CONFIRMED" or "CANCELLED"
}