package com.hextech.estoque_api.infrastructure.repositories;

import com.hextech.estoque_api.domain.entities.Movement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    @Query(nativeQuery = false, value =
            "SELECT m FROM Movement m " +
            "WHERE m.company.id = :companyId " +
            "AND m.moment BETWEEN :startDate AND :endDate " +
            "ORDER BY m.moment DESC"
    )
    Page<Movement> searchAllByCompanyIdAndDate(Long companyId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
