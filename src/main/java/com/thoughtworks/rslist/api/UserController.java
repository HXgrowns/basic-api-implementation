package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entity.User;
import com.thoughtworks.rslist.exception.CommonError;
import com.thoughtworks.rslist.exception.InvalidUserException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public void addUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InvalidUserException("invalid user");
        }

        if(user != null) {
            userList.add(user);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getUserList() {
        return ResponseEntity.ok(userList);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity exceptionHandler(RuntimeException e) {
        CommonError commonError = new CommonError();
        commonError.setError(e.getMessage());

        return ResponseEntity.badRequest().body(commonError);
    }
}
