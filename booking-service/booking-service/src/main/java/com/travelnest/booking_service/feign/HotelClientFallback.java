package com.travelnest.booking_service.feign;

import com.travelnest.booking_service.dto.RoomDto;
import org.springframework.stereotype.Component;

@Component
public class HotelClientFallback implements HotelClient {

    @Override
    public RoomDto getRoomById(Long roomId) {
        // Hotel service is down — return empty room
        return null;
    }

    @Override
    public RoomDto updateRoomAvailability(Long roomId, Boolean available) {
        // Hotel service is down — return null
        return null;
    }
}
