package com.travelnest.hotel_service.service;

import com.travelnest.hotel_service.dto.HotelRequest;
import com.travelnest.hotel_service.dto.RoomRequest;
import com.travelnest.hotel_service.model.Hotel;
import com.travelnest.hotel_service.model.Room;
import com.travelnest.hotel_service.repository.HotelRepository;
import com.travelnest.hotel_service.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    //  Get all hotels
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    //  Get hotel by id
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Hotel not found with id: " + id));
    }

    //  Search hotels by city
    public List<Hotel> getHotelsByCity(String city) {
        return hotelRepository.findByCityIgnoreCase(city);
    }

    //  Add new hotel
    public Hotel addHotel(HotelRequest request) {
        Hotel hotel = new Hotel();
        hotel.setName(request.getName());
        hotel.setCity(request.getCity());
        hotel.setAddress(request.getAddress());
        hotel.setDescription(request.getDescription());
        hotel.setImageUrl(request.getImageUrl());
        hotel.setRating(request.getRating());
        return hotelRepository.save(hotel);
    }

    //  Get all rooms of a hotel
    public List<Room> getRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    //  Get available rooms of a hotel
    public List<Room> getAvailableRooms(Long hotelId) {
        return roomRepository.findByHotelIdAndIsAvailable(hotelId, true);
    }

    //  Add room to hotel
    public Room addRoom(Long hotelId, RoomRequest request) {

        // First find the hotel
        Hotel hotel = getHotelById(hotelId);

        // Create room and link to hotel
        Room room = new Room();
        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        room.setPricePerNight(request.getPricePerNight());
        room.setCapacity(request.getCapacity());
        room.setDescription(request.getDescription());
        room.setIsAvailable(true);      // new room is available by default
        room.setHotel(hotel);           // link room to hotel ← important!

        return roomRepository.save(room);
    }

    //  Update room availability
    // Called by booking-service when room is booked or cancelled
    public Room updateRoomAvailability(Long roomId, Boolean isAvailable) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() ->
                        new RuntimeException("Room not found with id: " + roomId));
        room.setIsAvailable(isAvailable);
        return roomRepository.save(room);
    }
}
