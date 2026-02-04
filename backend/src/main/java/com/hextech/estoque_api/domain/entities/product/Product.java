package com.hextech.estoque_api.domain.entities.product;

import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.stockProduct.StockProduct;
import com.hextech.estoque_api.domain.entities.unitMeasure.UnitMeasure;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private BigDecimal price;
    private BigDecimal totalQuantity;
    private BigDecimal stockMax;
    private BigDecimal stockMin;

    @ManyToOne
    @JoinColumn(name = "unit_measure_id")
    private UnitMeasure unitMeasure;

    @Formula("""
            CASE
                WHEN total_quantity > stock_max THEN 'HIGH'
                WHEN total_quantity < stock_min THEN 'LOW'
                ELSE 'NORMAL'
            END
            """)
    private String stockStatus;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    private Boolean isEnable = true;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<StockProduct> stocks = new HashSet<>();

    private Product(String code, String name, BigDecimal price, BigDecimal stockMax, BigDecimal stockMin, UnitMeasure unitMeasure, Company company, Boolean isEnable) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stockMax = stockMax;
        this.stockMin = stockMin;
        this.totalQuantity = BigDecimal.ZERO;
        this.unitMeasure = unitMeasure;
        this.company = company;
        this.isEnable = isEnable;
    }

    public static Product createNewProduct(String code, String name, BigDecimal price, BigDecimal stockMax, BigDecimal stockMin, UnitMeasure unitMeasure, Company company) {
        validateAttributes(code, name, price, stockMax, stockMin, unitMeasure);
        if (company == null) throw new IllegalArgumentException("Empresa do produto não pode ser nula.");
        return new Product(code, name, price, stockMax, stockMin, unitMeasure, company, true);
    }

    public void updateProduct(String code, String name, BigDecimal price, BigDecimal stockMax, BigDecimal stockMin, UnitMeasure unitMeasure) {
        validateAttributes(code, name, price, stockMax, stockMin, unitMeasure);
        this.code = code;
        this.name = name;
        this.price = price;
        this.stockMax = stockMax;
        this.stockMin = stockMin;
        this.unitMeasure = unitMeasure;
    }

    public boolean isCodeEqual(String code) {
        return this.code.equals(code);
    }

    private static void validateAttributes(String code, String name, BigDecimal price, BigDecimal stockMax, BigDecimal stockMin, UnitMeasure unitMeasure) {
        if (code == null || code.isBlank() || code.length() > 10)
            throw new IllegalArgumentException("Código do produto não pode ser nulo, vazio ou maior que 10 caracteres.");
        if (name == null || name.isBlank() || name.length() > 240)
            throw new IllegalArgumentException("Nome do produto não pode ser nulo, vazio ou maior que 240 caracteres.");
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Preço do produto não pode ser nulo, zero ou negativo.");
        if (stockMax.compareTo(stockMin) <= 0)
            throw new IllegalArgumentException("Estoque máximo não pode ser menor ou igual ao estoque mínimo.");
        if (unitMeasure == null || !unitMeasure.getIsEnable())
            throw new IllegalArgumentException("Unidade de medida não encontrada ou desabilitada.");
    }

    @Transient
    public StockStatus getStockStatusEnum() {
        return StockStatus.valueOf(this.stockStatus.toUpperCase());
    }

    public StockStatus getStockStatus() {
        if (totalQuantity.compareTo(stockMax) > 0) return StockStatus.HIGH;
        if (totalQuantity.compareTo(stockMin) < 0) return StockStatus.LOW;
        return StockStatus.NORMAL;
    }

    public void disableProduct() {
        this.name = this.name + " (desabilitado)";
        this.isEnable = false;
    }
}
