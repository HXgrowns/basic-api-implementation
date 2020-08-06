package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RsEvent {
    public interface  PublicView{

    }

    public interface PrivateView extends PublicView{

    }

    @JsonView(PublicView.class)
    @NotBlank
    private String eventName;

    @NotBlank
    @JsonView(PublicView.class)
    private String keyword;

    @JsonView(PrivateView.class)
    private @Valid User user;

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

    @Override
    public String toString() {
        return String.format("{\n" +
                "\"name\": \"%s\",\n" +
                "\"keyword\": \"%s\"\n" +
                "}", this.eventName, this.keyword);
    }
}
