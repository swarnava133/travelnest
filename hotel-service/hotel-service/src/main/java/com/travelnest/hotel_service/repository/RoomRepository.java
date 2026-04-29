package com.travelnest.hotel_service.repository;

import com.travelnest.hotel_service.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Find all rooms of a specific hotel
    // SQL: SELECT * FROM rooms WHERE hotel_id = ?
    List<Room> findByHotelId(Long hotelId);

    // Find available rooms of a hotel
    // SQL: SELECT * FROM rooms WHERE hotel_id = ? AND is_available = true
    List<Room> findByHotelIdAndIsAvailable(Long hotelId, Boolean isAvailable);
}
