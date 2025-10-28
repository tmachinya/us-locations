package com.pm.uslocations.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "time_zones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeZoneRef {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

}
