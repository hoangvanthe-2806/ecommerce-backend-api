package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<User> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public User create(User user) {
        validateUnique(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(Long id, User user) {
        return repo.findById(id).map(existing -> {
            if (user.getFirstName() != null) existing.setFirstName(user.getFirstName());
            if (user.getLastName() != null) existing.setLastName(user.getLastName());
            if (user.getUsername() != null) {
                if (repo.findByUsername(user.getUsername())
                        .filter(u -> !u.getId().equals(id)).isPresent()) {
                    throw new IllegalArgumentException("tên đăng nhập đã được sử dụng");
                }
                existing.setUsername(user.getUsername());
            }
            if (user.getEmail() != null) {
                if (repo.findByEmail(user.getEmail())
                        .filter(u -> !u.getId().equals(id)).isPresent()) {
                    throw new IllegalArgumentException("Email đã được sử dụng");
                }
                existing.setEmail(user.getEmail());
            }
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                existing.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            return repo.save(existing);
        });
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    private void validateUnique(User user) {
        repo.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("Email đã được sử dụng");
        });
        repo.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new IllegalArgumentException("tên đăng nhập đã được sử dụng");
        });
    }
}