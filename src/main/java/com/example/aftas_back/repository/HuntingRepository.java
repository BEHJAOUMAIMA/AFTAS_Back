package com.example.aftas_back.repository;

import com.example.aftas_back.domain.Competition;
import com.example.aftas_back.domain.Hunting;
import com.example.aftas_back.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HuntingRepository extends JpaRepository<Hunting, Long> {
    Hunting getHuntingsById(Long id);
    List<Hunting> getHuntingsByCompetition(Competition competition);
    List<Hunting> getHuntingsByMember(User member);
    List<Hunting> getHuntingsByCompetitionAndMember(Competition competition, User member);

}
