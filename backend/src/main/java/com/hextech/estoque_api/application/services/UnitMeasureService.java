package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.unitMeasure.UnitMeasure;
import com.hextech.estoque_api.domain.exceptions.BusinessException;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.infrastructure.repositories.CompanyRepository;
import com.hextech.estoque_api.infrastructure.repositories.ProductRepository;
import com.hextech.estoque_api.infrastructure.repositories.UnitMeasureRepository;
import com.hextech.estoque_api.interfaces.dtos.unitMeasure.UnitMeasureRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.unitMeasure.UnitMeasureResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnitMeasureService {

    @Autowired
    private UnitMeasureRepository repository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<UnitMeasureResponseDTO> findAllByCompanyId(String query, Long currentCompanyId, Pageable pageable) {
        Page<UnitMeasure> UnitMeasures = repository.findAllByNameAndCompanyId(query, currentCompanyId, pageable);
        return UnitMeasures.map(UnitMeasureResponseDTO::new);
    }

    @Transactional
    public UnitMeasureResponseDTO insert(UnitMeasureRequestDTO request, Long currentCompanyId) {
        Company company = companyRepository.findById(currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));

        validateNameAndAcronym(null, request.name(), request.acronym(), company.getId());

        UnitMeasure entity = UnitMeasure.createNewUnitMeasure(request.name(), request.acronym(), company);

        entity = repository.save(entity);
        return new UnitMeasureResponseDTO(entity);
    }

    @Transactional
    public UnitMeasureResponseDTO update(Long id, UnitMeasureRequestDTO request, Long currentCompanyId) {
        companyRepository.findById(currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));
        UnitMeasure entity = repository.findByIdAndCompanyId(id, currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("U.M. não encontrada."));

        if (!entity.getIsEnable())
            throw new BusinessException("A U.M. está desabilitada e não pode ser modificada.");

        if (!entity.getAcronym().equals(request.acronym())) {
            if (productRepository.existsProductByUnitMeasureIdAndCompanyId(entity.getId(), currentCompanyId))
                throw new BusinessException("Não é permitido alterar a Sigla, U.M. possui produtos cadastrados.");
        }

        validateNameAndAcronym(id, request.name(), request.acronym(), currentCompanyId);

        entity.updateUnitMeasure(request.name(), request.acronym());

        entity = repository.save(entity);
        return new UnitMeasureResponseDTO(entity);
    }

    @Transactional
    public void deleteByIdAndCompanyId(Long id, Long currentCompanyId) {
        UnitMeasure entity = repository.findByIdAndCompanyId(id, currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("U.M. não encontrada."));

        if (!entity.getIsEnable())
            throw new BusinessException("A U.M. está desabilitada e não pode ser deletada.");

        boolean hasProducts = productRepository.existsProductByUnitMeasureIdAndCompanyId(entity.getId(), currentCompanyId);

        if (hasProducts) {
            entity.disableUnitMeasure();
            repository.save(entity);
        } else {
            repository.delete(entity);
        }
    }

    private void validateNameAndAcronym(Long id, String name, String acronym, Long companyId) {
        Long idToIgnore = (id == null) ? -1L : id;

        if (repository.existsByNameAndCompanyIdAndIsEnableIsTrueAndIdNot(name, companyId, idToIgnore))
            throw new BusinessException("Nome da unidade de medida já existe.");

        if (repository.existsByAcronymAndCompanyIdAndIsEnableIsTrueAndIdNot(acronym, companyId, idToIgnore))
            throw new BusinessException("Sigla da unidade de medida já existe.");
    }
}


