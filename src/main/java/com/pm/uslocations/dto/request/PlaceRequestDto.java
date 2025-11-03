package com.pm.uslocations.dto.request;

import com.pm.uslocations.enums.PlaceType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceRequestDto {

    @NotNull(message = "stateId is required")
    private Integer stateId;

    /** Optional; places can span counties or be unassigned in this model */
    private Integer countyId;

    @NotBlank(message = "name is required")
    private String name;

    @NotNull(message = "placeType is required")
    private PlaceType placeType;

    /** Optional FIPS (7 chars: state(2)+place(5)) */
    @Size(min = 7, max = 7, message = "placeFips must be 7 characters")
    private String placeFips;

    /** Optional coordinates */
    private BigDecimal latitude;   // precision validated in DB
    private BigDecimal longitude;

    private Integer elevationFt;
}