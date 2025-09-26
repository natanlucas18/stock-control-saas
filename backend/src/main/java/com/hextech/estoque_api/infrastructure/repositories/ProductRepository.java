package com.hextech.estoque_api.infrastructure.repositories;

import com.hextech.estoque_api.domain.entities.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
            SELECT p FROM Product p
            WHERE UPPER(p.name) LIKE UPPER(CONCAT('%', :name, '%'))
            AND p.company.id = :companyId
            """)
    Page<Product> findAllByNameAndCompanyId(String name, Long companyId, Pageable pageable);

    Optional<Product> findByIdAndCompanyId(Long id, Long companyId);
}
