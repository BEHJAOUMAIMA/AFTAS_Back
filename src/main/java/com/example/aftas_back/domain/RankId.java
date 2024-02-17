package com.example.aftas_back.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankId implements Serializable{
    @Column(name = "competition_id")
    private Long competitionId;

    @Column(name = "member_id")
    private Long memberId;
}
