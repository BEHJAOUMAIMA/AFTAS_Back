package com.example.aftas_back.domain;

import com.example.aftas_back.domain.enums.IdentityDocumentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String familyName;
    private LocalDateTime accessionDate;
    private String nationality;
    private IdentityDocumentType identityDocumentType;
    private String identityNumber;
    private String email;
    private String password;
    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
