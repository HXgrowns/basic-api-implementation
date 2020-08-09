package com.thoughtworks.rslist.entity;

import com.thoughtworks.rslist.domain.RsEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer age;
    private String gender;
    private String email;
    private String phone;
    @Column(name = "vote_num", columnDefinition = "tinyint default 10")
    private Integer vote;
}
