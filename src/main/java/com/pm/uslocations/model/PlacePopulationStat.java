package com.pm.uslocations.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "place_population_stats",
        uniqueConstraints = @UniqueConstraint(columnNames = {"place_id", "year"})
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlacePopulationStat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "population", nullable = false)
    private Long population;

    private String source;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
}
