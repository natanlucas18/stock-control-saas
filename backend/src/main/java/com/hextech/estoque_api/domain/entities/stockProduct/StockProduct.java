package com.hextech.estoque_api.domain.entities.stockProduct;

import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "stock_product")
public class StockProduct {

    @EmbeddedId
    private StockProductId id;
    private BigDecimal quantity;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @MapsId("stockLocationId")
    @JoinColumn(name = "stock_location_id")
    private StockLocation stockLocation;

    public StockProduct() {
    }

    public StockProduct(StockProductId id, BigDecimal quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public StockProductId getId() {
        return id;
    }

    public void setId(StockProductId id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public StockLocation getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(StockLocation stockLocation) {
        this.stockLocation = stockLocation;
    }
}
