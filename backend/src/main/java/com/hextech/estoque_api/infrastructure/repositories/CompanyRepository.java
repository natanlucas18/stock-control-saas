package com.hextech.estoque_api.infrastructure.repositories;

import com.hextech.estoque_api.domain.entities.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
