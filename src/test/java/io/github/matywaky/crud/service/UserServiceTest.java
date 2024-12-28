package io.github.matywaky.crud.service;

import io.github.matywaky.crud.dto.UserDto;
import io.github.matywaky.crud.entity.User;
import io.github.matywaky.crud.exception.EntityNotFoundException;
import io.github.matywaky.crud.exception.InvalidInputException;
import io.github.matywaky.crud.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for the {@link UserService} class.
 */
@SpringBootTest
public class UserServiceTest {

    /**
     * The {@link UserService} bean to be tested. It is automatically injected by Spring.
     */
    @Autowired
    private UserService userService;

    /**
     * Test the {@link UserService#getUserById(Long)} method with a valid user ID.
     * This test verifies that when a valid user ID is provided, the service correctly retrieves the
     * corresponding user data and maps it to a {@link UserDto} object. The expected values of the user's
     * first name, last name, email, and password are asserted.
     */
    @Test
    public void testGetUserById_ValidId() {
        UserDto userDto = userService.getUserById(1L);
        User user = UserMapper.mapToUser(userDto);
        Assertions.assertEquals("Mateusz", user.getFirstName());
        Assertions.assertEquals("Nowak", user.getLastName());
        Assertions.assertEquals("admin@admin.admin", user.getEmail());
        Assertions.assertEquals("admin", user.getPassword());
    }

    /**
     * Test the {@link UserService#getUserById(Long)} method with an invalid user ID.
     * This test ensures that when an invalid user ID (less than or equal to zero) is provided,
     * an {@link InvalidInputException} is thrown.
     */
    @Test
    public void testGetUserById_InvalidId() {
        Assertions.assertThrows(
                InvalidInputException.class,
                () -> userService.getUserById(-1L)
        );
    }

    /**
     * Test the {@link UserService#getUserById(Long)} method with a non-existent user ID.
     * This test ensures that when a non-existent user ID is provided, an {@link EntityNotFoundException}
     * is thrown. The ID used in this test (100L) is assumed to not exist in the database.
     */
    @Test
    public void testGetUserById_NotFoundId() {
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.getUserById(100L)
        );
    }

    /**
     * Test the {@link UserService#getUserById(Long)} method with a null user ID.
     * This test ensures that when a null user ID is provided, an {@link InvalidInputException} is thrown.
     */
    @Test
    public void testGetUserById_NullId() {
        Assertions.assertThrows(
                InvalidInputException.class,
                () -> userService.getUserById(null)
        );
    }
}
