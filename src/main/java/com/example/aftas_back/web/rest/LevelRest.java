package com.example.aftas_back.web.rest;

import com.example.aftas_back.domain.Level;
import com.example.aftas_back.dto.request.LevelRequestDTO;
import com.example.aftas_back.dto.response.LevelResponseDTO;
import com.example.aftas_back.handler.response.ResponseMessage;
import com.example.aftas_back.service.LevelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/levels")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('MANAGER','JURY')")
public class LevelRest {

    private final LevelService levelService;
    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_LEVEL') and hasAnyRole('JURY', 'MANAGER')")
    public ResponseEntity<List<LevelResponseDTO>> getAllLevels() {

        List<Level> levels = levelService.findAll();
        List<LevelResponseDTO> levelResponseDTOS = levels.stream()
                .map(LevelResponseDTO::fromLevel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(levelResponseDTOS);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_LEVEL') and hasAnyRole('JURY', 'MANAGER')")
    public ResponseEntity<?> getLevelById(@PathVariable Long id) {

        Optional<Level> level = levelService.findById(id);

        if (level.isEmpty()) {
            return ResponseMessage.notFound("Level not found with ID: " + id);
        }

        LevelResponseDTO levelResponseDTO = LevelResponseDTO.fromLevel(level.get());

        return ResponseEntity.ok(levelResponseDTO);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('CREATE_LEVEL') and hasAnyRole('JURY', 'MANAGER')")
    public ResponseEntity<ResponseMessage> addLevel(@Valid @RequestBody LevelRequestDTO levelRequestDTO) {
        Level level = levelService.save(levelRequestDTO.toLevel());
        if(level == null) {
            return ResponseMessage.badRequest("Level not created");
        }else {
            return ResponseMessage.created("Level created successfully", level);
        }
    }

    @PutMapping("/update/{levelId}")
    @PreAuthorize("hasAuthority('UPDATE_LEVEL') and hasAnyRole('JURY', 'MANAGER')")
    public ResponseEntity<ResponseMessage> updateLevel(@PathVariable Long levelId, @Valid @RequestBody LevelRequestDTO levelRequestDTO) {

        Level updatedLevel = levelRequestDTO.toLevel();

        Level level = levelService.update(updatedLevel, levelId);

        return ResponseEntity.ok(ResponseMessage.created("Competition updated successfully", level).getBody());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_LEVEL') and hasRole('MANAGER')")
    public ResponseEntity<?> deleteLevel(@PathVariable Long id) {
        Optional<Level> existingLevel = levelService.findById(id);

        if (existingLevel.isEmpty()) {
            return ResponseMessage.notFound("Level not found with ID: " + id);
        }

        levelService.delete(id);

        return ResponseMessage.ok("Level deleted successfully with ID: " + id, null);
    }
}
