package com.travelnest.booking_service.repository;
import com.travelnest.booking_service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Get all bookings for a specific user
    List<Booking> findByUserEmail(String userEmail);

    // Get all bookings for a specific room
    List<Booking> findByRoomId(Long roomId);

    // Get bookings by status
    List<Booking> findByStatus(String status);
}
