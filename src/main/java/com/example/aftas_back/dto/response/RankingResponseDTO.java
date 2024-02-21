package com.example.aftas_back.dto.response;

import com.example.aftas_back.domain.Ranking;

public record RankingResponseDTO(
        Long userId,
        Long competitionId,
        Integer score,
        Integer rank
) {
    public static RankingResponseDTO fromRanking(Ranking ranking) {
        return new RankingResponseDTO(
                ranking.getUser().getId(),
                ranking.getCompetition().getId(),
                ranking.getRank(),
                ranking.getScore()
        );
    }
}
