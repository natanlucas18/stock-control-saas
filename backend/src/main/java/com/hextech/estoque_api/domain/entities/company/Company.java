package com.hextech.estoque_api.domain.entities.company;

import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;
import com.hextech.estoque_api.domain.entities.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true, nullable = false, length = 14)
    private String cnpj;
    private boolean enabled;
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<User> users;
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<StockLocation> stockLocations;
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<Product> products;

    public Company(Long id, String nome, String cnpj, boolean enabled) {
        this.id = id;
        this.name = nome;
        this.cnpj = cnpj;
        this.enabled = enabled;
    }
}