package com.travelnest.booking_service.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingRequest {
    private Long hotelId;
    private Long roomId;
    private String userEmail;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfGuests;
}
