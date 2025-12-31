package com.hextech.estoque_api.domain.entities.unitMeasure;

import com.hextech.estoque_api.domain.entities.company.Company;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unit_measures")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UnitMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String acronym;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private UnitMeasure(String name, String acronym, Company company) {
        this.name = name;
        this.acronym = acronym;
        this.company = company;
    }

    public static UnitMeasure createNewUnitMeasure(String name, String acronym, Company company) {
        validateAttributes(name, acronym, company);
        return new UnitMeasure(name.toUpperCase(), acronym.toUpperCase(), company);
    }

    public void updateUnitMeasure(String name, String acronym) {
        validateAttributes(name, acronym, this.company);
        this.name = name.toUpperCase();
        this.acronym = acronym.toUpperCase();
    }

    private static void validateAttributes(String name, String acronym, Company company) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome da unidade de medida não pode ser nulo ou vazio.");
        if (acronym == null || acronym.isBlank()) throw new IllegalArgumentException("Sigla não pode ser nula ou vazia");
        if (company == null) throw new IllegalArgumentException("Empresa da unidade de medida não pode ser nula.");
    }
}
