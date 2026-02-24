package com.pm.uslocations.dto.request;

import com.pm.uslocations.enums.PlaceType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Payload for creating or updating a US place — city, town, village, CDP, or borough")
public class PlaceRequestDto {

    @NotNull(message = "stateId is required")
    @Schema(description = "Database ID of the parent state", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer stateId;

    @Schema(description = "Database ID of the county this place belongs to. Omit if the place spans multiple counties or is unassigned.", example = "1")
    private Integer countyId;

    @NotBlank(message = "name is required")
    @Schema(description = "Place name", example = "Albany", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull(message = "placeType is required")
    @Schema(description = "Classification of the place", example = "CITY", requiredMode = Schema.RequiredMode.REQUIRED)
    private PlaceType placeType;

    @Size(min = 7, max = 7, message = "placeFips must be 7 characters")
    @Schema(description = "7-character FIPS code (state(2) + place(5))", example = "3601000")
    private String placeFips;

    @Schema(description = "Latitude in decimal degrees (WGS84)", example = "42.6526")
    private BigDecimal latitude;

    @Schema(description = "Longitude in decimal degrees (WGS84)", example = "-73.7562")
    private BigDecimal longitude;

    @Schema(description = "Elevation above sea level in feet", example = "141")
    private Integer elevationFt;
}
