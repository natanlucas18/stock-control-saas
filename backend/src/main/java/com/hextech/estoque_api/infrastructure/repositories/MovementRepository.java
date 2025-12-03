package com.hextech.estoque_api.infrastructure.repositories;

import com.hextech.estoque_api.domain.entities.movement.Movement;
import com.hextech.estoque_api.domain.entities.movement.MovementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    @Query(value = """
            SELECT m FROM Movement m
            WHERE m.company.id = :companyId
            AND m.moment BETWEEN :startDate AND :endDate
            """
    )
    Page<Movement> searchAllByCompanyIdAndDate(Long companyId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query(value = """
            SELECT m FROM Movement m
            WHERE m.company.id = :companyId
            AND m.moment BETWEEN :startDate AND :endDate
            AND ((:type IS NULL) OR (m.type = :type))
            AND ((:productId IS NULL) OR (m.product.id = :productId))
            """)
    Page<Movement> searchAllTest(@Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate,
                                 @Param("type") MovementType type,
                                 @Param("productId") Long productId,
                                 @Param("companyId") Long companyId,
                                 Pageable pageable);

    @Query("""
            SELECT COUNT(m) > 0 FROM Movement m
            WHERE m.product.id = :productId
            """)
    boolean existsMovementByProductId(Long productId);

    @Query("""
            SELECT COUNT(m) > 0 FROM Movement m
            WHERE m.fromStockLocation.id = :stockLocationId
            OR m.toStockLocation.id = :stockLocationId
            """)
    boolean existsMovementByStockLocationId(Long stockLocationId);
}
