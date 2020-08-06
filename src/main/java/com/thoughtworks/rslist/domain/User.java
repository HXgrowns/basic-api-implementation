package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@JsonView(RsEvent.PrivateView.class)
public class User {
    private int id;

    @Size(max = 8)
    @NotBlank(message = "user name is null")
    @JsonProperty("userName")
    private String userName;

    @Min(value = 18, message = "age < 18")
    @Max(value = 100, message = "age > 100")
    @NotNull
    @JsonProperty("age")
    private Integer age;

    @NotNull
    @JsonProperty("gender")
    private String gender;

    @Email
    @JsonProperty("email")
    private String email;

    @Pattern(regexp = "1\\d{10}", message = "invalid phone")
    @JsonProperty("phone")
    private String phone;

    private int voteCount = 10;

    public User(@Size(max = 8) @NotNull String userName,
                @Min(18) @Max(100) @NotNull Integer age,
                @NotNull String gender,
                @Email String email,
                @Pattern(regexp = "1\\d{10}") String phone) {
        this.userName = userName;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }


}