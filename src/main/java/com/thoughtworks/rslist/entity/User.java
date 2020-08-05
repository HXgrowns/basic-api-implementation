package com.thoughtworks.rslist.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class User {
    @Size(max = 8)
    @NotNull
    private String userName;

    @Min(18)
    @Max(100)
    @NotNull
    private Integer age;

    @NotNull
    private String gender;

    @Email
    private String email;

    @Pattern(regexp = "1\\d{10}")
    private String phone;

    private int voteCount = 10;

    public User(@Size(max = 8) @NotNull String userName, @Min(18) @Max(100) @NotNull Integer age, @NotNull String gender, @Email String email, @Pattern(regexp = "1\\d{10}") String phone) {
        this.userName = userName;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }
}
