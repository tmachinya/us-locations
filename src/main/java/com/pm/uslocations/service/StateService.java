package com.pm.uslocations.service;


import com.pm.uslocations.dto.request.StateRequestDto;
import com.pm.uslocations.dto.response.StateResponseDto;

import java.util.List;

public interface StateService {
    List<StateResponseDto> list(String q, Integer limit, Integer offset);
    List<StateResponseDto> listAllStates();
    StateResponseDto getById(Integer id);
    StateResponseDto getByCode(String code);
    StateResponseDto create(StateRequestDto dto);
    StateResponseDto update(Integer id, StateRequestDto dto);
    StateResponseDto getByCapital(String capital);
    void delete(Integer id);
}
