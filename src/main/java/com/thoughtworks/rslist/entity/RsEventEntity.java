package com.thoughtworks.rslist.entity;

import com.thoughtworks.rslist.domain.RsEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "rs_event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RsEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String eventName;
    private String keyword;
    //private Integer userId;

    @ManyToOne(targetEntity = UserEntity.class, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    public RsEventEntity(String eventName, String keyword) {
        this.eventName = eventName;
        this.keyword = keyword;
    }

    public RsEventEntity(RsEvent rsEvent) {
        this.id = rsEvent.getId();
        this.eventName = rsEvent.getEventName();
        this.keyword = rsEvent.getKeyword();
        this.user = new UserEntity(rsEvent.getUser());
    }

    public RsEventEntity(String eventName, String keyword, UserEntity user) {
        this.eventName = eventName;
        this.keyword = keyword;
        this.user = user;
    }
}
