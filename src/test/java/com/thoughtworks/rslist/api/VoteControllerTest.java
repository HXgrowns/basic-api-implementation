package com.thoughtworks.rslist.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VoteControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldFindByVoteTime() throws Exception {
        mockMvc.perform(get("/votes")
        .param("startTime", "2020-08-08 23:37:35")
        .param("endTime", "2020-08-08 23:40:01"))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].voteNum", is(2)))
        .andExpect(jsonPath("$[0].voteTime", is("2020-08-08T23:37:35")));
    }
}
