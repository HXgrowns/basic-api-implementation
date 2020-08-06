package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.exception.CommonError;
import com.thoughtworks.rslist.exception.InvalidUserException;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
@ControllerAdvice
public class UserController {
    private List<User> userList;

    public UserController() {
        this.userList = new ArrayList<>(Arrays.asList(
                new User("hu",
                        20, "female", "hu@thoughtworks.com", "12222222222"),
                new User("xiao",
                        20, "female", "hu@thoughtworks.com", "12222222222")));
    }

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity addUser(@RequestBody(required = false) @Valid User user, BindingResult bindingResult) {

        UserEntity userEntity = UserEntity.builder()
                .name(user.getUserName())
                .gender(user.getGender())
                .age(user.getAge())
                .email(user.getEmail())
                .phone(user.getPhone())
                .vote(user.getVoteCount())
                .build();
        userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.OK).header("index", "ok").build();
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
