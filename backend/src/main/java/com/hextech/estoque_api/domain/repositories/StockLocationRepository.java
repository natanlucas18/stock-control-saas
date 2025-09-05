package com.hextech.estoque_api.domain.repositories;

import com.hextech.estoque_api.domain.entities.StockLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockLocationRepository extends JpaRepository<StockLocation, Long> {

    List<StockLocation> findByCompanyId(Long companyId);

    Optional<StockLocation> findByIdAndCompanyId(Long id, Long companyId);
}
