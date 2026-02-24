package com.pm.uslocations.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Full details of a US state")
public class StateResponseDto {

    @Schema(description = "Internal database ID", example = "1")
    private Integer id;

    @Schema(description = "2-letter state code", example = "NY")
    private String stateCode;

    @Schema(description = "Full state name", example = "New York")
    private String name;

    @Schema(description = "2-digit FIPS code assigned by the US Census Bureau", example = "36")
    private String fips;

    @Schema(description = "2-letter postal abbreviation", example = "NY")
    private String postalAbbr;

    @Schema(description = "Name of the state capital city", example = "Albany")
    private String capital;

    @Schema(description = "Total area of the state in square miles", example = "54555.00")
    private BigDecimal areaSqMi;

    @Schema(description = "Latest estimated population", example = "19900000")
    private Long populationEst;

    @Schema(description = "IANA time zone identifiers the state observes", example = "[\"America/New_York\"]")
    private Set<String> timeZoneNames;

    @Schema(description = "Timestamp when the record was created", example = "2024-01-15T10:30:00Z")
    private OffsetDateTime createdAt;

    @Schema(description = "Timestamp when the record was last updated", example = "2024-06-01T08:00:00Z")
    private OffsetDateTime updatedAt;
}
