package io.github.matywaky.crud.service;

import io.github.matywaky.crud.dto.UserDto;
import io.github.matywaky.crud.entity.User;
import io.github.matywaky.crud.mapper.UserMapper;
import io.github.matywaky.crud.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }
}
