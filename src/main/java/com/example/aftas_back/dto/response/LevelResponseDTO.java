package com.example.aftas_back.dto.response;
import com.example.aftas_back.domain.Level;

public record LevelResponseDTO(
        Long id,
        Integer code,
        String description,
        Integer points
) {
    public static LevelResponseDTO fromLevel(Level level){
        return new LevelResponseDTO(
                level.getId(),
                level.getCode(),
                level.getDescription(),
                level.getPoints()
        );
    }
}
