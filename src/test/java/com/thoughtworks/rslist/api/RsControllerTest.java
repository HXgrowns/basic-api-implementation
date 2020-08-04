package com.thoughtworks.rslist.api;

import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    RsController rsController;

    @Test
    void shouldGetRsEventById() throws Exception {
        mockMvc.perform(get("/rs?index=1"))
                .andExpect(content().string("第一条事件"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs?index=2"))
                .andExpect(content().string("第二条事件"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs?index=3"))
                .andExpect(content().string("第三条事件"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRsEventListByGivenRange() throws Exception {
        mockMvc.perform(get("/rs/list/?start=1&end=2"))
                .andExpect(content().string("[第一条事件, 第二条事件]"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/?start=1"))
                .andExpect(content().string("[第一条事件, 第二条事件, 第三条事件]"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/?start=2&end=3"))
                .andExpect(content().string("[第二条事件, 第三条事件]"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/?end=3"))
                .andExpect(content().string("[第一条事件, 第二条事件, 第三条事件]"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddRsEvent() throws Exception {
        mockMvc.perform(post("/rs")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"第四事件\",\"keyword\":\"forth\"}"))
                .andExpect(status().isOk());
    }
}


