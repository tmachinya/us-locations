package com.pm.uslocations.service;

import com.pm.uslocations.dto.request.PlaceRequestDto;
import com.pm.uslocations.dto.response.PlaceResponseDto;
import com.pm.uslocations.enums.PlaceType;

import java.util.List;

public interface PlaceService {
    List<PlaceResponseDto> list(String q, Integer stateId, Integer countyId, PlaceType type, Integer limit, Integer offset);
    List<PlaceResponseDto> listAllPlaces();
    PlaceResponseDto getById(Integer id);
    PlaceResponseDto getByFips(String placeFips);
    PlaceResponseDto create(PlaceRequestDto dto);
    PlaceResponseDto update(Integer id, PlaceRequestDto dto);
    void delete(Integer id);
}