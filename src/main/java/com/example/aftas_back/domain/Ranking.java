package com.example.aftas_back.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ranking {
    private Integer rank;

    private Integer score;

    @EmbeddedId
    private RankId id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    @MapsId("competitionId")
    private Competition competition;
}
