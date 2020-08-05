package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.entity.RsEvent;
import com.thoughtworks.rslist.entity.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
                .andExpect(jsonPath("$.eventName", is("第一条事件")))
                .andExpect(jsonPath("$.keyword", is("one")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user").doesNotExist());
    }

    @Test
    void shouldGetRsEventListByGivenRange() throws Exception {
        mockMvc.perform(get("/rs/list/?start=0&end=2"))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyword", is("one")))
                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyword", is("two")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list/?start=1"))
                .andExpect(jsonPath("$[0].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[0].keyword", is("two")))
                .andExpect(jsonPath("$[1].eventName", is("第三条事件")))
                .andExpect(jsonPath("$[1].keyword", is("three")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list/?end=2"))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyword", is("one")))
                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyword", is("two")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyword", is("one")))
                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyword", is("two")))
                .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
                .andExpect(jsonPath("$[2].keyword", is("three")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list/?start=0&end=100"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid request param")));
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


    @Test
    void shouldFailWhenKeywordIsNull() throws Exception {
        RsEvent rsEvent = new RsEvent("添加一条热搜", "");
        User user = new User(
                "huxiao",
                19,
                "male",
                "a@thoughtworks.com",
                "18888888888");
        rsEvent.setUser(user);
        shouldAddUserRsEvent(rsEvent);
    }

    @Test
    void shouldFailWhenEventNameIsNull() throws Exception {
        RsEvent rsEvent = new RsEvent("", "娱乐");
        User user = new User(
                "huxiao",
                19,
                "male",
                "a@thoughtworks.com",
                "18888888888");
        rsEvent.setUser(user);
        shouldAddUserRsEvent(rsEvent);
    }

    @Test
    void shouldFailWhenUserNameInValid() throws Exception {
        RsEvent rsEvent = new RsEvent("添加一条热搜", "娱乐");
        User user = new User(
                null,
                19,
                "male",
                "a@thoughtworks.com",
                "18888888888");
        rsEvent.setUser(user);
        shouldAddUserRsEvent(rsEvent);

        user = new User(
                "xiaowang&huxiao",
                19,
                "female",
                "a@thoughtworks.com",
                "18888888888");
        rsEvent.setUser(user);
        shouldAddUserRsEvent(rsEvent);
    }

    @Test
    void shouldFailWhenGenderIsNull() throws Exception {
        RsEvent rsEvent = new RsEvent("添加一条热搜", "娱乐");
        User user = new User(
                "huxiao",
                19,
                null,
                "a@thoughtworks.com",
                "18888888888");
        rsEvent.setUser(user);
        shouldAddUserRsEvent(rsEvent);
    }

    @Test
    void shouldFailWhenAgeIsNullOrNotRange() throws Exception {
        RsEvent rsEvent = new RsEvent("添加一条热搜", "娱乐");
        User user = new User(
                "huxiao",
                null,
                "male",
                "a@thoughtworks.com",
                "18888888888");
        rsEvent.setUser(user);
        shouldAddUserRsEvent(rsEvent);

        user = new User(
                "huxiao",
                7,
                "male",
                "a@thoughtworks.com",
                "18888888888");
        rsEvent.setUser(user);
        shouldAddUserRsEvent(rsEvent);

        user = new User(
                "huxiao",
                101,
                "male",
                "a@thoughtworks.com",
                "18888888888");
        rsEvent.setUser(user);
        shouldAddUserRsEvent(rsEvent);
    }

    @Test
    void shouldValidEmail() throws Exception {
        RsEvent rsEvent = new RsEvent("添加一条热搜", "娱乐");
        User user = new User(
                "huxiao",
                19,
                "male",
                "athoughtworks.com",
                "18888888888");
        rsEvent.setUser(user);
        shouldAddUserRsEvent(rsEvent);
    }

    @Test
    void shouldValidPhone() throws Exception {
        RsEvent rsEvent = new RsEvent("添加一条热搜", "娱乐");
        User user = new User(
                "huxiao",
                19,
                "male",
                "a@thoughtworks.com",
                "08888888888");
        rsEvent.setUser(user);
        shouldAddUserRsEvent(rsEvent);

        user = new User(
                "huxiao",
                19,
                "male",
                "a@thoughtworks.com",
                "118888888888");
        rsEvent.setUser(user);
        shouldAddUserRsEvent(rsEvent);
    }

    @Test
    void shouldFailWhenExist() throws Exception {
        RsEvent rsEvent = new RsEvent("添加一条热搜", "娱乐", new User(
                "huxiao",
                19,
                "male",
                "a111@thoughtworks.com",
                "18888888888"));

        ObjectMapper objectMapper = new ObjectMapper();
        String rsEventString = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rsEventString))
                .andExpect(status().isCreated());
    }

    void shouldAddUserRsEvent(RsEvent rsEvent) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String rsEventString = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rsEventString))
                .andExpect(status().isBadRequest());
    }
}


