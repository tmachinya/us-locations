package com.pm.uslocations.model;

import com.pm.uslocations.enums.PlaceType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "places",
        uniqueConstraints = @UniqueConstraint(columnNames = {"state_id", "name", "place_type"})
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Place {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "county_id")
    private County county; // nullable; places can span counties

    @Column(name = "name", nullable = false, columnDefinition = "citext")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "place_type", nullable = false, length = 50)
    private PlaceType placeType;

    @Column(name = "place_fips", length = 7, columnDefinition = "bpchar")
    private String placeFips;

    @Column(name = "latitude", precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "elevation_ft")
    private Integer elevationFt;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private OffsetDateTime updatedAt;
}
