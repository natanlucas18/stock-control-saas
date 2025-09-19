package com.hextech.estoque_api.domain.entities.product;

import com.hextech.estoque_api.domain.entities.Company;
import com.hextech.estoque_api.domain.entities.StockLocation;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal stockMax;
    private BigDecimal stockMin;
    private UnitMeasure unitMeasure;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "stock_location_id")
    private StockLocation stockLocation;

    public Product() {
    }

    private Product(String name, BigDecimal price, BigDecimal stockMax, BigDecimal stockMin, UnitMeasure unitMeasure, Company company, StockLocation stockLocation) {
        this.name = name;
        this.price = price;
        this.quantity = new BigDecimal(0);
        this.stockMax = stockMax;
        this.stockMin = stockMin;
        this.unitMeasure = unitMeasure;
        this.company = company;
        this.stockLocation = stockLocation;
    }

    public static Product createNewProduct(String name, BigDecimal price, BigDecimal stockMax, BigDecimal stockMin, UnitMeasure unitMeasure, Company company, StockLocation stockLocation) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome do produto não pode ser nulo ou vazio.");
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Preço do produto não pode ser nulo, zero ou negativo.");
        if (stockMax.compareTo(stockMin) <= 0) throw new IllegalArgumentException("Estoque máximo não pode ser menor ou igual que o estoque mínimo.");
        if (unitMeasure == null) throw new IllegalArgumentException("Unidade de medida do produto não pode ser nula.");
        if (company == null) throw new IllegalArgumentException("Empresa do produto não pode ser nula.");
        if (stockLocation == null) throw new IllegalArgumentException("Local de estoque do produto não pode ser nulo.");
        return new Product(name, price, stockMax, stockMin, unitMeasure, company, stockLocation);
    }

    public void updateProduct(String name, BigDecimal price, BigDecimal stockMax, BigDecimal stockMin, UnitMeasure unitMeasure, StockLocation stockLocation) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome do produto não pode ser nulo ou vazio.");
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Preço do produto não pode ser nulo, zero ou negativo.");
        if (stockMax.compareTo(stockMin) <= 0) throw new IllegalArgumentException("Estoque máximo não pode ser menor ou igual ao estoque mínimo.");
        if (unitMeasure == null) throw new IllegalArgumentException("Unidade de medida do produto não pode ser nula.");
        if (stockLocation == null) throw new IllegalArgumentException("Local de estoque do produto não pode ser nulo.");
        this.name = name;
        this.price = price;
        this.stockMax = stockMax;
        this.stockMin = stockMin;
        this.unitMeasure = unitMeasure;
        this.stockLocation = stockLocation;
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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

    public StockLocation getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(StockLocation stockLocation) {
        this.stockLocation = stockLocation;
    }

    public void decreaseQuantity(BigDecimal quantity) {
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        if (this.quantity.compareTo(quantity) < 0) {
            throw new IllegalArgumentException("Quantidade insuficiente em estoque.");
        }
        this.quantity = this.quantity.subtract(quantity);
    }

    public void increaseQuantity(BigDecimal quantity) {
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        } else {
            this.quantity = this.quantity.add(quantity);
        }
    }

    public StockStatus checkStockStatus() {
        if (quantity.compareTo(stockMin) < 0)
            return new StockStatus(StockStatus.Level.LOW);
        else if (quantity.compareTo(stockMax) > 0)
            return new StockStatus(StockStatus.Level.HIGH);
        else
            return new StockStatus(StockStatus.Level.NORMAL);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(quantity, product.quantity) && unitMeasure == product.unitMeasure;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, quantity, unitMeasure);
    }
}
