package com.hextech.estoque_api.domain.entities.stockLocation;

import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.stockProduct.StockProduct;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stock_locations")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class StockLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @OneToMany(mappedBy = "stockLocation", fetch = FetchType.LAZY)
    private Set<StockProduct> products = new HashSet<>();

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
}
