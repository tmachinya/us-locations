package com.pm.uslocations.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Payload for creating or updating a US state")
public class StateRequestDto {

    @NotBlank
    @Size(min = 2, max = 2, message = "State code must be 2 characters")
    @Schema(description = "2-letter state code", example = "NY", requiredMode = Schema.RequiredMode.REQUIRED)
    private String stateCode;

    @NotBlank(message = "Name is required")
    @Schema(description = "Full state name", example = "New York", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank
    @Size(min = 2, max = 2, message = "FIPS must be 2 characters")
    @Schema(description = "2-digit FIPS code assigned by the US Census Bureau", example = "36", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fips;

    @NotBlank
    @Size(min = 2, max = 2, message = "Postal abbreviation must be 2 characters")
    @Schema(description = "2-letter postal abbreviation", example = "NY", requiredMode = Schema.RequiredMode.REQUIRED)
    private String postalAbbr;

    @Schema(description = "Name of the state capital city", example = "Albany")
    private String capital;

    @DecimalMin(value = "0.00", inclusive = true, message = "Area must be non-negative")
    @Schema(description = "Total area in square miles", example = "54555.00")
    private BigDecimal areaSqMi;

    @PositiveOrZero(message = "Population must be zero or positive")
    @Schema(description = "Latest estimated population", example = "19900000")
    private Long populationEst;

    @Schema(description = "Database IDs of time zones to associate with this state", example = "[1]")
    private Set<Integer> timeZoneIds;
}
