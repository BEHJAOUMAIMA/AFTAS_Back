package com.example.aftas_back.service.impl;

import com.example.aftas_back.domain.User;
import com.example.aftas_back.repository.UserRepository;
import com.example.aftas_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User addUser(User userRequest) {
        Optional<User> existingUser = userRepository.findByEmail(userRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = User.builder()
                .name(userRequest.getName())
                .familyName(userRequest.getFamilyName())
                .nationality(userRequest.getNationality())
                .identityDocumentType(userRequest.getIdentityDocumentType())
                .identityNumber(userRequest.getIdentityNumber())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(userRequest.getRole())
                .accessionDate(LocalDate.now().atStartOfDay())
                .enabled(true)
                .build();

        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User userUpdated, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setName(userUpdated.getName());
        user.setFamilyName(userUpdated.getFamilyName());
        user.setNationality(userUpdated.getNationality());
        user.setIdentityDocumentType(userUpdated.getIdentityDocumentType());
        user.setIdentityNumber(userUpdated.getIdentityNumber());
        user.setEmail(userUpdated.getEmail());
        user.setPassword(passwordEncoder.encode(userUpdated.getPassword()));
        user.setRole(userUpdated.getRole());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public User enableUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setEnabled(true);

        return userRepository.save(user);
    }
}
