package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.exception.CommonError;
import com.thoughtworks.rslist.exception.InvalidUserException;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.service.RsEventService;
import com.thoughtworks.rslist.service.UserService;
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
    UserService userService;

    @Autowired
    RsEventService rsEventService;

    @PostMapping
    public ResponseEntity addUser(@RequestBody(required = false) @Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String msg = bindingResult.getFieldError().getDefaultMessage();
            throw new InvalidUserException(msg);
        }

        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.OK).header("index", "ok").build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getById(@PathVariable int id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> getUserList() {
        return ResponseEntity.ok(userList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable Integer id) {
        rsEventService.deleteByUserId(id);
        userService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity exceptionHandler(RuntimeException e) {
        CommonError commonError = new CommonError();
        commonError.setError(e.getMessage());

        return ResponseEntity.badRequest().body(commonError);
    }
}
