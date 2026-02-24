package com.pm.uslocations.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Classification of a US place — CITY, TOWN, VILLAGE, CDP, BOROUGH, OTHER")
public enum PlaceType {
    CITY,
    TOWN,
    VILLAGE,
    /** Census-Designated Place */
    CDP,
    BOROUGH,
    OTHER
}
