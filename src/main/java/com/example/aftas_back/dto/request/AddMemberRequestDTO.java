package com.example.aftas_back.dto.request;

import com.example.aftas_back.domain.Competition;
import com.example.aftas_back.domain.Ranking;
import com.example.aftas_back.domain.User;

public record AddMemberRequestDTO(
        Long competitionId,
        Long userId
) {
    public Ranking toRanking() {
        return Ranking.builder()
                .competition(Competition.builder().id(competitionId).build())
                .user(User.builder().id(userId).build())
                .build();
    }
}
