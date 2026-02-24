package com.pm.uslocations.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateResponseDto {

    private Integer id;
    private String stateCode;
    private String name;
    private String fips;
    private String postalAbbr;
    private String capital;
    private BigDecimal areaSqMi;
    private Long populationEst;
    private Set<String> timeZoneNames;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
