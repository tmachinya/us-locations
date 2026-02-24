package com.pm.uslocations.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/climate-stats")
@Tag(name = "Climate Stats", description = "Monthly and yearly climate statistics for US states — coming soon")
public class StateClimateStatController {
}
