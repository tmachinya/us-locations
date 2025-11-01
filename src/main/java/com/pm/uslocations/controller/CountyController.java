package com.pm.uslocations.controller;

import com.pm.uslocations.dto.response.CountyResponseDto;
import com.pm.uslocations.dto.request.CountyRequestDto;
import com.pm.uslocations.service.CountyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/counties")
@RequiredArgsConstructor
public class CountyController {

    private final CountyService countyService;

    /**
     * List counties with optional text search and/or state filter.
     * Example: GET /api/counties?q=Albany&stateId=1&limit=25&offset=0
     */
    @GetMapping
    public List<CountyResponseDto> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer stateId,
            @RequestParam(required = false, defaultValue = "50") Integer limit,
            @RequestParam(required = false, defaultValue = "0") Integer offset
    ) {
        return countyService.list(q, stateId, limit, offset);
    }

    /** Get a county by database ID. */
    @GetMapping("/{id}")
    public CountyResponseDto getById(@PathVariable Integer id) {
        return countyService.getById(id);
    }

    /** Get a county by its 5-digit FIPS code. */
    @GetMapping("/fips/{countyFips}")
    public CountyResponseDto getByFips(@PathVariable String countyFips) {
        return countyService.getByFips(countyFips);
    }

    /** Create a new county. */
    @PostMapping
    public CountyResponseDto create(@Valid @RequestBody CountyRequestDto dto) {
        return countyService.create(dto);
    }

    /** Update an existing county by ID. */
    @PutMapping("/{id}")
    public CountyResponseDto update(
            @PathVariable Integer id,
            @Valid @RequestBody CountyRequestDto dto
    ) {
        return countyService.update(id, dto);
    }

    /** Delete a county by ID. */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        countyService.delete(id);
    }

    @GetMapping("/all-counties")
    public List<CountyResponseDto> listAllCounties() {
        return countyService.listAllCounties();
    }
}