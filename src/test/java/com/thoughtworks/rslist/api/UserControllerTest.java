package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldAddUser() throws Exception {
        User user = new User("hu", 18, "female", "hu@thoughtworks.com", "18888818888");

        ObjectMapper objectMapper = new ObjectMapper();
        String userString = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userString))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddUserFail() throws Exception {
        User user = new User("huxiao", -1, "female", "hu@thoughtworks.com", "18888818888");
        ObjectMapper objectMapper = new ObjectMapper();
        String userString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userString))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("age < 18")));
    }


    @Test
    void shouldFineUserById() throws Exception {
        mockMvc.perform(get("/user/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("hu")));
    }

    @Test
    void shouldDeleteUserById() throws Exception {
        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("user id is not exists")));
    }

}
