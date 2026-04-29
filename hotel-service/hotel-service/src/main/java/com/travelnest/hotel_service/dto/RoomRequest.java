package com.travelnest.hotel_service.dto;
import lombok.Data;

@Data
public class RoomRequest {
    private String roomNumber;
    private String roomType;
    private Double pricePerNight;
    private Integer capacity;
    private String description;
}
