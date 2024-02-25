package com.example.aftas_back.dto.response;

import com.example.aftas_back.domain.User;
import com.example.aftas_back.domain.enums.IdentityDocumentType;
import com.example.aftas_back.domain.enums.Role;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record MemberResponseDTO(
        Long id,
        String name,
        String familyName,
        LocalDateTime accessionDate,
        String nationality,
        IdentityDocumentType identityDocumentType,
        String identityNumber,
        Role role,
        List<GrantedAuthority> authorities,
        boolean status
) {
    public static MemberResponseDTO fromMember(User user) {
        List<GrantedAuthority> authorityList = new ArrayList<>(user.getAuthorities());
        return new MemberResponseDTO(
                user.getId(),
                user.getName(),
                user.getFamilyName(),
                user.getAccessionDate(),
                user.getNationality(),
                user.getIdentityDocumentType(),
                user.getIdentityNumber(),
                user.getRole(),
                authorityList,
                user.isStatus()
        );
    }
}
