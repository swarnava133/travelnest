package com.travelnest.hotel_service.dto;


import lombok.Data;

@Data
public class HotelRequest {
    private String name;
    private String city;
    private String address;
    private String description;
    private String imageUrl;
    private Double rating;
}
