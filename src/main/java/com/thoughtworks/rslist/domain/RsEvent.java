package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.thoughtworks.rslist.entity.RsEventEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RsEvent {
    private Integer id;
    public interface  PublicView{

    }

    public interface PrivateView extends PublicView{

    }

    @JsonView(PublicView.class)
    @NotNull(message = "eventName is null")
    private String eventName;

    @NotNull(message = "keyword is null")
    @JsonView(PublicView.class)
    private String keyword;

    @JsonView(PrivateView.class)
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

    @Override
    public String toString() {
        return String.format("{\n" +
                "\"name\": \"%s\",\n" +
                "\"keyword\": \"%s\"\n" +
                "}", this.eventName, this.keyword);
    }

    public RsEventEntity build() {
        return RsEventEntity.builder()
                .id(id)
                .user(this.user.build())
                .eventName(this.eventName)
                .voteNum(0)
                .keyword(this.keyword)
                .build();
    }
}
