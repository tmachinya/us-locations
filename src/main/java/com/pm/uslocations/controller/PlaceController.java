package com.pm.uslocations.controller;

import com.pm.uslocations.dto.request.PlaceRequestDto;
import com.pm.uslocations.dto.response.PlaceResponseDto;
import com.pm.uslocations.enums.PlaceType;
import com.pm.uslocations.service.PlaceService;
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
@RequestMapping("/api/places")
@RequiredArgsConstructor
@Tag(name = "Places", description = "CRUD operations for US places — cities, towns, villages, CDPs, and boroughs")
public class PlaceController {

    private final PlaceService placeService;

    @Operation(summary = "List places", description = "Returns a paged list of places. Filter by name, state, county, or place type.")
    @ApiResponse(responseCode = "200", description = "List of places")
    @GetMapping
    public List<PlaceResponseDto> list(
            @Parameter(description = "Search term — partial place name or exact FIPS code") @RequestParam(required = false) String q,
            @Parameter(description = "Filter by state database ID") @RequestParam(required = false) Integer stateId,
            @Parameter(description = "Filter by county database ID") @RequestParam(required = false) Integer countyId,
            @Parameter(description = "Filter by place type: CITY, TOWN, VILLAGE, CDP, BOROUGH, OTHER") @RequestParam(required = false) PlaceType type,
            @Parameter(description = "Max results to return (1–200, default 50)") @RequestParam(required = false, defaultValue = "50") Integer limit,
            @Parameter(description = "Number of records to skip (default 0)") @RequestParam(required = false, defaultValue = "0") Integer offset
    ) {
        return placeService.list(q, stateId, countyId, type, limit, offset);
    }

    @Operation(summary = "List all places (no paging)", description = "Returns every place without pagination. Use with caution on large datasets.")
    @ApiResponse(responseCode = "200", description = "Full list of places")
    @GetMapping("/all-places")
    public List<PlaceResponseDto> listAll() {
        return placeService.listAllPlaces();
    }

    @Operation(summary = "Get place by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Place found"),
            @ApiResponse(responseCode = "404", description = "Place not found")
    })
    @GetMapping("/{id}")
    public PlaceResponseDto getById(
            @Parameter(description = "Database ID of the place") @PathVariable Integer id) {
        return placeService.getById(id);
    }

    @Operation(summary = "Get place by FIPS code", description = "Look up a place using its 7-character FIPS code (state(2) + place(5)), e.g. 3601000 for Albany NY.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Place found"),
            @ApiResponse(responseCode = "404", description = "Place not found")
    })
    @GetMapping("/fips/{placeFips}")
    public PlaceResponseDto getByFips(
            @Parameter(description = "7-character FIPS code, e.g. 3601000") @PathVariable String placeFips) {
        return placeService.getByFips(placeFips);
    }

    @Operation(summary = "Create a new place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Place created"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Referenced state or county not found")
    })
    @PostMapping
    public PlaceResponseDto create(@Valid @RequestBody PlaceRequestDto dto) {
        return placeService.create(dto);
    }

    @Operation(summary = "Update an existing place by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Place updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Place, state, or county not found")
    })
    @PutMapping("/{id}")
    public PlaceResponseDto update(
            @Parameter(description = "Database ID of the place to update") @PathVariable Integer id,
            @Valid @RequestBody PlaceRequestDto dto) {
        return placeService.update(id, dto);
    }

    @Operation(summary = "Delete a place by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Place deleted"),
            @ApiResponse(responseCode = "404", description = "Place not found")
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "Database ID of the place to delete") @PathVariable Integer id) {
        placeService.delete(id);
    }
}