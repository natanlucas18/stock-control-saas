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

    public Product() {
    }

    public Product(Long id, String name, Double price, Integer quantity, UnitMeasure unitMeasure) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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
