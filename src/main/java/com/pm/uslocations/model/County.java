package com.pm.uslocations.model;

import com.pm.uslocations.model.State;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "counties",
        uniqueConstraints = @UniqueConstraint(columnNames = {"state_id", "name"})
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class County {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @Column(name = "name", nullable = false, columnDefinition = "citext")
    private String name;

    @Column(name = "county_fips", nullable = false, unique = true, length = 5)
    private String countyFips;

    @Column(name = "seat", columnDefinition = "citext")
    private String seat;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private OffsetDateTime updatedAt;
}
