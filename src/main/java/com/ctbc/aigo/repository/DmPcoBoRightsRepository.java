package com.ctbc.aigo.repository;

import com.ctbc.aigo.entity.DmPcoBoRightsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DmPcoBoRightsRepository extends JpaRepository<DmPcoBoRightsEntity, String> {
    DmPcoBoRightsEntity findByCustomerId(String customerId);
}
