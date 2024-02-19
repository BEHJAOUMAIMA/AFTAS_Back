package com.example.aftas_back.dto.request;

import com.example.aftas_back.domain.Competition;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;

public record CompetitionRequestDTO(
        @NotNull(message = "Date cannot be null")
        @FutureOrPresent(message = "Date must be in the Present or the future")
        LocalDate date,
        @NotNull(message = "Start time cannot be null")
        LocalTime startTime,
        @NotNull(message = "End time cannot be null")
        LocalTime endTime,
        @NotNull(message = "Number of participants cannot be null")
        @Min(value = 1, message = "Number of participants must be greater than 0")
        Integer numberOfParticipants,
        @NotBlank(message = "Location cannot be blank")
        @NotNull(message = "Location cannot be null")
        String Location,
        @NotNull(message = "Amount cannot be null")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0.0")
        Double amount
) {
    public Competition toCompetition() {
        return Competition.builder()
                .date(this.date)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .numberOfParticipants(this.numberOfParticipants)
                .location(this.Location)
                .amount(this.amount)
                .build();
    }
}
