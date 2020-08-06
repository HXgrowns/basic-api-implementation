package com.thoughtworks.rslist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private int age;
    private String gender;
    private String email;
    private String phone;
    private int vote;

    //public UserEntity(String userName, int age, String gender, String email, String phone) {
    //    this.name = userName;
    //    this.age = age;
    //    this.gender = gender;
    //    this.email = email;
    //    this.phone = phone;
    //}
}
