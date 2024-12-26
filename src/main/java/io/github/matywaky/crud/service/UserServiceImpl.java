package io.github.matywaky.crud.service;

import io.github.matywaky.crud.dto.UserDto;
import io.github.matywaky.crud.entity.User;
import io.github.matywaky.crud.exception.EntityNotFoundException;
import io.github.matywaky.crud.mapper.UserMapper;
import io.github.matywaky.crud.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public UserDto getUserById(Long id) {
        User user = checkIfUserWithIdExistsOrThrow(id);
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map((UserMapper::mapToUserDto)).toList();
    }

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

    @Override
    public void deleteUser(Long id) {
        checkIfUserWithIdExistsOrThrow(id);

        userRepository.deleteById(id);
    }

    private User checkIfUserWithIdExistsOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), id));
    }
}
