package com.hextech.estoque_api.infrastructure.repositories;

import com.hextech.estoque_api.domain.entities.unitMeasure.UnitMeasure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitMeasureRepository extends JpaRepository<UnitMeasure, Long> {

    @Query(value = """
                SELECT obj FROM UnitMeasure obj
                WHERE (UPPER(obj.name) LIKE UPPER(CONCAT('%', :query, '%'))
                OR UPPER(obj.acronym) LIKE UPPER(CONCAT('%', :query, '%')))
                AND obj.company.id = :companyId
            """)
    Page<UnitMeasure> findAllByNameAndCompanyId(String query, Long companyId, Pageable pageable);

    Optional<UnitMeasure> findByIdAndCompanyId(Long id, Long companyId);

    boolean existsByNameAndCompanyIdAndIsEnableIsTrueAndIdNot(String name, Long companyId, Long id);

    boolean existsByAcronymAndCompanyIdAndIsEnableIsTrueAndIdNot(String acronym, Long companyId, Long id);
}
