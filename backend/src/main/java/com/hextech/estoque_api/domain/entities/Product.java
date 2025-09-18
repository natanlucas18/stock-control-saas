package com.hextech.estoque_api.domain.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private UnitMeasure unitMeasure;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "stock_location_id")
    private StockLocation stockLocation;

    public Product() {
    }

    private Product(String name, Double price, UnitMeasure unitMeasure, Company company, StockLocation stockLocation) {
        this.name = name;
        this.price = price;
        this.quantity = 0;
        this.unitMeasure = unitMeasure;
        this.company = company;
        this.stockLocation = stockLocation;
    }

    public static Product createNewProduct(String name, Double price, UnitMeasure unitMeasure, Company company, StockLocation stockLocation) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome do produto não pode ser nulo ou vazio");
        if (price == null || price <= 0) throw new IllegalArgumentException("Preço do produto não pode ser nulo, zero ou negativo");
        if (unitMeasure == null) throw new IllegalArgumentException("Unidade de medida do produto não pode ser nula");
        if (company == null) throw new IllegalArgumentException("Empresa do produto não pode ser nula");
        if (stockLocation == null) throw new IllegalArgumentException("Local de estoque do produto não pode ser nulo");
        return new Product(name, price, unitMeasure, company, stockLocation);
    }

    public void updateProduct(String name, Double price, UnitMeasure unitMeasure, StockLocation stockLocation) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome do produto não pode ser nulo ou vazio");
        if (price == null || price <= 0) throw new IllegalArgumentException("Preço do produto não pode ser nulo, zero ou negativo");
        if (unitMeasure == null) throw new IllegalArgumentException("Unidade de medida do produto não pode ser nula");
        if (stockLocation == null) throw new IllegalArgumentException("Local de estoque do produto não pode ser nulo");
        this.name = name;
        this.price = price;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public void decreaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        if (this.quantity < amount) {
            throw new IllegalArgumentException("Quantidade insuficiente em estoque");
        }
        this.quantity -= amount;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.quantity += amount;
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
