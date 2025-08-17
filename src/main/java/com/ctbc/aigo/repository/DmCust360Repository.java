package com.ctbc.aigo.repository;

import com.ctbc.aigo.entity.DmCust360Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DmCust360Repository extends JpaRepository<DmCust360Entity, String> {
    List<DmCust360Entity> findByCustomerId(String customerId);
}
