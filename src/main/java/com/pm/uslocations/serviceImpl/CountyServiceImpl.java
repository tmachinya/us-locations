package com.pm.uslocations.serviceImpl;

import com.pm.uslocations.dto.request.CountyRequestDto;
import com.pm.uslocations.dto.response.CountyResponseDto;
import com.pm.uslocations.mapper.CountyMapper;
import com.pm.uslocations.model.County;
import com.pm.uslocations.model.State;
import com.pm.uslocations.repo.CountyRepository;
import com.pm.uslocations.repo.StateRepository;
import com.pm.uslocations.service.CountyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CountyServiceImpl implements CountyService {

    private final CountyRepository countyRepository;
    private final StateRepository stateRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CountyResponseDto> listAllCounties() {
        return countyRepository
                .findAll()
                .stream()
                .map(CountyMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountyResponseDto> list(String q, Integer stateId, Integer limit, Integer offset) {
        int safeLimit  = (limit  == null || limit  <= 0 || limit  > 200) ? 50 : limit;
        int off        = (offset == null || offset <  0) ? 0  : offset;
        int page       = off / safeLimit;
        Pageable pageable = PageRequest.of(page, safeLimit);

        List<County> counties = countyRepository.search(q, stateId, pageable);
        return counties.stream().map(CountyMapper::toResponseDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CountyResponseDto getById(Integer id) {
        County c = countyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("County not found: id=" + id));
        return CountyMapper.toResponseDto(c);
    }

    @Override
    @Transactional(readOnly = true)
    public CountyResponseDto getByFips(String countyFips) {
        County c = countyRepository.findByCountyFipsIgnoreCase(countyFips)
                .orElseThrow(() -> new EntityNotFoundException("County not found: countyFips=" + countyFips));
        return CountyMapper.toResponseDto(c);
    }

    @Override
    public CountyResponseDto create(CountyRequestDto dto) {
        // ensure state exists
        State s = stateRepository.findById(dto.getStateId())
                .orElseThrow(() -> new EntityNotFoundException("State not found: id=" + dto.getStateId()));

        County c = new County();
        c.setState(s);
        CountyMapper.applyRequestDto(c, dto);

        County saved = countyRepository.save(c);
        return CountyMapper.toResponseDto(saved);
    }

    @Override
    public CountyResponseDto update(Integer id, CountyRequestDto dto) {
        County c = countyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("County not found: id=" + id));

        // Allow moving to a different state if provided
        if (dto.getStateId() != null && (c.getState() == null || !dto.getStateId().equals(c.getState().getId()))) {
            State s = stateRepository.findById(dto.getStateId())
                    .orElseThrow(() -> new EntityNotFoundException("State not found: id=" + dto.getStateId()));
            c.setState(s);
        }

        CountyMapper.applyRequestDto(c, dto);

        County saved = countyRepository.save(c);
        return CountyMapper.toResponseDto(saved);
    }

    @Override
    public void delete(Integer id) {
        if (!countyRepository.existsById(id)) {
            throw new EntityNotFoundException("County not found: id=" + id);
        }
        countyRepository.deleteById(id);
    }
}