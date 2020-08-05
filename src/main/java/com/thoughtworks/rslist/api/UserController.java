package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private List<User> userList;

    public UserController() {
        this.userList = new ArrayList<>();
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        userList.add(user);
    }

}
