package com.hextech.estoque_api.domain.entities.company;

import com.hextech.estoque_api.domain.entities.user.User;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true, nullable = false, length = 14)
    private String cnpj;
    private boolean enabled;

    @OneToMany(mappedBy = "company")
    private List<User> users;

    @OneToMany(mappedBy = "company")
    private List<StockLocation> stockLocations;

    @OneToMany(mappedBy = "company")
    private List<Product> products;

    public Company() {
    }

    public Company(Long id, String nome, String cnpj, boolean enabled) {
        this.id = id;
        this.name = nome;
        this.cnpj = cnpj;
        this.enabled = enabled;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<StockLocation> getStockLocations() {
        return stockLocations;
    }

    public void setStockLocations(List<StockLocation> stockLocations) {
        this.stockLocations = stockLocations;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return enabled == company.enabled && Objects.equals(id, company.id) && Objects.equals(name, company.name) && Objects.equals(cnpj, company.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cnpj, enabled);
    }
}
