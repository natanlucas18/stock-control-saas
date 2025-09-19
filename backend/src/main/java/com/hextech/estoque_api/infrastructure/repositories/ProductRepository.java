package com.hextech.estoque_api.infrastructure.repositories;

import com.hextech.estoque_api.domain.entities.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCompanyId(Long companyId);

    Optional<Product> findByIdAndCompanyId(Long id, Long companyId);
}
