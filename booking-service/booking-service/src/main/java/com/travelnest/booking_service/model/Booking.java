package com.travelnest.booking_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail;         // who booked

    @Column(nullable = false)
    private Long hotelId;             // which hotel

    @Column(nullable = false)
    private Long roomId;              // which room

    @Column(nullable = false)
    private String hotelName;         // hotel name (for display)

    @Column(nullable = false)
    private String roomNumber;        // room number (for display)

    @Column(nullable = false)
    private LocalDate checkInDate;    // check in date

    @Column(nullable = false)
    private LocalDate checkOutDate;   // check out date

    @Column(nullable = false)
    private Integer numberOfGuests;   // how many people

    @Column(nullable = false)
    private Double totalAmount;       // total price

    @Column(nullable = false)
    private String status;            // PENDING, CONFIRMED, CANCELLED

    @Column(nullable = false)
    private LocalDateTime bookedAt;   // when booking was made

    private Long paymentId;           // payment reference
}
