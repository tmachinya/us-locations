package com.pm.uslocations.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Full details of a US county")
public class CountyResponseDto {

    @Schema(description = "Internal database ID", example = "1")
    private Integer id;

    @Schema(description = "County name", example = "Albany County")
    private String name;

    @Schema(description = "5-character FIPS code (state(2) + county(3))", example = "36001")
    private String countyFips;

    @Schema(description = "County seat (main city/town)", example = "Albany")
    private String seat;

    @Schema(description = "Database ID of the parent state", example = "1")
    private Integer stateId;

    @Schema(description = "2-letter code of the parent state", example = "NY")
    private String stateCode;

    @Schema(description = "Full name of the parent state", example = "New York")
    private String stateName;

    @Schema(description = "Timestamp when the record was created", example = "2024-01-15T10:30:00Z")
    private OffsetDateTime createdAt;

    @Schema(description = "Timestamp when the record was last updated", example = "2024-06-01T08:00:00Z")
    private OffsetDateTime updatedAt;
}
