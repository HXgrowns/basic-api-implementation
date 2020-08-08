package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
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
class RsJpaControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldAddEventSuccess() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(2);
        RsEventEntity rsEvent = new RsEventEntity("first event", "one", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String rsEventString = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/jpa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rsEventString))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldAddEventFail() throws Exception {
        UserEntity user = new UserEntity(new User("huxiao", 18, "female", "hu@thoughtworks.com", "18888818888"));
        user.setId(0);
        RsEventEntity rsEvent = new RsEventEntity("first event", "one", user);

        ObjectMapper objectMapper = new ObjectMapper();
        String rsEventString = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/jpa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rsEventString))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteUserAndRsEvens() throws Exception {
        mockMvc.perform(delete("/user/2"))
                .andExpect(status().isOk());
    }


    @Test
    void shouldUpdateRsEvent() throws Exception {
        shouldAddEventSuccess();
        UserEntity user = new UserEntity(new User("huxiao", 18, "female", "hu@thoughtworks.com", "18888818888"));
        user.setId(1);
        RsEventEntity rsEvent = new RsEventEntity("new event", "new one", user);
        rsEvent.setId(1);
        ObjectMapper objectMapper = new ObjectMapper();
        String rsEventString = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(patch("/rs/jpa/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rsEventString))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldVoteRsEvent() throws Exception {
        mockMvc.perform(post("/rs/vote/jpa/-1")
                .param("userId", "3")
                .param("voteNum", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("reEvent is not exists")));

        mockMvc.perform(post("/rs/vote/jpa/1")
                .param("userId", "-1")
                .param("voteNum", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("user is not exists")));

        mockMvc.perform(post("/rs/vote/jpa/1")
                .param("userId", "3")
                .param("voteNum", "20"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("user total voteNum < voteNum")));

        mockMvc.perform(post("/rs/vote/jpa/1")
                .param("userId", "3")
                .param("voteNum", "2"))
                .andExpect(status().isCreated());
    }
}