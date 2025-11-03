package com.pm.uslocations.serviceImpl;

import com.pm.uslocations.dto.request.PlaceRequestDto;
import com.pm.uslocations.dto.response.PlaceResponseDto;
import com.pm.uslocations.enums.PlaceType;
import com.pm.uslocations.mapper.PlaceMapper;
import com.pm.uslocations.model.County;
import com.pm.uslocations.model.Place;
import com.pm.uslocations.model.State;
import com.pm.uslocations.repo.CountyRepository;
import com.pm.uslocations.repo.PlaceRepository;
import com.pm.uslocations.repo.StateRepository;
import com.pm.uslocations.service.PlaceService;
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
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final StateRepository stateRepository;
    private final CountyRepository countyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PlaceResponseDto> list(String q, Integer stateId, Integer countyId, PlaceType type, Integer limit, Integer offset) {
        int safeLimit  = (limit == null  || limit <= 0 || limit > 200) ? 50 : limit;
        int off        = (offset == null || offset <  0) ? 0  : offset;
        int page       = off / safeLimit;
        Pageable pageable = PageRequest.of(page, safeLimit);

        return placeRepository.search(q, stateId, countyId, type, pageable)
                .stream()
                .map(PlaceMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlaceResponseDto> listAllPlaces() {
        return placeRepository.findAll()
                .stream()
                .map(PlaceMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PlaceResponseDto getById(Integer id) {
        Place p = placeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Place not found: id=" + id));
        return PlaceMapper.toResponseDto(p);
    }

    @Override
    @Transactional(readOnly = true)
    public PlaceResponseDto getByFips(String placeFips) {
        Place p = placeRepository.findByPlaceFipsIgnoreCase(placeFips)
                .orElseThrow(() -> new EntityNotFoundException("Place not found: placeFips=" + placeFips));
        return PlaceMapper.toResponseDto(p);
    }

    @Override
    public PlaceResponseDto create(PlaceRequestDto dto) {
        State s = stateRepository.findById(dto.getStateId())
                .orElseThrow(() -> new EntityNotFoundException("State not found: id=" + dto.getStateId()));

        County c = null;
        if (dto.getCountyId() != null) {
            c = countyRepository.findById(dto.getCountyId())
                    .orElseThrow(() -> new EntityNotFoundException("County not found: id=" + dto.getCountyId()));
        }

        Place p = new Place();
        p.setState(s);
        p.setCounty(c);
        PlaceMapper.applyRequestDto(p, dto);

        Place saved = placeRepository.save(p);
        return PlaceMapper.toResponseDto(saved);
    }

    @Override
    public PlaceResponseDto update(Integer id, PlaceRequestDto dto) {
        Place p = placeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Place not found: id=" + id));

        // Move to another state if provided
        if (dto.getStateId() != null && (p.getState() == null || !dto.getStateId().equals(p.getState().getId()))) {
            State s = stateRepository.findById(dto.getStateId())
                    .orElseThrow(() -> new EntityNotFoundException("State not found: id=" + dto.getStateId()));
            p.setState(s);
        }

        // Set/Change county if provided (nullable is allowed)
        if (dto.getCountyId() != null) {
            if (dto.getCountyId() == 0) {
                p.setCounty(null); // explicit clear if you choose to support 0 as "clear"
            } else {
                County c = countyRepository.findById(dto.getCountyId())
                        .orElseThrow(() -> new EntityNotFoundException("County not found: id=" + dto.getCountyId()));
                p.setCounty(c);
            }
        }

        PlaceMapper.applyRequestDto(p, dto);

        Place saved = placeRepository.save(p);
        return PlaceMapper.toResponseDto(saved);
    }

    @Override
    public void delete(Integer id) {
        if (!placeRepository.existsById(id)) {
            throw new EntityNotFoundException("Place not found: id=" + id);
        }
        placeRepository.deleteById(id);
    }
}