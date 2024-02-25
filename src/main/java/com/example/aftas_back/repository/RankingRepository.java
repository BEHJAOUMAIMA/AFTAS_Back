package com.example.aftas_back.repository;


import com.example.aftas_back.domain.Competition;
import com.example.aftas_back.domain.RankId;
import com.example.aftas_back.domain.Ranking;
import com.example.aftas_back.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, RankId> {
    List<Ranking> findByCompetitionId(Long competitionId);
    List<Ranking> getRankingByUser(User member);
    List<Ranking> getRankingByCompetition(Competition competition);
    Ranking getRankingByUserAndCompetition(Optional<User> member, Competition competition);
    @Query("SELECT MAX(r.position) FROM Ranking r WHERE r.id.competitionId = :competitionId")
    Integer findMaxRankByCompetitionId(@Param("competitionId") Long competitionId);

}