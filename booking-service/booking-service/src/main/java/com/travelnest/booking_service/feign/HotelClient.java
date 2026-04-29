package com.travelnest.booking_service.feign;


import com.travelnest.booking_service.dto.RoomDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

// "hotel-service" must match spring.application.name in hotel-service
@FeignClient(name = "hotel-service",
        fallback = HotelClientFallback.class)
public interface HotelClient {

    // Get room details from hotel service
    @GetMapping("/api/hotels/rooms/{roomId}")
    RoomDto getRoomById(@PathVariable Long roomId);

    // Update room availability after booking
    @PutMapping("/api/hotels/rooms/{roomId}/availability")
    RoomDto updateRoomAvailability(@PathVariable Long roomId,
                                   @RequestParam Boolean available);
}
