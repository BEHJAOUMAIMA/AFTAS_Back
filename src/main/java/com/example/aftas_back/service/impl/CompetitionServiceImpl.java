package com.example.aftas_back.service.impl;

import com.example.aftas_back.domain.Competition;
import com.example.aftas_back.handler.exception.OperationException;
import com.example.aftas_back.repository.CompetitionRepository;
import com.example.aftas_back.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    @Override
    public Competition save(Competition competition) {
        Competition existingCompetition = competitionRepository.findByDate(competition.getDate());
        if (existingCompetition != null){
            throw new OperationException("There is already a competition on " + competition.getDate());
        }
        if (competition.getStartTime().isAfter(competition.getEndTime())) {
            throw new OperationException("Start time must be before end time");
        }
        String code = generateCode(competition.getLocation(), competition.getDate());
        competition.setCode(code);
        return competitionRepository.save(competition);
    }
    @Override
    public List<Competition> findAll() {
        List<Competition> competitions = competitionRepository.findAll();
        if (competitions.isEmpty()) {
            throw new OperationException("No competitions found");
        }
        return competitions;
    }
    @Override
    public Optional<Competition> findById(Long id) {
        if (id <= 0) {
            throw new OperationException("ID must be greater than 0");
        }
        return competitionRepository.findById(id);
    }
    @Override
    public Competition update(Competition competitionUpdated, Long id) {
        Competition existingCompetition = competitionRepository.findById(id)
                .orElseThrow(() -> new OperationException("Competition not found"));

        Competition competitionWithSameDate = competitionRepository.findByDate(competitionUpdated.getDate());
        if (competitionWithSameDate != null && !competitionWithSameDate.getId().equals(id)) {
            throw new OperationException("There is already a competition on " + competitionUpdated.getDate());
        }

        if (competitionUpdated.getStartTime().isAfter(competitionUpdated.getEndTime())) {
            throw new OperationException("Start time must be before end time");
        }

        existingCompetition.setDate(competitionUpdated.getDate());
        existingCompetition.setStartTime(competitionUpdated.getStartTime());
        existingCompetition.setEndTime(competitionUpdated.getEndTime());
        existingCompetition.setNumberOfParticipants(competitionUpdated.getNumberOfParticipants());
        existingCompetition.setLocation(competitionUpdated.getLocation());
        existingCompetition.setAmount(competitionUpdated.getAmount());

        if (!existingCompetition.getDate().equals(competitionUpdated.getDate()) ||
                !existingCompetition.getLocation().equals(competitionUpdated.getLocation())) {
            String code = generateCode(competitionUpdated.getLocation(), competitionUpdated.getDate());
            existingCompetition.setCode(code);
        }

        return competitionRepository.save(existingCompetition);
    }
    @Override
    public void delete(Long id) {
        if (id <= 0) {
            throw new OperationException("ID must be greater than 0");
        }

        Optional<Competition> competitionOptional = competitionRepository.findById(id);
        if (competitionOptional.isEmpty()) {
            throw new OperationException("Competition not found with ID: " + id);
        }

        competitionRepository.deleteById(id);
    }
    @Override
    public String generateCode(String location, LocalDate date) {
        String locationCode = location.substring(0, 3).toLowerCase();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yy-MM-dd");
        String formattedDate = date.format(dateFormatter);

        return locationCode + "-" + formattedDate;
    }
    @Override
    public Competition getByCode(String code) {
        return competitionRepository.getCompetitionByCode(code);
    }
}
