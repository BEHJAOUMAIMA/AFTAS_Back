package com.example.aftas_back.dto.response;

import com.example.aftas_back.domain.Competition;

import java.time.LocalDate;
import java.time.LocalTime;
public record CompetitionResponseDTO(
        Long id,
        String code,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        Integer NumberOfParticipants,
        String Location,
        Double amount
) {
    public static CompetitionResponseDTO  fromCompetition(Competition competition) {
        return new CompetitionResponseDTO(
                competition.getId(),
                competition.getCode(),
                competition.getDate(),
                competition.getStartTime(),
                competition.getEndTime(),
                competition.getNumberOfParticipants(),
                competition.getLocation(),
                competition.getAmount()
        );
    }
}
