package com.pm.uslocations.repo;

import com.pm.uslocations.model.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface CountyRepository extends JpaRepository<County, Integer> {
    /**
     * Find a county by its 5-character FIPS code (case-insensitive).
     * Example: "36001" → Albany County
     */
    Optional<County> findByCountyFipsIgnoreCase(String countyFips);
    @Query("""
  SELECT c FROM County c JOIN c.state s
  WHERE (:q IS NULL OR c.name LIKE CONCAT('%', :q, '%') OR UPPER(c.countyFips) = UPPER(:q))
    AND (:stateId IS NULL OR s.id = :stateId)
  ORDER BY c.name ASC
""")
    List<County> search(@Param("q") String q, @Param("stateId") Integer stateId, Pageable pageable);
}


