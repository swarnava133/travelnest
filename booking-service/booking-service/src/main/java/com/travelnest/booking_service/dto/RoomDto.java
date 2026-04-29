package com.travelnest.booking_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// Ignore any extra fields from hotel-service response
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomDto {
    private Long id;
    private String roomNumber;
    private String roomType;
    private Double pricePerNight;
    private Integer capacity;
    private String description;

    // Fix Boolean field name mismatch
    @JsonProperty("isAvailable")
    private Boolean isAvailable;
}
