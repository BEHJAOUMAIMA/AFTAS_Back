package com.example.aftas_back.service.impl;

import com.example.aftas_back.domain.Fish;
import com.example.aftas_back.domain.Level;
import com.example.aftas_back.handler.exception.OperationException;
import com.example.aftas_back.repository.FishRepository;
import com.example.aftas_back.service.FishService;
import com.example.aftas_back.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FishServiceImpl implements FishService {

    private final FishRepository fishRepository;
    private final LevelService levelService;




    @Override
    public Fish save(Fish fish) {

        if(fishRepository.findByName(fish.getName()) != null) {
            throw new OperationException("Fish name " + fish.getName() + " already exist");
        }

        if(levelService.findById(fish.getLevel().getId()).isEmpty()) {
            throw new OperationException("Level id " + fish.getLevel().getId() + " not found");
        }

        return fishRepository.save(fish);
    }

    @Override
    public List<Fish> findAll() {

        List<Fish> fishes = fishRepository.findAll();

        if (fishes.isEmpty()) {
            throw new OperationException("No Fishes found");
        }

        return fishes;
    }

    @Override
    public Optional<Fish> findById(Long id) {
        if (id <= 0) {
            throw new OperationException("ID must be greater than 0");
        }
        return fishRepository.findById(id);
    }

    @Override
    public Fish update(Fish fishUpdated, Long id) {
        Optional<Fish> existingFishOptional = findById(id);
        Fish existingFish = existingFishOptional.orElseThrow(() ->
                new OperationException("Fish with ID " + id + " not found"));

        if (fishUpdated.getName() != null) {
            existingFish.setName(fishUpdated.getName());
        }

        if (fishUpdated.getAverageWeight() != null) {
            existingFish.setAverageWeight(fishUpdated.getAverageWeight());
        }

        Level updatedLevel = levelService.findById(fishUpdated.getLevel().getId())
                .orElseThrow(() -> new OperationException("Level with ID " + fishUpdated.getLevel().getId() + " not found"));

        if (!existingFish.getLevel().equals(updatedLevel)) {
            existingFish.setLevel(updatedLevel);
        }

        return fishRepository.save(existingFish);
    }


    @Override
    public void delete(Long id) {
        if (id <= 0) {
            throw new OperationException("ID must be greater than 0");
        }

        Optional<Fish> fish = fishRepository.findById(id);
        if (fish.isEmpty()) {
            throw new OperationException("Fish not found with ID: " + id);
        }

        fishRepository.deleteById(id);
    }

    @Override
    public Fish getByName(String name) {
        return fishRepository.findByName(name);
    }

    @Override
    public List<Fish> getByLevel(Integer level) {
        return null;
    }
}
