package com.thoughtworks.rslist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RsEvent {

    @NotBlank
    private String eventName;
    @NotBlank
    private String keyword;

    @Valid
    private User user;

    public RsEvent() {
    }

    public RsEvent(String name, String keyword) {
        this.eventName = name;
        this.keyword = keyword;
    }

    public RsEvent(@NotBlank String eventName, @NotBlank String keyword, @NotNull User user) {
        this.eventName = eventName;
        this.keyword = keyword;
        this.user = user;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    //@JsonIgnore
    public User getUser() {
        return user;
    }

    //@JsonProperty
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("{\n" +
                "\"name\": \"%s\",\n" +
                "\"keyword\": \"%s\"\n" +
                "}", this.eventName, this.keyword);
    }
}
