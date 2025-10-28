package com.pm.uslocations.model;

import com.pm.uslocations.enums.ClimateAggregate;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "place_climate_stats")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlaceClimateStat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Enumerated(EnumType.STRING)
    @Column(name = "aggregate", nullable = false)
    private ClimateAggregate aggregate;

    private Integer year;
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
