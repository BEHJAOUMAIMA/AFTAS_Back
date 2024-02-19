package com.example.aftas_back.dto.response;

import com.example.aftas_back.domain.Member;
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
    public static MemberResponseDTO fromMember(Member member) {
        return new MemberResponseDTO(
                member.getId(),
                member.getName(),
                member.getFamilyName(),
                member.getAccessionDate(),
                member.getNationality(),
                member.getIdentityDocumentType(),
                member.getIdentityNumber()
        );
    }
}
