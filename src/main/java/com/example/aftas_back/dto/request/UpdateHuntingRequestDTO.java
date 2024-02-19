package com.example.aftas_back.dto.request;

import com.example.aftas_back.domain.Hunting;

public record UpdateHuntingRequestDTO(
        Integer numberOfFish

) {
    public Hunting toHunting() {
        return Hunting.builder()
                .NumberOfFish(numberOfFish)
                .build();
    }
}
