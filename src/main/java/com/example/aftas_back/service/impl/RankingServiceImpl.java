package com.example.aftas_back.service.impl;

import com.example.aftas_back.domain.Competition;
import com.example.aftas_back.domain.RankId;
import com.example.aftas_back.domain.Ranking;
import com.example.aftas_back.domain.User;
import com.example.aftas_back.handler.exception.OperationException;
import com.example.aftas_back.repository.RankingRepository;
import com.example.aftas_back.service.CompetitionService;
import com.example.aftas_back.service.MemberService;
import com.example.aftas_back.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final RankingRepository rankingRepository;
    private final MemberService memberService;
    private final CompetitionService competitionService;


    @Override
    public Ranking save(Ranking ranking) {
        Long competitionId = ranking.getCompetition().getId();
        Long userId = ranking.getUser().getId();

        Competition competition = competitionService.findById(competitionId)
                .orElseThrow(() -> new OperationException("Competition id " + competitionId + " not found"));

        User user = memberService.findById(userId)
                .orElseThrow(() -> new OperationException("Member id " + userId + " not found"));

        if (competition.getRankings().stream().anyMatch(r -> r.getUser().equals(user))) {
            throw new OperationException("Member id " + userId + " is already registered for the competition");
        }

        if(competition.getStartTime().isBefore(competition.getStartTime().minusHours(24))){
            throw new OperationException("Competition id " + competitionId + " is closed for registration");
        }

        Integer remainingParticipants = competition.getNumberOfParticipants();

        if (remainingParticipants <= 0) {
            throw new OperationException("No more available slots for participants in the competition");
        }
        competition.setNumberOfParticipants(remainingParticipants - 1);

        competitionService.update(competition, competitionId);

        Integer maxRank = rankingRepository.findMaxRankByCompetitionId(competitionId);

        ranking.setRank(maxRank != null ? maxRank + 1 : 1);

        ranking.setScore(0);
        ranking.setCompetition(competition);
        ranking.setUser(user);

        RankId rankId = new RankId();
        rankId.setCompetitionId(competitionId);
        rankId.setUserId(userId);
        ranking.setId(rankId);

        return rankingRepository.save(ranking);
    }

    @Override
    public List<Ranking> findAll() {
        return rankingRepository.findAll();
    }

    @Override
    public Optional<Ranking> findById(RankId id) {
        return rankingRepository.findById(id);
    }

    @Override
    public List<Ranking> findByMember(Long member) {
        return null;
    }

    @Override
    public List<Ranking> findByCompetition(String competition) {
        Competition competition1 = competitionService.getByCode(competition);
        return rankingRepository.getRankingByCompetition(competition1);
    }

    @Override
    public Ranking findByMemberAndCompetition(Long member, String competition) {
        Optional<User> user = memberService.findById(member);
        Competition competition1 = competitionService.getByCode(competition);
        return rankingRepository.getRankingByMemberAndCompetition(user, competition1);
    }

    @Override
    public List<Ranking> sortParticipantsByScore(String competition) {
        List<Ranking> rankings = findByCompetition(competition).stream().sorted(Comparator.comparing(Ranking::getScore).reversed()).toList();
        if (!rankings.isEmpty()){
            for (int i = 0; i < rankings.size(); i++) {
                rankings.get(i).setRank(i+1);
                update(rankings.get(i));
            }
            return rankings;
        }
        return null;
    }

    @Override
    public Ranking update(Ranking rankingUpdated) {
        Ranking existingRanking = findByMemberAndCompetition(rankingUpdated.getUser().getId(), rankingUpdated.getCompetition().getCode());
        if (existingRanking != null){
            existingRanking.setRank(rankingUpdated.getRank());
            existingRanking.setScore(rankingUpdated.getScore());
            return rankingRepository.save(existingRanking);
        }
        return null;
    }

    @Override
    public void delete(Ranking ranking) {
        rankingRepository.delete(ranking);
    }

    @Override
    public List<Ranking> getPodium(String competitionCode) {
        List<Ranking> rankings = sortParticipantsByScore(competitionCode);

        if (rankings != null && rankings.size() >= 3) {
            return rankings.subList(0, 3);
        }
        return rankings;
    }


}
