package com.example.aftas_back.service.impl;


import com.example.aftas_back.domain.Level;
import com.example.aftas_back.handler.exception.OperationException;
import com.example.aftas_back.repository.LevelRepository;
import com.example.aftas_back.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;


    @Override
    public Level save(Level level) {
        Integer maxPoints = levelRepository.findMaxPoints();

        if (levelRepository.existsByCode(level.getCode())) {
            throw new OperationException("Level with code " + level.getCode() + " already exists");
        }
        if (maxPoints != null && level.getPoints() <= maxPoints) {
            throw new OperationException("Point must be greater than " + maxPoints);
        }

        return levelRepository.save(level);
    }

    @Override
    public List<Level> findAll() {
        List<Level> levels = levelRepository.findAll();

        if (levels.isEmpty()) {
            throw new OperationException("No Levels found");
        }

        return levels;
    }

    @Override
    public Optional<Level> findById(Long id) {
        if (id <= 0) {
            throw new OperationException("ID must be greater than 0");
        }

        return levelRepository.findById(id);
    }

    @Override
    public Level update(Level levelUpdated, Long id) {
        Level existingLevel = levelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Level with ID " + id + " not found"));

        int index = id.intValue();
        List<Level> levels = levelRepository.findAll();

        if (index > 0 && levelUpdated.getPoints() < levels.get(index - 1).getPoints()) {
            throw new OperationException("Point must be greater than or equal to " + levels.get(index - 1).getPoints());
        }

        if (index < levels.size() - 1 && levelUpdated.getPoints() > levels.get(index + 1).getPoints()) {
            throw new OperationException("Point must be less than or equal to " + levels.get(index + 1).getPoints());
        }

        existingLevel.setDescription(levelUpdated.getDescription());
        existingLevel.setPoints(levelUpdated.getPoints());

        return levelRepository.save(existingLevel);
    }

    @Override
    public void delete(Long id) {
        if (id <= 0) {
            throw new OperationException("ID must be greater than 0");
        }

        Optional<Level> level = levelRepository.findById(id);
        if (level.isEmpty()) {
            throw new OperationException("Level not found with ID: " + id);
        }

        levelRepository.deleteById(id);
    }
}
