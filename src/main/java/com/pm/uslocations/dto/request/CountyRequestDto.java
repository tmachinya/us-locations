package com.pm.uslocations.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountyRequestDto {

    @NotNull(message = "stateId is required")
    private Integer stateId;

    @NotBlank(message = "name is required")
    private String name;

    /** FIPS is 5 characters (state(2)+county(3)) */
    @NotBlank(message = "countyFips is required")
    @Size(min = 5, max = 5, message = "countyFips must be 5 characters")
    private String countyFips;

    private String seat;
}