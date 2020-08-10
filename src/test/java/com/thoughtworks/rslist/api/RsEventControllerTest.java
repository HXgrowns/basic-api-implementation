package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
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
public class RsEventControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldAddEventSuccess() throws Exception {
        User user = new User();
        user.setId(1);
        RsEvent rsEvent = new RsEvent("third event", "three", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String rsEventString = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rsEventString))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldFindById() throws Exception {
        mockMvc.perform(get("/rs/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.eventName", is("third event")))
                .andExpect(jsonPath("$.voteNum", is(14)))
                .andExpect(jsonPath("$.keyword", is("three")));

        mockMvc.perform(get("/rs/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("rsEvent is not exists")));
    }

    @Test
    public void shouldFindAllByPage() throws Exception {
        String rsEventList = mockMvc.perform(get("/rsEvents")
                .param("size", "10")
                .param("page", "0"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(rsEventList);
    }

    @Test
    void shouldUpdateRsEvent() throws Exception {
        UserEntity user = new User("huxiao", 18, "female", "hu@thoughtworks.com", "18888818888").build();
        user.setId(3);
        RsEventEntity rsEvent = new RsEventEntity("new event", "new one", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String rsEventString = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(patch("/rs/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rsEventString))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldVoteRsEvent() throws Exception {
        mockMvc.perform(post("/rs/vote/-1")
                .param("userId", "3")
                .param("voteNum", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("reEvent is not exists")));

        mockMvc.perform(post("/rs/vote/3")
                .param("userId", "-1")
                .param("voteNum", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("user is not exists")));

        mockMvc.perform(post("/rs/vote/3")
                .param("userId", "3")
                .param("voteNum", "20"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("user total voteNum < voteNum")));

        mockMvc.perform(post("/rs/vote/2")
                .param("userId", "2")
                .param("voteNum", "2"))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldDeleteRsEvent() throws Exception {
        mockMvc.perform(delete("/rs/10"))
                .andExpect(status().isOk());
    }
}
