package io.github.matywaky.crud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.matywaky.crud.dto.UserDto;
import io.github.matywaky.crud.entity.User;
import io.github.matywaky.crud.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    public void UserController_CreateUser_ReturnsCreatedUser() throws Exception {
        when(userService.createUser(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(userDto.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userDto.getEmail())));
    }
}
