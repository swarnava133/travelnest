package com.travelnest.user_service.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity                          // This class = a database table
@Table(name = "users")           // Table name in MySQL will be "users"
@Data                            // Lombok: auto generates getters, setters
@NoArgsConstructor               // Lombok: auto generates empty constructor
@AllArgsConstructor              // Lombok: auto generates full constructor
public class User {

    @Id                                    // This field = primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto increment
    private Long id;

    @Column(nullable = false)              // This column cannot be empty
    private String name;

    @Column(nullable = false, unique = true)  // Must be unique — no duplicate emails
    private String email;

    @Column(nullable = false)
    private String password;              // Will store ENCRYPTED password

    @Column(nullable = false)
    private String role;                  // "ROLE_USER" or "ROLE_ADMIN"
}
