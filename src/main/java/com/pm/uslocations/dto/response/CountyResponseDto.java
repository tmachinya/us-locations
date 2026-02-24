package com.pm.uslocations.dto.response;

import lombok.*;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountyResponseDto {
    private Integer id;

    // county fields
    private String name;
    private String countyFips;
    private String seat;

    // state summary
    private Integer stateId;
    private String stateCode;
    private String stateName;

    // audit
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}