package io.github.matywaky.crud.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) for user information.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {
    /**
     * Unique identifier for the user.
     */
    private Long id;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The password of the user.
     */
    private String password;
}
