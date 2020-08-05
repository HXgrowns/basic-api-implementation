package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.entity.RsEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class RsControllerTest {
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RsController()).build();
    }

    @Test
    void shouldGetRsEventById() throws Exception {
        mockMvc.perform(get("/rs/0"))
                .andExpect(jsonPath("$.name", is("第一条事件")))
                .andExpect(jsonPath("$.keyword", is("one")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRsEventListByGivenRange() throws Exception {
        mockMvc.perform(get("/rs/list/?start=0&end=2"))
                .andExpect(jsonPath("$[0].name", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyword", is("one")))
                .andExpect(jsonPath("$[1].name", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyword", is("two")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list/?start=1"))
                .andExpect(jsonPath("$[0].name", is("第二条事件")))
                .andExpect(jsonPath("$[0].keyword", is("two")))
                .andExpect(jsonPath("$[1].name", is("第三条事件")))
                .andExpect(jsonPath("$[1].keyword", is("three")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list/?end=2"))
                .andExpect(jsonPath("$[0].name", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyword", is("one")))
                .andExpect(jsonPath("$[1].name", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyword", is("two")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].name", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyword", is("one")))
                .andExpect(jsonPath("$[1].name", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyword", is("two")))
                .andExpect(jsonPath("$[2].name", is("第三条事件")))
                .andExpect(jsonPath("$[2].keyword", is("three")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddRsEvent() throws Exception {
        RsEvent rsEvent = new RsEvent("第四事件", "four");
        ObjectMapper objectMapper = new ObjectMapper();
        String rsEventString = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rsEventString))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateRsEvent() throws Exception {
        RsEvent rsEvent = new RsEvent("第一条事件", "one and one");
        ObjectMapper objectMapper = new ObjectMapper();
        String rsEventString = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(put("/rs/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rsEventString))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteRsEvent() throws Exception {
        mockMvc.perform(delete("/rs/1"))
                .andExpect(status().isOk());
    }
}


