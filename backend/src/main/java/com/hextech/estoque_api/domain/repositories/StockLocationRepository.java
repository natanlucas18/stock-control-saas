package com.hextech.estoque_api.domain.repositories;

import com.hextech.estoque_api.domain.entities.StockLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockLocationRepository extends JpaRepository<StockLocation, Long> {
}
