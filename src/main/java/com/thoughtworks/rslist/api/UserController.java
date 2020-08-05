package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private List<User> userList;

    public UserController() {
        this.userList = new ArrayList<>(Arrays.asList(
                new User("hu",
                        20, "female", "hu@thoughtworks.com", "12222222222"),
                new User("xiao",
                        20, "female", "hu@thoughtworks.com", "12222222222")));
    }

    @PostMapping
    public void addUser(@RequestBody @Valid User user) {
        userList.add(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUserList() {
        return ResponseEntity.ok(userList);
    }
}
