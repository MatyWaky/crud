package io.github.matywaky.crud.service;

import io.github.matywaky.crud.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
