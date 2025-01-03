package io.github.matywaky.crud.service;

import io.github.matywaky.crud.dto.UserDto;

import java.util.List;

/**
 * UserService interface providing CRUD operations for User entities
 */
public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
}
