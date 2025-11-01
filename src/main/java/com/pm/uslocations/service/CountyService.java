package com.pm.uslocations.service;

import com.pm.uslocations.dto.request.CountyRequestDto;
import com.pm.uslocations.dto.response.CountyResponseDto;

import java.util.List;

public interface CountyService {
    List<CountyResponseDto> list(String q, Integer stateId, Integer limit, Integer offset);
    CountyResponseDto getById(Integer id);
    CountyResponseDto getByFips(String countyFips);
    CountyResponseDto create(CountyRequestDto dto);
    CountyResponseDto update(Integer id, CountyRequestDto dto);
    void delete(Integer id);
    List<CountyResponseDto> listAllCounties();
}
