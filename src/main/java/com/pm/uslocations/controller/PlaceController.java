package com.pm.uslocations.controller;

import com.pm.uslocations.dto.request.PlaceRequestDto;
import com.pm.uslocations.dto.response.PlaceResponseDto;
import com.pm.uslocations.enums.PlaceType;
import com.pm.uslocations.service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    /** List/search places with optional filters and simple pagination */
    @GetMapping
    public List<PlaceResponseDto> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer stateId,
            @RequestParam(required = false) Integer countyId,
            @RequestParam(required = false) PlaceType type,
            @RequestParam(required = false, defaultValue = "50") Integer limit,
            @RequestParam(required = false, defaultValue = "0") Integer offset
    ) {
        return placeService.list(q, stateId, countyId, type, limit, offset);
    }

    /** Retrieve all places (no paging) */
    @GetMapping("/all-places")
    public List<PlaceResponseDto> listAll() {
        return placeService.listAllPlaces();
    }

    /** Get by database ID */
    @GetMapping("/{id}")
    public PlaceResponseDto getById(@PathVariable Integer id) {
        return placeService.getById(id);
    }

    /** Get by FIPS (7 chars) */
    @GetMapping("/fips/{placeFips}")
    public PlaceResponseDto getByFips(@PathVariable String placeFips) {
        return placeService.getByFips(placeFips);
    }

    /** Create a place */
    @PostMapping
    public PlaceResponseDto create(@Valid @RequestBody PlaceRequestDto dto) {
        return placeService.create(dto);
    }

    /** Update a place */
    @PutMapping("/{id}")
    public PlaceResponseDto update(@PathVariable Integer id, @Valid @RequestBody PlaceRequestDto dto) {
        return placeService.update(id, dto);
    }

    /** Delete a place */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        placeService.delete(id);
    }
}