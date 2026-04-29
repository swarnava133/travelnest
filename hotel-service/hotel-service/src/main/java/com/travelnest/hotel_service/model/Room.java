package com.travelnest.hotel_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomNumber;        // "101", "202"

    @Column(nullable = false)
    private String roomType;          // "SINGLE", "DOUBLE", "SUITE"

    @Column(nullable = false)
    private Double pricePerNight;     // 2500.00

    @Column(nullable = false)
    private Integer capacity;         // max people: 1, 2, 4

    @Column(nullable = false)
    private Boolean isAvailable;      // true = can be booked

    private String description;       // "Sea view room with AC"

    // Many rooms belong to ONE hotel
    // @ManyToOne = opposite of @OneToMany in Hotel.java
    // @JoinColumn = foreign key column name in rooms table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;
}
