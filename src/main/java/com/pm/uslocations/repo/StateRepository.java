package com.pm.uslocations.repo;

import com.pm.uslocations.model.State;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
    /**
     * Find a state by its 2-letter state code (case-insensitive).
     * Example: "NY" → New York
     */
    Optional<State> findByStateCodeIgnoreCase(String code);

    /**
     * Flexible search query that supports partial name matches
     * or exact code/postal abbreviation match.
     *
     * @param q search keyword (can be part of name, or a full code)
     * @param pageable limit & offset info (you can use PageRequest.of(page, size))
     * @return list of matching states
     */
    @Query("""
        SELECT s FROM State s
        WHERE (:q IS NULL OR
               LOWER(s.name) LIKE LOWER(CONCAT('%', :q, '%')) OR
               LOWER(s.stateCode) = LOWER(:q) OR
               LOWER(s.postalAbbr) = LOWER(:q))
        ORDER BY s.name ASC
        """)
    List<State> search(@Param("q") String q, Pageable pageable);

    State findByCapitalIgnoreCase(String capital);

}
