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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import javax.validation.Valid;
import java.util.*;
import java.util.function.Consumer;

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

        if (bindingResult.hasErrors()) {
            String msg = bindingResult.getFieldError().getDefaultMessage();
            throw new InvalidUserException(msg);
        }

        UserEntity userEntity = UserEntity.builder()
                .id(user.getId())
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

    @GetMapping("/{index}")
    public ResponseEntity<UserEntity> getUserByUserId(@PathVariable Integer index) {
        return ResponseEntity.ok(userRepository.findById(index).orElse(null));
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

    @DeleteMapping("/{index}")
    public ResponseEntity deleteUserById(@PathVariable Integer index) {
        userRepository.findById(index).ifPresent(o -> userRepository.deleteById(index));
        return ResponseEntity.ok().build();
    }
}
