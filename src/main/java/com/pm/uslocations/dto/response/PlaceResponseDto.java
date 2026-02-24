package com.pm.uslocations.dto.response;

import com.pm.uslocations.enums.PlaceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Full details of a US place — city, town, village, CDP, or borough")
public class PlaceResponseDto {

    @Schema(description = "Internal database ID", example = "1")
    private Integer id;

    @Schema(description = "Place name", example = "Albany")
    private String name;

    @Schema(description = "Classification of the place", example = "CITY")
    private PlaceType placeType;

    @Schema(description = "7-character FIPS code (state(2) + place(5))", example = "3601000")
    private String placeFips;

    @Schema(description = "Latitude in decimal degrees (WGS84)", example = "42.6526")
    private BigDecimal latitude;

    @Schema(description = "Longitude in decimal degrees (WGS84)", example = "-73.7562")
    private BigDecimal longitude;

    @Schema(description = "Elevation above sea level in feet", example = "141")
    private Integer elevationFt;

    @Schema(description = "Database ID of the parent state", example = "1")
    private Integer stateId;

    @Schema(description = "2-letter code of the parent state", example = "NY")
    private String stateCode;

    @Schema(description = "Full name of the parent state", example = "New York")
    private String stateName;

    @Schema(description = "Database ID of the county this place belongs to (nullable — some places span counties)", example = "1")
    private Integer countyId;

    @Schema(description = "Name of the county this place belongs to (nullable)", example = "Albany County")
    private String countyName;

    @Schema(description = "Timestamp when the record was created", example = "2024-01-15T10:30:00Z")
    private OffsetDateTime createdAt;

    @Schema(description = "Timestamp when the record was last updated", example = "2024-06-01T08:00:00Z")
    private OffsetDateTime updatedAt;
}
