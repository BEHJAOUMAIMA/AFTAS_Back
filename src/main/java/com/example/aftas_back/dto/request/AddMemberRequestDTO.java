package com.example.aftas_back.dto.request;

import com.example.aftas_back.domain.Competition;
import com.example.aftas_back.domain.Member;
import com.example.aftas_back.domain.Ranking;

public record AddMemberRequestDTO(
        Long competitionId,
        Long memberId
) {
    public Ranking toRanking() {
        return Ranking.builder()
                .competition(Competition.builder().id(competitionId).build())
                .member(Member.builder().id(memberId).build())
                .build();
    }
}
