package com.example.aftas_back.service;

import com.example.aftas_back.domain.RankId;
import com.example.aftas_back.domain.Ranking;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RankingService {
    Ranking save(Ranking ranking);
    List<Ranking> findAll();
    Optional<Ranking> findById(RankId id);
    List<Ranking> findByMember(Long member);
    List<Ranking> findByCompetition(String competition);
    Ranking findByMemberAndCompetition(Long member, String competition);
    List<Ranking> sortParticipantsByScore(String competition);
    Ranking update(Ranking rankingUpdated);
    void delete(Ranking ranking);
    List<Ranking> getPodium(String competitionCode);

}
