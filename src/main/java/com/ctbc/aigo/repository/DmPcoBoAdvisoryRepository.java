package com.ctbc.aigo.repository;

import com.ctbc.aigo.entity.DmPcoBoAdvisoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DmPcoBoAdvisoryRepository extends JpaRepository<DmPcoBoAdvisoryEntity, String> {
    DmPcoBoAdvisoryEntity findByCustomerId(String customerId);
}
