package com.pm.uslocations.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Payload for creating or updating a US county")
public class CountyRequestDto {

    @NotNull(message = "stateId is required")
    @Schema(description = "Database ID of the parent state", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer stateId;

    @NotBlank(message = "name is required")
    @Schema(description = "Full county name", example = "Albany County", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "countyFips is required")
    @Size(min = 5, max = 5, message = "countyFips must be 5 characters")
    @Schema(description = "5-character FIPS code (state(2) + county(3))", example = "36001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String countyFips;

    @Schema(description = "County seat (main city/town)", example = "Albany")
    private String seat;
}
