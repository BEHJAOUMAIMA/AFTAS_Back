package com.example.aftas_back.dto.response;

import com.example.aftas_back.domain.User;
import com.example.aftas_back.domain.enums.IdentityDocumentType;

import java.time.LocalDateTime;

public record MemberResponseDTO(
        Long id,
        String name,
        String familyName,
        LocalDateTime accessionDate,
        String nationality,
        IdentityDocumentType identityDocumentType,
        String identityNumber
) {
    public static MemberResponseDTO fromMember(User user) {
        return new MemberResponseDTO(
                user.getId(),
                user.getName(),
                user.getFamilyName(),
                user.getAccessionDate(),
                user.getNationality(),
                user.getIdentityDocumentType(),
                user.getIdentityNumber()
        );
    }
}
