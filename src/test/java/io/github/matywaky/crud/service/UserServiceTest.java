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

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    
    @Test
    public void testGetUserById_ValidId() {
        UserDto userDto = userService.getUserById(1L);
        User user = UserMapper.mapToUser(userDto);
        Assertions.assertEquals("Mateusz", user.getFirstName());
        Assertions.assertEquals("Nowak", user.getLastName());
        Assertions.assertEquals("admin@admin.admin", user.getEmail());
        Assertions.assertEquals("admin", user.getPassword());
    }

    @Test
    public void testGetUserById_InvalidId() {
        Assertions.assertThrows(
                InvalidInputException.class,
                () -> userService.getUserById(-1L)
        );
    }

    @Test
    public void testGetUserById_NotFoundId() {
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.getUserById(100L)
        );
    }

    @Test
    public void testGetUserById_NullId() {
        Assertions.assertThrows(
                InvalidInputException.class,
                () -> userService.getUserById(null)
        );
    }
}
