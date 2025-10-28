package com.pm.uslocations.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "states")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class State {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "state_code", nullable = false, unique = true, length = 2)
    private String stateCode;

    @Column(name = "name", nullable = false, unique = true, columnDefinition = "citext")
    private String name;

    @Column(name = "fips", nullable = false, unique = true, length = 2)
    private String fips;

    @Column(name = "postal_abbr", nullable = false, unique = true, length = 2)
    private String postalAbbr;

    @Column(name = "capital", columnDefinition = "citext")
    private String capital;

    @Column(name = "area_sq_mi", precision = 12, scale = 2)
    private BigDecimal areaSqMi;

    @Column(name = "population_est")
    private Long populationEst;

    /** DB trigger manages these; mark read-only from JPA side */
    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private OffsetDateTime updatedAt;

    /** Many-to-many via state_time_zones */
    @ManyToMany
    @JoinTable(
            name = "state_time_zones",
            joinColumns = @JoinColumn(name = "state_id"),
            inverseJoinColumns = @JoinColumn(name = "time_zone_id")
    )
    private Set<TimeZoneRef> timeZones = new LinkedHashSet<>();
}
