package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.exception.InvalidUserException;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Configuration
public class UserService {
    @Bean
    public UserService UserService() {
        return new UserService();
    }

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void deleteById(int id) {
        if (!userRepository.existsById(id)) {
            throw new InvalidUserException("user id is not exists");
        }
        userRepository.deleteById(id);
    }

    public void addUser(User user) {
        userRepository.save(Optional.ofNullable(user).orElseThrow(() -> new InvalidUserException("user is null")).build());
    }

    public UserEntity findById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new InvalidUserException("user is not exists"));
    }
}
