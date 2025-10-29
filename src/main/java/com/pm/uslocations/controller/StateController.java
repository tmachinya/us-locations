package com.pm.uslocations.controller;

import com.pm.uslocations.dto.request.StateRequestDto;
import com.pm.uslocations.dto.response.StateResponseDto;
import com.pm.uslocations.service.StateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/states")
@RequiredArgsConstructor
public class StateController {

    private final StateService stateService;

    /**
     * List all states, with optional search query.
     * Supports simple pagination via limit and offset.
     *
     * Example:
     * GET /api/states?q=new&limit=20&offset=0
     */
    @GetMapping
    public List<StateResponseDto> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false, defaultValue = "50") Integer limit,
            @RequestParam(required = false, defaultValue = "0") Integer offset) {
        return stateService.list(q, limit, offset);
    }

    /**
     * Get a state by its ID.
     * Example: GET /api/states/1
     */
    @GetMapping("/{id}")
    public StateResponseDto getById(@PathVariable Integer id) {
        return stateService.getById(id);
    }

    /**
     * Get a state by its 2-letter code.
     * Example: GET /api/states/code/NY
     */
    @GetMapping("/code/{code}")
    public StateResponseDto getByCode(@PathVariable String code) {
        return stateService.getByCode(code);
    }

    /**
     * Create a new state.
     * Example:
     * POST /api/states
     * {
     *   "stateCode": "TX",
     *   "name": "Texas",
     *   "fips": "48",
     *   "postalAbbr": "TX",
     *   "capital": "Austin",
     *   "areaSqMi": 268596.00,
     *   "populationEst": 30400000,
     *   "timeZoneIds": [2, 3]
     * }
     */
    @PostMapping
    public StateResponseDto create(@Valid @RequestBody StateRequestDto dto) {
        return stateService.create(dto);
    }

    /**
     * Update an existing state by ID.
     * Example:
     * PUT /api/states/2
     * {
     *   "stateCode": "TX",
     *   "name": "Texas",
     *   "capital": "Austin",
     *   "populationEst": 30500000
     * }
     */
    @PutMapping("/{id}")
    public StateResponseDto update(
            @PathVariable Integer id,
            @Valid @RequestBody StateRequestDto dto) {
        return stateService.update(id, dto);
    }

    /**
     * Delete a state by ID.
     * Example: DELETE /api/states/2
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        stateService.delete(id);
    }
}
