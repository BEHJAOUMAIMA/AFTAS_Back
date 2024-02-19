package com.example.aftas_back.service;

import com.example.aftas_back.domain.Competition;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface CompetitionService {
    Competition save(Competition competition);
    List<Competition> findAll();
    Optional<Competition> findById(Long id);
    Competition update(Competition competitionUpdated, Long id);
    void delete(Long id);
    String generateCode(String location, LocalDate date);
    Competition getByCode(String code);

}