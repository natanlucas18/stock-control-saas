package com.hextech.estoque_api.infrastructure.repositories;

import com.hextech.estoque_api.domain.entities.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
            SELECT p FROM Product p
            WHERE UPPER(p.name) LIKE UPPER(CONCAT('%', :name, '%'))
            AND p.company.id = :companyId
            """)
    Page<Product> findAllByNameAndCompanyId(String name, Long companyId, Pageable pageable);

    @EntityGraph(attributePaths = {"stocks"})
    Optional<Product> findByIdAndCompanyId(Long id, Long companyId);

    @Modifying
    @Query("""
            UPDATE Product p
            SET p.totalQuantity = :quantity
            WHERE p.id = :productId
            """)
    void updateTotalQuantity(Long productId, BigDecimal quantity);

    boolean existsByCodeAndCompanyId(String code, Long companyId);

    @Query(value = """
            SELECT p FROM Product p
            WHERE (:id IS NULL OR p.id = :id)
            AND p.company.id = :companyId
            """)
    Page<Product> searchAllProducts(Long id, Long companyId, Pageable pageable);
}
