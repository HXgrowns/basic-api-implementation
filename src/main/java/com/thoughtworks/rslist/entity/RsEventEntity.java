package com.thoughtworks.rslist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

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
    @Column(name = "vote_num", columnDefinition = "tinyint default 0")
    private Integer voteNum;

    @ManyToOne(targetEntity = UserEntity.class, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    @JsonIgnore
    public UserEntity getUser() {
        return user;
    }

    public RsEventEntity(String eventName, String keyword, UserEntity user) {
        this.eventName = eventName;
        this.keyword = keyword;
        this.user = user;
    }
}
