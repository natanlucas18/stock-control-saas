package com.hextech.estoque_api.domain.entities.product;

import com.hextech.estoque_api.domain.entities.stockProduct.StockProduct;
import com.hextech.estoque_api.domain.entities.company.Company;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal totalQuantity;
    private BigDecimal stockMax;
    private BigDecimal stockMin;
    private UnitMeasure unitMeasure;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "product")
    private Set<StockProduct> stocks = new HashSet<>();

    public Product() {
    }

    private Product(String name, BigDecimal price, BigDecimal stockMax, BigDecimal stockMin, UnitMeasure unitMeasure, Company company) {
        this.name = name;
        this.price = price;
        this.stockMax = stockMax;
        this.stockMin = stockMin;
        this.totalQuantity = BigDecimal.ZERO;
        this.unitMeasure = unitMeasure;
        this.company = company;
    }

    public static Product createNewProduct(String name, BigDecimal price, BigDecimal stockMax, BigDecimal stockMin, UnitMeasure unitMeasure, Company company) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome do produto não pode ser nulo ou vazio.");
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Preço do produto não pode ser nulo, zero ou negativo.");
        if (stockMax.compareTo(stockMin) <= 0) throw new IllegalArgumentException("Estoque máximo não pode ser menor ou igual que o estoque mínimo.");
        if (unitMeasure == null) throw new IllegalArgumentException("Unidade de medida do produto não pode ser nula.");
        if (company == null) throw new IllegalArgumentException("Empresa do produto não pode ser nula.");
        return new Product(name, price, stockMax, stockMin, unitMeasure, company);
    }

    public void updateProduct(String name, BigDecimal price, BigDecimal stockMax, BigDecimal stockMin, UnitMeasure unitMeasure) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome do produto não pode ser nulo ou vazio.");
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Preço do produto não pode ser nulo, zero ou negativo.");
        if (stockMax.compareTo(stockMin) <= 0) throw new IllegalArgumentException("Estoque máximo não pode ser menor ou igual ao estoque mínimo.");
        if (unitMeasure == null) throw new IllegalArgumentException("Unidade de medida do produto não pode ser nula.");
        this.name = name;
        this.price = price;
        this.stockMax = stockMax;
        this.stockMin = stockMin;
        this.unitMeasure = unitMeasure;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getStockMax() {
        return stockMax;
    }

    public void setStockMax(BigDecimal stockMax) {
        this.stockMax = stockMax;
    }

    public BigDecimal getStockMin() {
        return stockMin;
    }

    public void setStockMin(BigDecimal stockMin) {
        this.stockMin = stockMin;
    }

    public UnitMeasure getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(UnitMeasure unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<StockProduct> getStocks() {
        return stocks;
    }

    public void setStocks(Set<StockProduct> stocks) {
        this.stocks = stocks;
    }

    public StockStatus checkStockStatus() {
        if (totalQuantity.compareTo(stockMin) < 0)
            return StockStatus.LOW;
        else if (totalQuantity.compareTo(stockMax) > 0)
            return StockStatus.HIGH;
        else
            return StockStatus.NORMAL;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(totalQuantity, product.totalQuantity) && unitMeasure == product.unitMeasure;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, totalQuantity, unitMeasure);
    }
}
