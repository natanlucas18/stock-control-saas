package com.hextech.estoque_api.infrastructure.repositories;

import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockLocationRepository extends JpaRepository<StockLocation, Long> {

    @Query("""
            SELECT sl FROM StockLocation sl
            WHERE UPPER(sl.name) LIKE UPPER(CONCAT('%', :query, '%'))
            """)
    Page<StockLocation> findAllByNameAndCompanyId(String query, Long companyId, Pageable pageable);

    Optional<StockLocation> findByIdAndCompanyId(Long id, Long companyId);
}
