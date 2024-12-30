package io.github.matywaky.crud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.matywaky.crud.dto.UserDto;
import io.github.matywaky.crud.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userDto = UserDto.builder()
                .firstName("TestDto")
                .lastName("TestowyDto")
                .email("testDto@testDto.testDto")
                .password("12345Dto").build();
    }

    /// /////////////////////////////////
    ///  TESTS OF createUser()
    ///  POST /api/users
    /// /////////////////////////////////
    @Test
    public void UserController_CreateUser_ReturnsCreatedUserDto() throws Exception {
        when(userService.createUser(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(userDto.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userDto.getEmail())));
    }

    /// /////////////////////////////////
    ///  TESTS OF getAllUsers()
    ///  GET /api/users
    /// /////////////////////////////////
    @Test
    public void UserController_GetAllUsers_ReturnsUserDto() throws Exception {
        UserDto secondUserDto = UserDto.builder()
                .firstName("TestDto2")
                .lastName("TestowyDto2")
                .email("testDto2@testDto2.testDto2")
                .password("12345Dto2").build();

        List<UserDto> userDtos = List.of(userDto, secondUserDto);

        when(userService.getAllUsers()).thenReturn(userDtos);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(userDtos.size())));
    }

    /// /////////////////////////////////
    ///  TESTS OF getUserById()
    ///  GET /api/users/{id}
    /// /////////////////////////////////
    @Test
    public void UserController_GetUserById_ReturnsUserDto() throws Exception {
        Long userId = 1L;

        when(userService.getUserById(userId)).thenReturn(userDto);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$")).isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userDto.getEmail())));
    }

    /// /////////////////////////////////
    ///  TESTS OF deleteUser()
    ///  DELETE /api/users/{id}
    /// /////////////////////////////////
    @Test
    public void UserController_DeleteUserById_ReturnsText() throws Exception {
        Long userId = 1L;

        doNothing().when(userService).deleteUser(userId);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /// /////////////////////////////////
    ///  TESTS OF updateUser()
    ///  PUT /api/users/{id}
    /// /////////////////////////////////
    @Test
    public void UserController_UpdateUserWithId_ReturnsUpdatedUserDto() throws Exception {
        Long userId = 1L;

        when(userService.updateUser(eq(userId), eq(userDto))).thenReturn(userDto);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$")).isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userDto.getEmail())));
    }
}
