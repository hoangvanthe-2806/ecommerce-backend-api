package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.User;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User create(User user);
    Optional<User> update(Long id, User user);
    void deleteById(Long id);
}