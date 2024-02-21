package com.example.aftas_back.domain;


import com.example.aftas_back.domain.enums.IdentityDocumentType;
import jakarta.persistence.*;
import lombok.*;
/*
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String familyName;

    private LocalDateTime accessionDate;

    private String nationality;

    private IdentityDocumentType identityDocumentType;

    private String identityNumber;

    @OneToMany(mappedBy = "member")
    private List<Ranking> rankings;

    @OneToMany(mappedBy = "member")
    private List<Hunting> huntings;
}

*/