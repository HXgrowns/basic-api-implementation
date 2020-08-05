package com.thoughtworks.rslist.entity;

public class RsEvent {
    private String eventName;
    private String keyword;
    private User user;

    public RsEvent() {
    }

    public RsEvent(String name, String keyword) {
        this.eventName = name;
        this.keyword = keyword;
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

    public User getUser() {
        return user;
    }

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
