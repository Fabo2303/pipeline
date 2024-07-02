package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetUsers() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setPassword("pass1");
        user1.setRole("ROLE_USER");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setPassword("pass2");
        user2.setRole("ROLE_ADMIN");

        Mockito.when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(user1, user2))));
    }

    @Test
    void testCreateUser() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("pass1");
        user.setRole("ROLE_USER");

        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("User created successfully"));
    }

    @Test
    void testGetUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setPassword("pass1");
        user.setRole("ROLE_USER");

        Mockito.when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    void testGetUserNotFound() throws Exception {
        Mockito.when(userService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setPassword("pass1");
        user.setRole("ROLE_USER");

        Mockito.when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }

    @Test
    void testDeleteUserNotFound() throws Exception {
        Mockito.when(userService.findById(1L)).thenReturn(null);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setPassword("pass1");
        user.setRole("ROLE_USER");

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("updatedUser");
        updatedUser.setPassword("updatedPass");
        updatedUser.setRole("ROLE_ADMIN");

        Mockito.when(userService.updateById(1L, user)).thenReturn(updatedUser);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedUser)));
    }

    @Test
    void testUpdateUserNotFound() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setPassword("pass1");
        user.setRole("ROLE_USER");

        Mockito.when(userService.updateById(1L, user)).thenReturn(null);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }
}