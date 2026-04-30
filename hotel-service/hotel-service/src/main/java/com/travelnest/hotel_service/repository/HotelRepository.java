package com.travelnest.hotel_service.repository;

import com.travelnest.hotel_service.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    // Find all hotels in a city
    // SQL: SELECT * FROM hotels WHERE city = ?
    // Case insensitive city search
    List<Hotel> findByCityIgnoreCase(String city);

    // Find hotels by city and rating greater than given value
    // SQL: SELECT * FROM hotels WHERE city = ? AND rating >= ?
    List<Hotel> findByCityAndRatingGreaterThanEqual(
            String city, Double rating
    );
}
