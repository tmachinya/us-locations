package com.pm.uslocations.dto.response;

import com.pm.uslocations.enums.PlaceType;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceResponseDto {

    private Integer id;

    // core
    private String name;
    private PlaceType placeType;
    private String placeFips;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer elevationFt;

    // state summary
    private Integer stateId;
    private String stateCode;
    private String stateName;

    // county summary (nullable)
    private Integer countyId;
    private String countyName;

    // audit
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}