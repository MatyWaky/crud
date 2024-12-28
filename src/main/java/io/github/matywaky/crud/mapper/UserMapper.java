package io.github.matywaky.crud.mapper;

import io.github.matywaky.crud.dto.UserDto;
import io.github.matywaky.crud.entity.User;

/**
 * Utility class for mapping between {@link User} entity and {@link UserDto}.
 */
public class UserMapper {

    /**
     * Converts a {@link User} entity to a {@link UserDto}.
     *
     * @param user the {@link User} entity to be converted.
     * @return a new {@link UserDto} containing the user data.
     * @throws IllegalArgumentException if the provided user is null.
     */
    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
        );
    }

    /**
     * Converts a {@link UserDto} to a {@link User} entity.
     *
     * @param userDto the {@link UserDto} to be converted.
     * @return a new {@link User} entity.
     * @throws IllegalArgumentException if the provided userDto is null.
     */
    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPassword()
        );
    }
}
