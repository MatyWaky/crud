package io.github.matywaky.crud.repository;

import io.github.matywaky.crud.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User buildUser() {
        return User.builder()
                .firstName("Test")
                .lastName("Testowy")
                .email("test@test.test")
                .password("12345").build();
    }

    @Test
    public void UserRepository_SaveUser_ReturnSavedUser() {
        User user = buildUser();

        User savedUser = userRepository.save(user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(user, savedUser);
        Assertions.assertEquals(user.getId(), savedUser.getId());
        Assertions.assertEquals(user.getFirstName(), savedUser.getFirstName());
        Assertions.assertEquals(user.getLastName(), savedUser.getLastName());
        Assertions.assertEquals(user.getEmail(), savedUser.getEmail());
        Assertions.assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    public void UserRepository_SaveWithNotUniqueEmail_ThrowsException() {
        User user = buildUser();
        userRepository.save(user);

        User user2 = User.builder()
                .firstName("Test2")
                .lastName("Testowy2")
                .email("test@test.test")
                .password("123456").build();

        Assertions.assertThrows(RuntimeException.class, () -> userRepository.save(user2));
    }

    @Test
    public void UserRepository_FindUserById_ReturnUser() {
        User user = buildUser();
        userRepository.save(user);

        Optional<User> optionalUser = userRepository.findById(user.getId());
        Assertions.assertTrue(optionalUser.isPresent());

        User foundUser = optionalUser.get();
        Assertions.assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    public void UserRepository_FindUserByNotExistingId_ReturnEmptyOptional() {
        User user = buildUser();
        userRepository.save(user);

        Optional<User> optionalUser = userRepository.findById(user.getId()+1);

        Assertions.assertFalse(optionalUser.isPresent());
    }

    @Test
    public void UserRepository_FindAllUsers_ReturnAllUsers() {
        User user = buildUser();
        userRepository.save(user);

        User user2 = User.builder()
                .firstName("Test2")
                .lastName("Testowy2")
                .email("test2@test2.test2")
                .password("123456").build();

        userRepository.save(user2);

        List<User> users = userRepository.findAll();

        Assertions.assertNotNull(users);
        Assertions.assertEquals(2, users.size());
    }

    @Test
    public void UserRepository_DeleteUserWithID_ReturnOptionalEmpty() {
        User user = buildUser();
        userRepository.save(user);

        userRepository.deleteById(user.getId());
        Optional<User> optionalUser = userRepository.findById(user.getId());

        Assertions.assertFalse(optionalUser.isPresent());
    }

    @Test
    public void UserRepository_DeleteUserWithNotExistingID_ReturnOptionalEmpty() {
        User user = buildUser();
        userRepository.save(user);

        userRepository.deleteById(user.getId()+1);
        Optional<User> optionalUser = userRepository.findById(user.getId()+1);

        Assertions.assertFalse(optionalUser.isPresent());
    }
}
