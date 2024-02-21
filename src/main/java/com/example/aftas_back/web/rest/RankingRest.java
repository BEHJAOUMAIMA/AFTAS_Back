package com.example.aftas_back.web.rest;

import com.example.aftas_back.domain.RankId;
import com.example.aftas_back.domain.Ranking;
import com.example.aftas_back.dto.request.AddMemberRequestDTO;
import com.example.aftas_back.dto.response.RankingResponseDTO;
import com.example.aftas_back.handler.response.ResponseMessage;
import com.example.aftas_back.service.RankingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ranking")
@RequiredArgsConstructor
public class RankingRest {

    private final RankingService rankingService;
    @GetMapping
    public ResponseEntity<List<RankingResponseDTO>> getAllRankings() {
        List<Ranking> rankings = rankingService.findAll();
        List<RankingResponseDTO> rankingResponseDTOS = rankings.stream()
                .map(RankingResponseDTO::fromRanking)
                .toList();

        return ResponseEntity.ok(rankingResponseDTOS);
    }

    @GetMapping("/{competitionId}/{memberId}")
    public ResponseEntity<?> getRankingById(@PathVariable Long competitionId, @PathVariable Long memberId) {
        RankId id = new RankId(competitionId, memberId);
        Optional<Ranking> ranking = rankingService.findById(id);

        if (ranking.isEmpty()) {
            return ResponseMessage.notFound("Ranking not found with ID: " + id);
        }

        RankingResponseDTO rankingResponseDTO = RankingResponseDTO.fromRanking(ranking.get());
        return ResponseEntity.ok(rankingResponseDTO);
    }

    @PostMapping("/register/member")
    public ResponseEntity<?> registerMemberForCompetition(@Valid @RequestBody AddMemberRequestDTO addMemberRequestDTO) {
        Ranking ranking = rankingService.save(addMemberRequestDTO.toRanking());
        return ResponseMessage.ok("Member registered successfully", new RankingResponseDTO(ranking.getUser().getId(), ranking.getCompetition().getId(), ranking.getScore(), ranking.getRank()));
    }

    @PutMapping("/update/{competitionId}/{memberId}")
    public ResponseEntity<?> updateRanking(
            @PathVariable Long competitionId,
            @PathVariable Long memberId,
            @RequestBody AddMemberRequestDTO rankingDTO) {

        RankId id = new RankId(competitionId, memberId);

        Optional<Ranking> existingRankingOptional = rankingService.findById(id);

        if (existingRankingOptional.isPresent()) {
            Ranking existingRanking = existingRankingOptional.get();

            if (rankingDTO.toRanking().getScore() != null) {
                existingRanking.setScore(rankingDTO.toRanking().getScore());
            }

            if (rankingDTO.toRanking().getRank() != null) {
                existingRanking.setRank(rankingDTO.toRanking().getRank());
            }

            Ranking updatedRanking = rankingService.save(existingRanking);

            return ResponseEntity.ok(RankingResponseDTO.fromRanking(updatedRanking));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{competitionId}/{memberId}")
    public ResponseEntity<?> deleteRanking(
            @PathVariable Long competitionId,
            @PathVariable Long memberId) {

        RankId id = new RankId(competitionId, memberId);

        Optional<Ranking> existingRankingOptional = rankingService.findById(id);

        if (existingRankingOptional.isPresent()) {
            Ranking existingRanking = existingRankingOptional.get();

            rankingService.delete(existingRanking);

            return ResponseEntity.ok("Ranking deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}