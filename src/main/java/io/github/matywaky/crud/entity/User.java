package io.github.matywaky.crud.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a user in the system.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The first name of the user.
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * The last name of the user.
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * The email address of the user.
     */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * The password of the user.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Constructor that accepts basic user information.
     *
     * @param firstName the first name of the user.
     * @param lastName the last name of the user.
     * @param email the email address of the user.
     * @param password the hashed password of the user.
     */
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
