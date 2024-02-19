package com.example.aftas_back.service;

import com.example.aftas_back.domain.Fish;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FishService {
    Fish save(Fish fish);
    List<Fish> findAll();
    Optional<Fish> findById(Long id);
    Fish update(Fish fishUpdated, Long id);
    void delete(Long id);
    Fish getByName(String name);
    List<Fish> getByLevel(Integer level);

}