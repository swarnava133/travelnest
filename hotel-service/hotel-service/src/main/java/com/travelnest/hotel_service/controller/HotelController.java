package com.travelnest.hotel_service.controller;

import com.travelnest.hotel_service.dto.HotelRequest;
import com.travelnest.hotel_service.dto.RoomRequest;
import com.travelnest.hotel_service.model.Hotel;
import com.travelnest.hotel_service.model.Room;
import com.travelnest.hotel_service.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    // GET all hotels
    // http://localhost:8080/api/hotels
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    // GET hotel by id
    // http://localhost:8080/api/hotels/1
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    // GET hotels by city
    // http://localhost:8080/api/hotels/city/Kolkata
    @GetMapping("/city/{city}")
    public ResponseEntity<List<Hotel>> getHotelsByCity(
            @PathVariable String city) {
        return ResponseEntity.ok(hotelService.getHotelsByCity(city));
    }

    // POST add new hotel
    // http://localhost:8080/api/hotels
    @PostMapping
    public ResponseEntity<Hotel> addHotel(@RequestBody HotelRequest request) {
        return ResponseEntity.ok(hotelService.addHotel(request));
    }

    // GET all rooms of a hotel
    // http://localhost:8080/api/hotels/1/rooms
    @GetMapping("/{hotelId}/rooms")
    public ResponseEntity<List<Room>> getRooms(@PathVariable Long hotelId) {
        return ResponseEntity.ok(hotelService.getRoomsByHotel(hotelId));
    }

    // GET available rooms of a hotel
    // http://localhost:8080/api/hotels/1/rooms/available
    @GetMapping("/{hotelId}/rooms/available")
    public ResponseEntity<List<Room>> getAvailableRooms(
            @PathVariable Long hotelId) {
        return ResponseEntity.ok(hotelService.getAvailableRooms(hotelId));
    }

    // POST add room to hotel
    // http://localhost:8080/api/hotels/1/rooms
    @PostMapping("/{hotelId}/rooms")
    public ResponseEntity<Room> addRoom(
            @PathVariable Long hotelId,
            @RequestBody RoomRequest request) {
        return ResponseEntity.ok(hotelService.addRoom(hotelId, request));
    }

    // PUT update room availability
    // http://localhost:8080/api/hotels/rooms/1/availability?available=false
    @PutMapping("/rooms/{roomId}/availability")
    public ResponseEntity<Room> updateAvailability(
            @PathVariable Long roomId,
            @RequestParam Boolean available) {
        return ResponseEntity.ok(
                hotelService.updateRoomAvailability(roomId, available)
        );
    }
}
