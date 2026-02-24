package com.pm.uslocations.mapper;

import com.pm.uslocations.dto.request.CountyRequestDto;
import com.pm.uslocations.dto.response.CountyResponseDto;
import com.pm.uslocations.model.County;
import com.pm.uslocations.model.State;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CountyMapper {

    public CountyResponseDto toResponseDto(County c) {
        if (c == null) return null;

        State s = c.getState();
        return CountyResponseDto.builder()
                .id(c.getId())
                .name(c.getName())
                .countyFips(c.getCountyFips())
                .seat(c.getSeat())
                .stateId(s != null ? s.getId() : null)
                .stateCode(s != null ? s.getStateCode() : null)
                .stateName(s != null ? s.getName() : null)
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .build();
    }

    /** Apply mutable fields from request onto entity (state is handled in service) */
    public void applyRequestDto(County c, CountyRequestDto dto) {
        if (dto == null) return;
        c.setName(dto.getName());
        // normalize FIPS: upper + trim
        c.setCountyFips(dto.getCountyFips() != null ? dto.getCountyFips().trim().toUpperCase() : null);
        c.setSeat(dto.getSeat());
    }
}