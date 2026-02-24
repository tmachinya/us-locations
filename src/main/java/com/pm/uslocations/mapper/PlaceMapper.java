package com.pm.uslocations.mapper;

import com.pm.uslocations.dto.request.PlaceRequestDto;
import com.pm.uslocations.dto.response.PlaceResponseDto;
import com.pm.uslocations.model.County;
import com.pm.uslocations.model.Place;
import com.pm.uslocations.model.State;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlaceMapper {

    public PlaceResponseDto toResponseDto(Place p) {
        if (p == null) return null;

        State s = p.getState();
        County c = p.getCounty();

        return PlaceResponseDto.builder()
                .id(p.getId())
                .name(p.getName())
                .placeType(p.getPlaceType())
                .placeFips(p.getPlaceFips())
                .latitude(p.getLatitude())
                .longitude(p.getLongitude())
                .elevationFt(p.getElevationFt())
                .stateId(s != null ? s.getId() : null)
                .stateCode(s != null ? s.getStateCode() : null)
                .stateName(s != null ? s.getName() : null)
                .countyId(c != null ? c.getId() : null)
                .countyName(c != null ? c.getName() : null)
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }

    /** Apply mutable fields from request (state/county are set in service) */
    public void applyRequestDto(Place p, PlaceRequestDto dto) {
        if (dto == null) return;
        p.setName(dto.getName());
        p.setPlaceType(dto.getPlaceType());
        p.setPlaceFips(dto.getPlaceFips() != null ? dto.getPlaceFips().trim().toUpperCase() : null);
        p.setLatitude(dto.getLatitude());
        p.setLongitude(dto.getLongitude());
        p.setElevationFt(dto.getElevationFt());
    }
}