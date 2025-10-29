package com.pm.uslocations.repo;

import com.pm.uslocations.model.TimeZoneRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeZoneRefRepository extends JpaRepository<TimeZoneRef,Integer> {
}
