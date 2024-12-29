package io.github.matywaky.crud.service;

import io.github.matywaky.crud.dto.UserDto;
import io.github.matywaky.crud.entity.User;
import io.github.matywaky.crud.exception.EntityNotFoundException;
import io.github.matywaky.crud.exception.InvalidInputException;
import io.github.matywaky.crud.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void init() {
        user = User.builder()
                .firstName("Test")
                .lastName("Testowy")
                .email("test@test.test")
                .password("12345").build();

        userDto = UserDto.builder()
                .firstName("TestDto")
                .lastName("TestowyDto")
                .email("testDto@testDto.testDto")
                .password("12345Dto").build();
    }

    /// /////////////////////////////////
    ///  TESTS OF getUserById()
    /// /////////////////////////////////
    @Test
    public void UserService_GetUserById_ReturnUserDto() {
        Long id = 1L;
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserDto foundUserDto = userService.getUserById(id);

        Assertions.assertNotNull(foundUserDto);
        Assertions.assertEquals(user.getId(), foundUserDto.getId());
    }

    @Test
    public void UserService_GetUserByNotExistingId_ReturnUserDto() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception =
                new EntityNotFoundException(User.class.getSimpleName(), id);

        EntityNotFoundException testException = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.getUserById(id)
        );

        Assertions.assertEquals(exception.getMessage(), testException.getMessage());
    }

    @Test
    public void UserService_GetUserByInvalidId_ThrowException() {
        Long id = -1L;

        InvalidInputException exception =
                new InvalidInputException(String.valueOf(id));

        InvalidInputException testException = Assertions.assertThrows(
                InvalidInputException.class,
                () -> userService.getUserById(id)
        );

        Assertions.assertEquals(exception.getMessage(), testException.getMessage());
    }

    /// /////////////////////////////////
    ///  TESTS OF createUser()
    /// /////////////////////////////////

    @Test
    public void UserService_CreateUser_ReturnUserDto() {
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDto createdUserDto = userService.createUser(userDto);

        Assertions.assertNotNull(createdUserDto);
        Assertions.assertEquals(userDto.getId(), createdUserDto.getId());
    }

    @Test
    public void UserService_CreateUserWithNullData_ThrowException() {
        Assertions.assertThrows(RuntimeException.class, () -> userService.createUser(null));
    }

    /// /////////////////////////////////
    ///  TESTS OF getAllUsers()
    /// /////////////////////////////////

    @Test
    public void UserService_GetAllUsers_ReturnAllUserDtos() {
        User secondUser = User.builder()
                .firstName("Test2")
                .lastName("Testowy2")
                .email("test2@test2.test2")
                .password("1234522").build();
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(secondUser);

        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> userDtos = userService.getAllUsers();

        Assertions.assertNotNull(userDtos);
        Assertions.assertEquals(userDtos.size(), users.size());
    }

    @Test
    public void UserService_GetAllUsers_ReturnEmptyList() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<UserDto> userDtos = userService.getAllUsers();

        Assertions.assertNotNull(userDtos);
        Assertions.assertEquals(0, userDtos.size());
    }

    /// /////////////////////////////////
    ///  TESTS OF updateUser()
    /// /////////////////////////////////

    @Test
    public void UserService_UpdateUser_ReturnUserDto() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDto updatedUserDto = userService.updateUser(id, userDto);

        Assertions.assertNotNull(updatedUserDto);
        Assertions.assertEquals(user.getId(), updatedUserDto.getId());
        Assertions.assertEquals(userDto.getFirstName(), updatedUserDto.getFirstName());
    }

    /// /////////////////////////////////
    ///  TESTS OF deleteUser()
    /// /////////////////////////////////

    @Test
    public void UserService_DeleteUser_NoReturn() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Assertions.assertAll(() -> userService.deleteUser(id));
    }
}
