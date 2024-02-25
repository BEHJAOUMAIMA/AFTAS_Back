package com.example.aftas_back.web.rest;

import com.example.aftas_back.domain.Fish;
import com.example.aftas_back.dto.request.FishRequestDTO;
import com.example.aftas_back.dto.response.FishResponseDTO;
import com.example.aftas_back.handler.response.ResponseMessage;
import com.example.aftas_back.service.FishService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fishes")
@PreAuthorize("hasAnyRole('MANAGER','JURY')")
public class FishRequest {
    private final FishService fishService;

    public FishRequest(FishService fishService) {
        this.fishService = fishService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_FISHES') and hasAnyRole('JURY', 'MANAGER')")
    public ResponseEntity<List<FishResponseDTO>> getAllFishes() {

        List<Fish> fishes = fishService.findAll();
        List<FishResponseDTO> fishResponseDTOS = fishes.stream()
                .map(FishResponseDTO::fromFish)
                .collect(Collectors.toList());

        return ResponseEntity.ok(fishResponseDTOS);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_FISHES') and hasAnyRole('JURY', 'MANAGER')")
    public ResponseEntity<?> getFishById(@PathVariable Long id) {

        Optional<Fish> fish = fishService.findById(id);

        if (fish.isEmpty()) {
            return ResponseMessage.notFound("Fish not found with ID: " + id);
        }

        FishResponseDTO fishResponseDTO = FishResponseDTO.fromFish(fish.get());

        return ResponseEntity.ok(fishResponseDTO);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('VIEW_FISHES') and hasAnyRole('JURY', 'MANAGER')")
    public ResponseEntity<ResponseMessage> addFish(@Valid @RequestBody FishRequestDTO fishRequestDTO) {
        Fish fish = fishService.save(fishRequestDTO.toFish());
        if(fish == null) {
            return ResponseMessage.badRequest("Fish not created");
        }else {
            return ResponseMessage.created("Fish created successfully", fish);
        }
    }

    @PutMapping("/update/{fishId}")
    @PreAuthorize("hasAuthority('VIEW_FISHES') and hasAnyRole('JURY', 'MANAGER')")
    public ResponseEntity<ResponseMessage> updateFish(@PathVariable Long fishId, @Valid @RequestBody FishRequestDTO fishRequestDTO) {
        Fish updatedFish = fishService.update(fishRequestDTO.toFish(), fishId);
        return ResponseEntity.ok(ResponseMessage.created("Fish updated successfully", updatedFish).getBody());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('VIEW_FISHES') and hasRole('MANAGER')")
    public ResponseEntity<?> deleteFish(@PathVariable Long id) {
        Optional<Fish> existingFish = fishService.findById(id);

        if (existingFish.isEmpty()) {
            return ResponseMessage.notFound("Fish not found with ID: " + id);
        }

        fishService.delete(id);

        return ResponseMessage.ok("Fish deleted successfully with ID: " + id, null);
    }
}