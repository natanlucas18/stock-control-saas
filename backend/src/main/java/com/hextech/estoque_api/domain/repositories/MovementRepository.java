package com.hextech.estoque_api.domain.repositories;

import com.hextech.estoque_api.domain.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    @Query(nativeQuery = false, value =
            "SELECT m FROM Movement m " +
            "WHERE m.client.id = :clientId " +
            "AND m.moment BETWEEN :startDate AND :endDate " +
            "ORDER BY m.moment DESC"
    )
    List<Movement> searchAllByClientIdAndDate(Long clientId, LocalDate startDate, LocalDate endDate);
}
