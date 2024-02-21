package com.example.aftas_back.service;

import com.example.aftas_back.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MemberService{
    User save(User member);
    List<User> findAll();
    Optional<User> findById(Long id);
    User update(User memberUpdated, Long id);
    void delete(Long id);
    User getById(Long id);
    List<User> searchMembers(Long id, String name, String familyName);

}