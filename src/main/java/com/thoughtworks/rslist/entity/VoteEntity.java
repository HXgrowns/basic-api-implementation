package com.thoughtworks.rslist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer voteNum;
    private LocalDateTime voteTime;

    @ManyToOne(targetEntity = UserEntity.class, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne(targetEntity = RsEventEntity.class, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "rs_event_id", referencedColumnName = "id")
    private RsEventEntity rsEventEntity;
}
