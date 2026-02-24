package com.pm.uslocations.repo;

import com.pm.uslocations.enums.PlaceType;
import com.pm.uslocations.model.Place;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {

    Optional<Place> findByPlaceFipsIgnoreCase(String placeFips);

    @Query("""
        SELECT p FROM Place p
        JOIN p.state s
        LEFT JOIN p.county c
        WHERE (:q IS NULL OR p.name LIKE CONCAT('%', :q, '%') OR UPPER(p.placeFips) = UPPER(:q))
          AND (:stateId  IS NULL OR s.id = :stateId)
          AND (:countyId IS NULL OR c.id = :countyId)
          AND (:type     IS NULL OR p.placeType = :type)
        ORDER BY p.name ASC
        """)
    List<Place> search(
            @Param("q") String q,
            @Param("stateId") Integer stateId,
            @Param("countyId") Integer countyId,
            @Param("type") PlaceType type,
            Pageable pageable
    );
}