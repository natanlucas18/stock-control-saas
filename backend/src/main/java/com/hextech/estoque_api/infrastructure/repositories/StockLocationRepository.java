package com.hextech.estoque_api.infrastructure.repositories;

import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockLocationRepository extends JpaRepository<StockLocation, Long> {

    Page<StockLocation> findByCompanyId(Long companyId, Pageable pageable);

    Optional<StockLocation> findByIdAndCompanyId(Long id, Long companyId);
}
