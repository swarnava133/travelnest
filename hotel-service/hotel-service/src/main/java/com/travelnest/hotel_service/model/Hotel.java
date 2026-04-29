package com.travelnest.hotel_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;              // "Taj Hotel"

    @Column(nullable = false)
    private String city;              // "Kolkata"

    @Column(nullable = false)
    private String address;           // "1 AJC Bose Road"

    @Column(nullable = false)
    private String description;       // "5 star luxury hotel"

    private String imageUrl;          // hotel photo URL

    @Column(nullable = false)
    private Double rating;            // 4.5

    // One hotel has many rooms
    // mappedBy = "hotel" means Room.java has a field named "hotel"
    // cascade = if hotel deleted, delete all its rooms too
    // fetch = LAZY means don't load rooms unless we ask for them
    @JsonIgnore
    @OneToMany(mappedBy = "hotel",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Room> rooms;
}
