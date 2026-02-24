package com.pm.uslocations.mapper;

import com.pm.uslocations.dto.request.StateRequestDto;
import com.pm.uslocations.dto.response.StateResponseDto;
import com.pm.uslocations.model.State;
import com.pm.uslocations.model.TimeZoneRef;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class StateMapper {

    /** Convert Entity → Response DTO */
    public StateResponseDto toResponseDto(State state) {
        if (state == null) return null;

        return StateResponseDto.builder()
                .id(state.getId())
                .stateCode(state.getStateCode())
                .name(state.getName())
                .fips(state.getFips())
                .postalAbbr(state.getPostalAbbr())
                .capital(state.getCapital())
                .areaSqMi(state.getAreaSqMi())
                .populationEst(state.getPopulationEst())
                .timeZoneNames(state.getTimeZones() == null ? null :
                        state.getTimeZones().stream()
                                .map(TimeZoneRef::getName)
                                .collect(Collectors.toSet()))
                .createdAt(state.getCreatedAt())
                .updatedAt(state.getUpdatedAt())
                .build();
    }



    /** Apply Request DTO fields to Entity (used for create or update) */
    public void applyRequestDto(State state, StateRequestDto dto) {
        if (dto == null) return;

        state.setStateCode(dto.getStateCode() != null ? dto.getStateCode().toUpperCase() : null);
        state.setName(dto.getName());
        state.setFips(dto.getFips() != null ? dto.getFips().toUpperCase() : null);
        state.setPostalAbbr(dto.getPostalAbbr() != null ? dto.getPostalAbbr().toUpperCase() : null);
        state.setCapital(dto.getCapital());
        state.setAreaSqMi(dto.getAreaSqMi());
        state.setPopulationEst(dto.getPopulationEst());
        // Time zones handled in service layer using dto.getTimeZoneIds()
    }

}
