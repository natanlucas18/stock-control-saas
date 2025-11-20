package com.hextech.estoque_api.application.tests;

import com.hextech.estoque_api.domain.entities.company.Company;

public class CompanyFactory {

    public static Company createCompany(Long id) {
        return new Company(id, "Company test", "999999999999", true);
    }
}
