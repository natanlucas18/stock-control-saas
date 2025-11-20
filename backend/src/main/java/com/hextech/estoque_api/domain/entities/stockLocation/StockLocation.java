package com.hextech.estoque_api.domain.entities.stockLocation;

import com.hextech.estoque_api.domain.entities.stockProduct.StockProduct;
import com.hextech.estoque_api.domain.entities.company.Company;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "stock_locations")
public class StockLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "stockLocation")
    private Set<StockProduct> products = new HashSet<>();

    public StockLocation() {
    }

    private StockLocation(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public static StockLocation createNewStockLocation(String name, Company company) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        if (company == null) throw new IllegalArgumentException("Empresa não pode ser nula.");
        return new StockLocation(name, company);
    }

    public void updateStockLocation(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        this.name = name;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<StockProduct> getProducts() {
        return products;
    }

    public void setProducts(Set<StockProduct> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StockLocation stockLocation = (StockLocation) o;
        return Objects.equals(id, stockLocation.id) && Objects.equals(name, stockLocation.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
