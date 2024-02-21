package com.example.aftas_back.dto.response;

import com.example.aftas_back.domain.Hunting;

public record HuntingResponseDTO(
        String fish,
        Long User,
        String competition,
        Integer numberOfFish
) {
    public static HuntingResponseDTO fromHunting(Hunting hunting){
        return new HuntingResponseDTO(
                hunting.getFish() != null ? hunting.getFish().getName() : null,
                hunting.getUser() != null ? hunting.getUser().getId() : null,
                hunting.getCompetition() != null ? hunting.getCompetition().getCode() : null,
                hunting.getNumberOfFish()
        );
    }
}
