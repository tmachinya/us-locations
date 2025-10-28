package com.pm.uslocations.model;

import com.pm.uslocations.enums.ClimateAggregate;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "state_climate_stats")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StateClimateStat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @Enumerated(EnumType.STRING)
    @Column(name = "aggregate", nullable = false)
    private ClimateAggregate aggregate; // MONTHLY or YEARLY

    /** Null for 30-yr normals; present for specific years */
    private Integer year;

    /** 1..12 required for MONTHLY; null for YEARLY */
    private Short month;

    @Column(name = "avg_temp_f", precision = 5, scale = 2)
    private BigDecimal avgTempF;

    @Column(name = "avg_precip_in", precision = 6, scale = 2)
    private BigDecimal avgPrecipIn;

    @Column(name = "avg_snow_in", precision = 6, scale = 2)
    private BigDecimal avgSnowIn;

    private String source;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
}
