package com.pm.uslocations.controller;

import com.pm.uslocations.dto.response.CountyResponseDto;
import com.pm.uslocations.dto.request.CountyRequestDto;
import com.pm.uslocations.service.CountyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/counties")
@RequiredArgsConstructor
@Tag(name = "Counties", description = "CRUD operations for US counties")
public class CountyController {

    private final CountyService countyService;

    @Operation(summary = "List counties", description = "Returns a paged list of counties. Optionally filter by name or state.")
    @ApiResponse(responseCode = "200", description = "List of counties")
    @GetMapping
    public List<CountyResponseDto> list(
            @Parameter(description = "Search term — partial county name or exact FIPS code") @RequestParam(required = false) String q,
            @Parameter(description = "Filter by state database ID") @RequestParam(required = false) Integer stateId,
            @Parameter(description = "Max results to return (1–200, default 50)") @RequestParam(required = false, defaultValue = "50") Integer limit,
            @Parameter(description = "Number of records to skip (default 0)") @RequestParam(required = false, defaultValue = "0") Integer offset
    ) {
        return countyService.list(q, stateId, limit, offset);
    }

    @Operation(summary = "List all counties (no paging)", description = "Returns every county without pagination. Use with caution on large datasets.")
    @ApiResponse(responseCode = "200", description = "Full list of counties")
    @GetMapping("/all-counties")
    public List<CountyResponseDto> listAllCounties() {
        return countyService.listAllCounties();
    }

    @Operation(summary = "Get county by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "County found"),
            @ApiResponse(responseCode = "404", description = "County not found")
    })
    @GetMapping("/{id}")
    public CountyResponseDto getById(
            @Parameter(description = "Database ID of the county") @PathVariable Integer id) {
        return countyService.getById(id);
    }

    @Operation(summary = "Get county by FIPS code", description = "Look up a county using its 5-character FIPS code, e.g. 36001 for Albany County NY.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "County found"),
            @ApiResponse(responseCode = "404", description = "County not found")
    })
    @GetMapping("/fips/{countyFips}")
    public CountyResponseDto getByFips(
            @Parameter(description = "5-character FIPS code, e.g. 36001") @PathVariable String countyFips) {
        return countyService.getByFips(countyFips);
    }

    @Operation(summary = "Create a new county")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "County created"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Referenced state not found")
    })
    @PostMapping
    public CountyResponseDto create(@Valid @RequestBody CountyRequestDto dto) {
        return countyService.create(dto);
    }

    @Operation(summary = "Update an existing county by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "County updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "County or referenced state not found")
    })
    @PutMapping("/{id}")
    public CountyResponseDto update(
            @Parameter(description = "Database ID of the county to update") @PathVariable Integer id,
            @Valid @RequestBody CountyRequestDto dto
    ) {
        return countyService.update(id, dto);
    }

    @Operation(summary = "Delete a county by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "County deleted"),
            @ApiResponse(responseCode = "404", description = "County not found")
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "Database ID of the county to delete") @PathVariable Integer id) {
        countyService.delete(id);
    }
}