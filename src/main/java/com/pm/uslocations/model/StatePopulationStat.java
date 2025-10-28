package com.pm.uslocations.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "state_population_stats",
        uniqueConstraints = @UniqueConstraint(columnNames = {"state_id", "year"})
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StatePopulationStat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "population", nullable = false)
    private Long population;

    private String source;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
}
