
package com.pm.uslocations.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateRequestDto {

    @NotBlank
    @Size(min = 2, max = 2, message = "State code must be 2 characters")
    private String stateCode;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank
    @Size(min = 2, max = 2, message = "FIPS must be 2 characters")
    private String fips;

    @NotBlank
    @Size(min = 2, max = 2, message = "Postal abbreviation must be 2 characters")
    private String postalAbbr;

    private String capital;

    @DecimalMin(value = "0.00", inclusive = true, message = "Area must be non-negative")
    private BigDecimal areaSqMi;

    @PositiveOrZero(message = "Population must be zero or positive")
    private Long populationEst;

    /** Optional: IDs of time zones to link to this state */
    private Set<Integer> timeZoneIds;
}
