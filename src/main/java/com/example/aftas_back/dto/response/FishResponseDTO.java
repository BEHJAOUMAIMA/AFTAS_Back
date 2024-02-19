package com.example.aftas_back.dto.response;

import com.example.aftas_back.domain.Fish;
import com.example.aftas_back.domain.Level;

public record FishResponseDTO(
        Long id,
        String name,
        Double averageWeight,
        Level level
) {
    public static FishResponseDTO fromFish(Fish fish){
        return new FishResponseDTO(
                fish.getId(),
                fish.getName(),
                fish.getAverageWeight(),
                fish.getLevel()
        );
    }
}
