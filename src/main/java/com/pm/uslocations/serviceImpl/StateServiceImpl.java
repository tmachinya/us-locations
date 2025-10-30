package com.pm.uslocations.serviceImpl;

import com.pm.uslocations.dto.request.StateRequestDto;
import com.pm.uslocations.dto.response.StateResponseDto;
import com.pm.uslocations.mapper.StateMapper;
import com.pm.uslocations.model.State;
import com.pm.uslocations.model.TimeZoneRef;
import com.pm.uslocations.repo.StateRepository;
import com.pm.uslocations.repo.TimeZoneRefRepository;
import com.pm.uslocations.service.StateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;
    private final TimeZoneRefRepository timeZoneRefRepository;

    @Override
    @Transactional(readOnly = true)
    public List<StateResponseDto> list(String q, Integer limit, Integer offset) {
        int safeLimit = (limit == null || limit <= 0 || limit > 200) ? 50 : limit;
        int off = (offset == null || offset < 0) ? 0 : offset;
        int page = off / safeLimit;
        Pageable pageable = PageRequest.of(page, safeLimit);

        List<State> states = stateRepository.search(q, pageable);

        List<State> allStates = stateRepository.findAll();
        return allStates.stream().map(StateMapper::toResponseDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StateResponseDto getById(Integer id) {
        State s = stateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("State not found: id=" + id));
        return StateMapper.toResponseDto(s);
    }

    @Override
    @Transactional(readOnly = true)
    public StateResponseDto getByCode(String code) {
        State s = stateRepository.findByStateCodeIgnoreCase(code)
                .orElseThrow(() -> new EntityNotFoundException("State not found: code=" + code));
        return StateMapper.toResponseDto(s);
    }

    @Override
    public StateResponseDto create(StateRequestDto dto) {
        State s = new State();
        StateMapper.applyRequestDto(s, dto);

        // Handle time zones if provided
        if (dto.getTimeZoneIds() != null && !dto.getTimeZoneIds().isEmpty()) {
            var tzs = timeZoneRefRepository.findAllById(dto.getTimeZoneIds());
            if (tzs.size() != dto.getTimeZoneIds().size()) {
                throw new EntityNotFoundException("One or more time zones not found");
            }
            s.getTimeZones().clear();
            s.getTimeZones().addAll(new LinkedHashSet<>(tzs));
        }

        State saved = stateRepository.save(s);
        return StateMapper.toResponseDto(saved);
    }

    @Override
    public StateResponseDto update(Integer id, StateRequestDto dto) {
        State s = stateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("State not found: id=" + id));

        StateMapper.applyRequestDto(s, dto);

        // If timeZoneIds present, treat as explicit update (can be empty to clear)
        if (dto.getTimeZoneIds() != null) {
            var tzs = dto.getTimeZoneIds().isEmpty()
                    ? List.<TimeZoneRef>of()
                    : timeZoneRefRepository.findAllById(dto.getTimeZoneIds());
            if (tzs.size() != dto.getTimeZoneIds().size()) {
                throw new EntityNotFoundException("One or more time zones not found");
            }
            s.getTimeZones().clear();
            s.getTimeZones().addAll(new LinkedHashSet<>(tzs));
        }

        State saved = stateRepository.save(s);
        return StateMapper.toResponseDto(saved);
    }

    @Override
    public void delete(Integer id) {
        if (!stateRepository.existsById(id)) {
            throw new EntityNotFoundException("State not found: id=" + id);
        }
        stateRepository.deleteById(id);
    }
}
