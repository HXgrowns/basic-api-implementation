package com.thoughtworks.rslist.entity;

public class RsEvent {
    private String name;
    private String keyword;

    public RsEvent() {
    }

    public RsEvent(String name, String keyword) {
        this.name = name;
        this.keyword = keyword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return String.format("{\n" +
                "\"name\": \"%s\",\n" +
                "\"keyword\": \"%s\"\n" +
                "}", this.name, this.keyword);
    }
}
