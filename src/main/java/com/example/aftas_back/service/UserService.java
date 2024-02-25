package com.example.aftas_back.service;


import com.example.aftas_back.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User addUser(User user);
    List<User> getAll();
    Optional<User> getById(Long id);
    User updateUser(User userUpdated, Long id);
    void deleteUser(Long id);
    User getUserById(Long id);
    User enableUser(Long userId);

}
