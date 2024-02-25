package com.example.aftas_back.web.rest;

import com.example.aftas_back.domain.User;
import com.example.aftas_back.dto.request.MemberRequestDTO;
import com.example.aftas_back.dto.response.MemberResponseDTO;
import com.example.aftas_back.handler.response.ResponseMessage;
import com.example.aftas_back.service.MemberService;
import com.example.aftas_back.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('MANAGER', 'JURY')")
public class MemberRest {
    private final MemberService memberService;
    private final UserService userService;
    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_USERS') and hasAnyRole('JURY', 'MANAGER')")
    public ResponseEntity<List<MemberResponseDTO>> getAllMembers() {
        List<User> members = memberService.findAll();
        List<MemberResponseDTO> memberDTOs = members.stream()
                .map(MemberResponseDTO::fromMember)
                .collect(Collectors.toList());

        return ResponseEntity.ok(memberDTOs);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('CREATE_USERS') and hasRole('MANAGER')")
    public ResponseEntity<ResponseMessage> addMember(@Valid @RequestBody MemberRequestDTO memberDTO) {
        User savedMember = memberService.save(memberDTO.toMember());

        if (savedMember == null) {
            return ResponseMessage.badRequest("Member not created");
        } else {
            return ResponseMessage.created("Member created successfully", savedMember);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_USERS') and hasAnyRole('JURY', 'MANAGER')")
    public ResponseEntity<?> getMemberById(@PathVariable Long id) {
        Optional<User> member = memberService.findById(id);

        if (member.isEmpty()) {
            return ResponseMessage.notFound("Member not found with ID: " + id);
        }

        MemberResponseDTO memberDTO = MemberResponseDTO.fromMember(member.get());
        return ResponseEntity.ok(memberDTO);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('UPDATE_USERS') and hasRole('MANAGER')")
    public ResponseEntity<?> updateMember(@PathVariable Long id, @Valid @RequestBody MemberRequestDTO memberDTO) {
        Optional<User> existingMember = memberService.findById(id);

        if (existingMember.isEmpty()) {
            return ResponseMessage.notFound("Member not found with ID: " + id);
        }

        User updatedMember = memberDTO.toMember();
        updatedMember.setId(id);

        User result = memberService.update(updatedMember, id);

        if (result == null) {
            return ResponseMessage.badRequest("Failed to update member with ID: " + id);
        }

        MemberResponseDTO updatedMemberDTO = MemberResponseDTO.fromMember(result);
        return ResponseMessage.ok("Member updated successfully with ID: " + id, updatedMemberDTO);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_USERS') and hasRole('MANAGER')")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        Optional<User> existingMember = memberService.findById(id);

        if (existingMember.isEmpty()) {
            return ResponseMessage.notFound("Member not found with ID: " + id);
        }

        memberService.delete(id);

        return ResponseMessage.ok("Member deleted successfully with ID: " + id, null);
    }

    @PutMapping("/{userId}/enable")
    @PreAuthorize("hasAuthority('ENABLE_ACCOUNT') and hasRole('MANAGER')")
    public ResponseEntity<?> enableUser(@PathVariable Long userId) {
        User enabledUser = userService.enableUser(userId);
        return ResponseEntity.ok(enabledUser);
    }

}
