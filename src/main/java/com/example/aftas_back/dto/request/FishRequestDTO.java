package com.example.aftas_back.dto.request;

import com.example.aftas_back.domain.Fish;
import com.example.aftas_back.domain.Level;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record FishRequestDTO(
        @NotBlank
        @NotNull(message = "the Fish name cannot be null")
        @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
        String name,
        @Positive(message = "Average weight must be a positive number")
        Double averageWeight,
        @NotNull(message = "the Level Id cannot be null")
        Long levelId
) {
    public Fish toFish(){
        return Fish.builder()
                .name(name)
                .averageWeight(averageWeight)
                .level(Level.builder().id(levelId).build())
                .build();
    }
}
