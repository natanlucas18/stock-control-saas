package com.hextech.estoque_api.domain.entities.product;

import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.stockProduct.StockProduct;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    private UnitMeasure unitMeasure;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<StockProduct> stocks = new HashSet<>();

    private Product(String code, String name, BigDecimal price, BigDecimal stockMax, BigDecimal stockMin, UnitMeasure unitMeasure, Company company) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stockMax = stockMax;
        this.stockMin = stockMin;
        this.totalQuantity = BigDecimal.ZERO;
        this.unitMeasure = unitMeasure;
        this.company = company;
    }

    public static Product createNewProduct(String code, String name, BigDecimal price, BigDecimal stockMax, BigDecimal stockMin, UnitMeasure unitMeasure, Company company) {
        validateAttribute(code, name, price, stockMax, stockMin, unitMeasure);
        if (company == null) throw new IllegalArgumentException("Empresa do produto não pode ser nula.");
        return new Product(code, name, price, stockMax, stockMin, unitMeasure, company);
    }

    public void updateProduct(String code, String name, BigDecimal price, BigDecimal stockMax, BigDecimal stockMin, UnitMeasure unitMeasure) {
        validateAttribute(code, name, price, stockMax, stockMin, unitMeasure);
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

    private static void validateAttribute(String code, String name, BigDecimal price, BigDecimal stockMax, BigDecimal stockMin, UnitMeasure unitMeasure) {
        if (code == null || code.isBlank() || code.length() > 10) throw new IllegalArgumentException("Código do produto não pode ser nulo, vazio ou maior que 10 caracteres.");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome do produto não pode ser nulo ou vazio.");
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Preço do produto não pode ser nulo, zero ou negativo.");
        if (stockMax.compareTo(stockMin) <= 0) throw new IllegalArgumentException("Estoque máximo não pode ser menor ou igual ao estoque mínimo.");
        if (unitMeasure == null) throw new IllegalArgumentException("Unidade de medida do produto não pode ser nula.");
    }

    public StockStatus checkStockStatus() {
        if (totalQuantity.compareTo(stockMin) < 0)
            return StockStatus.LOW;
        else if (totalQuantity.compareTo(stockMax) > 0)
            return StockStatus.HIGH;
        else
            return StockStatus.NORMAL;
    }
}
