package com.pm.uslocations.controller;

import com.pm.uslocations.dto.request.StateRequestDto;
import com.pm.uslocations.dto.response.StateResponseDto;
import com.pm.uslocations.service.StateService;
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
@RequestMapping("/api/states")
@RequiredArgsConstructor
@Tag(name = "States", description = "CRUD operations for US states")
public class StateController {

    private final StateService stateService;

    @Operation(summary = "List states", description = "Returns a paged list of states. Optionally filter by name, state code, or postal abbreviation.")
    @ApiResponse(responseCode = "200", description = "List of states")
    @GetMapping
    public List<StateResponseDto> list(
            @Parameter(description = "Search term — partial name, or exact state code / postal abbreviation") @RequestParam(required = false) String q,
            @Parameter(description = "Max results to return (1–200, default 50)") @RequestParam(required = false, defaultValue = "50") Integer limit,
            @Parameter(description = "Number of records to skip (default 0)") @RequestParam(required = false, defaultValue = "0") Integer offset) {
        return stateService.list(q, limit, offset);
    }

    @Operation(summary = "List all states (no paging)", description = "Returns every state without pagination. Use with caution on large datasets.")
    @ApiResponse(responseCode = "200", description = "Full list of states")
    @GetMapping("/all-states")
    public List<StateResponseDto> listAllStates() {
        return stateService.listAllStates();
    }

    @Operation(summary = "Get state by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "State found"),
            @ApiResponse(responseCode = "404", description = "State not found")
    })
    @GetMapping("/{id}")
    public StateResponseDto getById(
            @Parameter(description = "Database ID of the state") @PathVariable Integer id) {
        return stateService.getById(id);
    }

    @Operation(summary = "Get state by 2-letter code", description = "Look up a state using its postal/state code, e.g. NY, CA, TX.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "State found"),
            @ApiResponse(responseCode = "404", description = "State not found")
    })
    @GetMapping("/code/{code}")
    public StateResponseDto getByCode(
            @Parameter(description = "2-letter state code, e.g. NY") @PathVariable String code) {
        return stateService.getByCode(code);
    }

    @Operation(summary = "Get state by capital city name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "State found"),
            @ApiResponse(responseCode = "404", description = "No state has that capital")
    })
    @GetMapping("/capital/{capital}")
    public StateResponseDto getByCapital(
            @Parameter(description = "Capital city name, e.g. Albany") @PathVariable String capital) {
        return stateService.getByCapital(capital);
    }

    @Operation(summary = "Create a new state")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "State created"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Referenced time zone ID not found")
    })
    @PostMapping
    public StateResponseDto create(@Valid @RequestBody StateRequestDto dto) {
        return stateService.create(dto);
    }

    @Operation(summary = "Update an existing state by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "State updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "State or referenced time zone not found")
    })
    @PutMapping("/{id}")
    public StateResponseDto update(
            @Parameter(description = "Database ID of the state to update") @PathVariable Integer id,
            @Valid @RequestBody StateRequestDto dto) {
        return stateService.update(id, dto);
    }

    @Operation(summary = "Delete a state by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "State deleted"),
            @ApiResponse(responseCode = "404", description = "State not found")
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "Database ID of the state to delete") @PathVariable Integer id) {
        stateService.delete(id);
    }
}
