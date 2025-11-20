package com.hextech.estoque_api.infrastructure.repositories;

import com.hextech.estoque_api.domain.entities.stockProduct.StockProduct;
import com.hextech.estoque_api.domain.entities.stockProduct.StockProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface StockProductRepository extends JpaRepository<StockProduct, StockProductId> {

    Optional<StockProduct> findByStockLocationIdAndProductId(Long stockLocationId, Long productId);

    @Query("""
            SELECT COALESCE(SUM(sp.quantity), 0)
            FROM StockProduct sp
            WHERE sp.product.id = :productId
            """)
    BigDecimal sumByProductId(Long productId);
}
