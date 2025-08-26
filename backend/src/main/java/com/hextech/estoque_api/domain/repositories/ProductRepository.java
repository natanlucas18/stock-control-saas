package com.hextech.estoque_api.domain.repositories;

import com.hextech.estoque_api.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndClientId(Long id, Long currentClientId);
}
