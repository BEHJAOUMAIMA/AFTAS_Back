package com.example.aftas_back.service.impl;

import com.example.aftas_back.domain.*;
import com.example.aftas_back.repository.HuntingRepository;
import com.example.aftas_back.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HuntingServiceImpl implements HuntingService {
    private final HuntingRepository huntingRepository;
    private final FishService fishService;
    private final CompetitionService competitionService;
    private final MemberService memberService;
    private final RankingService rankingService;

    @Override
    public Hunting save(Hunting hunting, Double weight) {
        hunting.setFish(fishService.getByName(hunting.getFish().getName()));
        Ranking ranking = rankingService.findByMemberAndCompetition(hunting.getUser().getId(), hunting.getCompetition().getCode());
        if (weight >= hunting.getFish().getAverageWeight() && ranking != null){
            hunting.setCompetition(competitionService.getByCode(hunting.getCompetition().getCode()));
            hunting.setUser(memberService.findById(hunting.getUser().getId()).orElse(null));
            Hunting existingHunt = checkIfFishAlreadyHunted(hunting.getUser(), hunting.getCompetition(), hunting.getFish());
            ranking.setScore(ranking.getScore() + hunting.getFish().getLevel().getPoints());
            rankingService.update(ranking);
            if(existingHunt == null){
                hunting.setNumberOfFish(1);
                return huntingRepository.save(hunting);
            }
            existingHunt.setNumberOfFish(existingHunt.getNumberOfFish() + 1);
            return huntingRepository.save(existingHunt);

        }
        return null;
    }

    @Override
    public List<Hunting> getAll() {
        return huntingRepository.findAll();
    }

    @Override
    public Hunting getById(Long id) {
        return huntingRepository.getHuntingsById(id);
    }

    @Override
    public List<Hunting> getByCompetition(String codeCompetition) {
        return huntingRepository.getHuntingsByCompetition(competitionService.getByCode(codeCompetition));
    }

    @Override
    public List<Hunting> getByMember(Long memberId) {
        return huntingRepository.getHuntingsByMember(memberService.getById(memberId));
    }


    @Override
    public List<Hunting> getByCompetitionAndMember(String codeCompetition, Long memberId) {
        return huntingRepository.getHuntingsByCompetitionAndMember(competitionService.getByCode(codeCompetition), memberService.getById(memberId));
    }

    @Override
    public Hunting update(Hunting hunting, Long id) {
        Hunting existingHunting = getById(id);
        if (existingHunting != null){
            existingHunting.setNumberOfFish(hunting.getNumberOfFish());
            return huntingRepository.save(existingHunting);
        }
        return null;
    }

    @Override
    public Hunting checkIfFishAlreadyHunted(User member, Competition competition, Fish fish) {
        List<Hunting> allHunts = getAll();
        if (allHunts.isEmpty()) {
            return null;
        }
        List<Hunting> hunts = allHunts.stream()
                .filter(hunting -> hunting.getUser()
                        .equals(member) && hunting.getCompetition()
                        .equals(competition) && hunting.getFish()
                        .equals(fish)).toList();
        return hunts.isEmpty() ? null : hunts.get(0);
    }

    @Override
    public void delete(Long id) {
        Hunting hunting = getById(id);
        if (hunting != null){
            huntingRepository.delete(hunting);
        }
    }
}
