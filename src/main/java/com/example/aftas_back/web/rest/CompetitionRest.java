package com.example.aftas_back.web.rest;

import com.example.aftas_back.domain.Competition;
import com.example.aftas_back.dto.request.CompetitionRequestDTO;
import com.example.aftas_back.dto.response.CompetitionResponseDTO;
import com.example.aftas_back.handler.response.ResponseMessage;
import com.example.aftas_back.service.CompetitionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/competitions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('MANAGER','JURY', 'MEMBER')")
public class CompetitionRest {

    private final CompetitionService competitionService;

    @PreAuthorize("hasAuthority('VIEW_COMPETITIONS') and hasAnyRole('MEMBER', 'JURY', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<CompetitionResponseDTO>> getAllCompetitions() {
        List<Competition> competitions = competitionService.findAll();
        List<CompetitionResponseDTO> competitionResponseDTOS = competitions.stream()
                .map(CompetitionResponseDTO::fromCompetition)
                .toList();

        return ResponseEntity.ok(competitionResponseDTOS);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_COMPETITIONS') and hasAnyRole('MEMBER', 'JURY', 'MANAGER')")
    public ResponseEntity<?> getCompetitionById(@PathVariable Long id) {
        Optional<Competition> competition = competitionService.findById(id);

        if (competition.isEmpty()) {
            return ResponseMessage.notFound("Competition not found with ID: " + id);
        }

        CompetitionResponseDTO competitionResponseDTO = CompetitionResponseDTO.fromCompetition(competition.get());
        return ResponseEntity.ok(competitionResponseDTO);
    }
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('CREATE_COMPETITIONS') and hasAnyRole('JURY', 'MANAGER')")
    public ResponseEntity<ResponseMessage> addCompetition(@Valid @RequestBody CompetitionRequestDTO competitionRequestDTO) {
        Competition competition = competitionService.save(competitionRequestDTO.toCompetition());
        if(competition == null) {
            return ResponseMessage.badRequest("Competition not created");
        }else {
            return ResponseMessage.created("Competition created successfully", competition);
        }
    }
    @PutMapping("/update/{competitionId}")
    @PreAuthorize("hasAuthority('UPDATE_COMPETITIONS') and hasAnyRole('JURY', 'MANAGER')")
    public ResponseEntity<ResponseMessage> updateCompetition(@PathVariable Long competitionId, @Valid @RequestBody CompetitionRequestDTO updatedCompetitionRequestDTO) {

        Competition updatedCompetition = updatedCompetitionRequestDTO.toCompetition();
        Competition competition = competitionService.update(updatedCompetition, competitionId);

        return ResponseEntity.ok(ResponseMessage.created("Competition updated successfully", competition).getBody());
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('UPDATE_COMPETITIONS') and hasRole('MANAGER')")
    public ResponseEntity<?> deleteCompetition(@PathVariable Long id) {
        Optional<Competition> existingCompetition = competitionService.findById(id);

        if (existingCompetition.isEmpty()) {
            return ResponseMessage.notFound("Competition not found with ID: " + id);
        }

        competitionService.delete(id);

        return ResponseMessage.ok("Competition deleted successfully with ID: " + id, null);
    }

}