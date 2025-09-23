package com.hextech.estoque_api.infrastructure.repositories;

import com.hextech.estoque_api.domain.entities.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCompanyId(Long companyId, Pageable pageable);

    Optional<Product> findByIdAndCompanyId(Long id, Long companyId);
}
