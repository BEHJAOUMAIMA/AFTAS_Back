package com.example.aftas_back.web.rest;

import com.example.aftas_back.domain.Hunting;
import com.example.aftas_back.dto.request.HuntingRequestDTO;
import com.example.aftas_back.dto.request.UpdateHuntingRequestDTO;
import com.example.aftas_back.dto.response.HuntingResponseDTO;
import com.example.aftas_back.handler.response.ResponseMessage;
import com.example.aftas_back.service.HuntingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hunting")
@RequiredArgsConstructor
public class HuntingRest {
    private final HuntingService huntingService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Hunting> hunts = huntingService.getAll();
        if (hunts.isEmpty()) {
            return ResponseMessage.notFound("No hunting was found");
        }
        else{
            return ResponseMessage.ok("Hunts fetched successfully", hunts.stream().map(HuntingResponseDTO::fromHunting).toList());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHuntingById(@PathVariable Long id){
        Hunting hunting = huntingService.getById(id);
        if(hunting == null){
            return ResponseMessage.notFound("Hunting not found");
        }
        else{
            return ResponseMessage.ok("Success", HuntingResponseDTO.fromHunting(hunting));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Valid HuntingRequestDTO hunting){
        Hunting hunting1 = huntingService.save(hunting.toHunting(), hunting.weight());
        if (hunting1 == null) {
            return ResponseMessage.badRequest("bad request");
        }
        else{
            return ResponseMessage.created("Hunting created successfully", HuntingResponseDTO.fromHunting(hunting1));
        }
    }

    @GetMapping("/member/{member}")
    public ResponseEntity<?> getHuntingByMember(@PathVariable Long member){
        List<Hunting> hunts = huntingService.getByMember(member);
        if(hunts == null) {
            return ResponseMessage.notFound("Hunting not found");
        }
        else {
            return ResponseMessage.ok("Success", hunts.stream().map(HuntingResponseDTO::fromHunting).toList());
        }
    }

    @GetMapping("/competition/{competition}")
    public ResponseEntity<?> getHuntingByCompetition(@PathVariable String competition){
        List<Hunting> hunts = huntingService.getByCompetition(competition);
        if(hunts == null) {
            return ResponseMessage.notFound("Hunting not found");
        }
        else {
            return ResponseMessage.ok("Success", hunts.stream().map(HuntingResponseDTO::fromHunting).toList());
        }
    }

    @GetMapping("/competition_and_member/{competition}/{member}")
    public ResponseEntity<?> getHuntingByCompetitionAndMember(@PathVariable String competition, @PathVariable Long member){
        List<Hunting> hunts = huntingService.getByCompetitionAndMember(competition, member);
        if(hunts == null) {
            return ResponseMessage.notFound("Hunting not found");
        }
        else {
            return ResponseMessage.ok("Success", hunts.stream().map(HuntingResponseDTO::fromHunting).toList());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody UpdateHuntingRequestDTO hunting, @PathVariable Long id){
        Hunting hunting1 = huntingService.update(hunting.toHunting(), id);
        if (hunting1 == null) {
            return ResponseMessage.badRequest("bad request");
        }
        else {
            return ResponseMessage.created("Hunting updated successfully", HuntingResponseDTO.fromHunting(hunting1));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Hunting hunting = huntingService.getById(id);
        if (hunting == null) {
            return ResponseMessage.notFound("Hunting not found");
        }
        else {
            huntingService.delete(id);
            return ResponseMessage.ok("Hunting deleted successfully", HuntingResponseDTO.fromHunting(hunting));
        }
    }
}