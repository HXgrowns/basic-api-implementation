package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class UserControllerTest {
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }

    @Test
    void shouldGetUserList() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldValidUserName() throws Exception {
        User user = new User(
                "xiaowang&huxiao",
                19,
                "female",
                "a@thoughtworks.com",
                "18888888888");
        validUser(user);

        user = new User(
                null,
                19,
                "female",
                "a@thoughtworks.com",
                "18888888888");
        validUser(user);
    }

    @Test
    void shouldFailWhenGenderIsNull() throws Exception {
        User user = new User(
                "huxiao",
                19,
                null,
                "a@thoughtworks.com",
                "18888888888");
        validUser(user);
    }

    @Test
    void shouldFailWhenAgeIsNullOrNotRange() throws Exception {
        User user = new User(
                "huxiao",
                null,
                "male",
                "a@thoughtworks.com",
                "18888888888");
        validUser(user);

        user = new User(
                "huxiao",
                7,
                "male",
                "a@thoughtworks.com",
                "18888888888");
        validUser(user);

        user = new User(
                "huxiao",
                101,
                "male",
                "a@thoughtworks.com",
                "18888888888");
        validUser(user);
    }

    @Test
    void shouldValidEmail() throws Exception {
        User user = new User(
                "huxiao",
                19,
                "male",
                "athoughtworks.com",
                "18888888888");
        validUser(user);
    }

    @Test
    void shouldValidPhone() throws Exception {
        User user = new User(
                "huxiao",
                19,
                "male",
                "a@thoughtworks.com",
                "08888888888");
        validUser(user);

        user = new User(
                "huxiao",
                19,
                "male",
                "a@thoughtworks.com",
                "118888888888");
        validUser(user);
    }

    void validUser(User user) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String userString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userString))
                .andExpect(status().isBadRequest());
    }

}
