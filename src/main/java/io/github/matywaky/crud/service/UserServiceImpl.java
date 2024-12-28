package io.github.matywaky.crud.service;

import io.github.matywaky.crud.dto.UserDto;
import io.github.matywaky.crud.entity.User;
import io.github.matywaky.crud.exception.EntityNotFoundException;
import io.github.matywaky.crud.exception.InvalidInputException;
import io.github.matywaky.crud.mapper.UserMapper;
import io.github.matywaky.crud.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the UserService interface providing CRUD operations for User entities.
 * Uses UserRepository for database access and UserMapper for mapping between entity and DTO.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Creates a new user in the database.
     *
     * @param userDto the DTO containing user details to be created
     * @return the created user's details as a {@link UserDto}
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user's details as a {@link UserDto}
     * @throws EntityNotFoundException if no user is found with the given ID
     */
    @Override
    public UserDto getUserById(Long id) {
        User user = checkIfUserWithIdExistsOrThrow(id);
        return UserMapper.mapToUserDto(user);
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of user details as {@link UserDto}s
     */
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map((UserMapper::mapToUserDto)).toList();
    }

    /**
     * Updates an existing user's details.
     *
     * @param id the ID of the user to update
     * @param userDto the DTO containing updated user details
     * @return the updated user's details as a {@link UserDto}
     * @throws EntityNotFoundException if no user is found with the given ID
     */
    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = checkIfUserWithIdExistsOrThrow(id);

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @throws EntityNotFoundException if no user is found with the given ID
     */
    @Override
    public void deleteUser(Long id) {
        checkIfUserWithIdExistsOrThrow(id);

        userRepository.deleteById(id);
    }

    /**
     * Validates the existence of a user by their ID and retrieves the user entity.
     *
     * @param id the ID of the user to validate
     * @return the {@link User} entity
     * @throws InvalidInputException   if the ID is null or not positive
     * @throws EntityNotFoundException if no user is found with the given ID
     */
    protected User checkIfUserWithIdExistsOrThrow(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidInputException(id == null ? "null" : id.toString());
        }

        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), id));
    }
}
