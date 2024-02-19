package com.example.aftas_back.dto.request;

import com.example.aftas_back.domain.Competition;
import com.example.aftas_back.domain.Fish;
import com.example.aftas_back.domain.Hunting;
import com.example.aftas_back.domain.Member;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record HuntingRequestDTO(
        String fish,
        Long member,
        String competition,
        @Positive
        @NotNull
        Double weight
) {
    public Hunting toHunting() {
        return Hunting.builder()
                .competition(Competition.builder().code(competition).build())
                .member(Member.builder().id(member).build())
                .fish(Fish.builder().name(fish).build())
                .build();
    }
}
