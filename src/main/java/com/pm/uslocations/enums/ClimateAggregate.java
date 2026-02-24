package com.pm.uslocations.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Aggregation period for climate statistics — MONTHLY (per calendar month) or YEARLY (annual average)")
public enum ClimateAggregate {
    /** Per-calendar-month averages (month field 1–12 required) */
    MONTHLY,
    /** Annual averages or 30-year climate normals (month field is null) */
    YEARLY
}
