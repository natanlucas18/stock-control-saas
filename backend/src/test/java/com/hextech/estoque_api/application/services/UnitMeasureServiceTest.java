package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.CompanyFactory;
import com.hextech.estoque_api.application.tests.UnitMeasureFactory;
import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.unitMeasure.UnitMeasure;
import com.hextech.estoque_api.domain.exceptions.BusinessException;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.infrastructure.repositories.CompanyRepository;
import com.hextech.estoque_api.infrastructure.repositories.ProductRepository;
import com.hextech.estoque_api.infrastructure.repositories.UnitMeasureRepository;
import com.hextech.estoque_api.interfaces.dtos.unitMeasure.UnitMeasureRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.unitMeasure.UnitMeasureResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitMeasureServiceTest {

    @Mock
    private UnitMeasureRepository unitMeasureRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UnitMeasureService unitMeasureService;

    private Company company;
    private UnitMeasure unitMeasure;
    private UnitMeasureRequestDTO unitMeasureRequestDTO;
    private Long companyId;
    private Long unitMeasureId;

    @BeforeEach
    void setUp() {
        companyId = 1L;
        unitMeasureId = 1L;
        company = CompanyFactory.createCompany(companyId);

        unitMeasure = UnitMeasureFactory.createUnitMeasure(unitMeasureId);

        unitMeasureRequestDTO = UnitMeasureFactory.createUnitMeasureRequestDTO();
    }

    @Test
    @DisplayName("Should find all unit measures by company ID successfully")
    void findAllByCompanyId_ShouldReturnPageOfUnitMeasures() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UnitMeasure> unitMeasurePage = new PageImpl<>(List.of(unitMeasure), pageable, 1);

        when(unitMeasureRepository.findAllByNameAndCompanyId(anyString(), anyLong(), any(Pageable.class)))
                .thenReturn(unitMeasurePage);

        Page<UnitMeasureResponseDTO> result = unitMeasureService.findAllByCompanyId("", companyId, pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals(unitMeasure.getName(), result.getContent().get(0).name());
        verify(unitMeasureRepository, times(1))
                .findAllByNameAndCompanyId(anyString(), eq(companyId), any(Pageable.class));
    }

    @Test
    @DisplayName("Should insert a new unit measure successfully")
    void insert_ShouldReturnUnitMeasureResponseDTO() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(unitMeasureRepository.existsByNameAndCompanyIdAndIsEnableIsTrueAndIdNot(
                anyString(), anyLong(), anyLong())).thenReturn(false);
        when(unitMeasureRepository.existsByAcronymAndCompanyIdAndIsEnableIsTrueAndIdNot(
                anyString(), anyLong(), anyLong())).thenReturn(false);
        when(unitMeasureRepository.save(any(UnitMeasure.class))).thenReturn(unitMeasure);

        UnitMeasureResponseDTO result = unitMeasureService.insert(unitMeasureRequestDTO, companyId);

        assertNotNull(result);
        assertEquals(unitMeasureRequestDTO.name(), result.name());
        assertEquals(unitMeasureRequestDTO.acronym(), result.acronym());
        verify(companyRepository, times(1)).findById(eq(companyId));
        verify(unitMeasureRepository, times(1)).save(any(UnitMeasure.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when company not found on insert")
    void insert_ShouldThrowResourceNotFoundException_WhenCompanyNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                unitMeasureService.insert(unitMeasureRequestDTO, companyId));

        assertEquals("Empresa não encontrada.", exception.getMessage());
        verify(companyRepository, times(1)).findById(eq(companyId));
        verify(unitMeasureRepository, never()).save(any(UnitMeasure.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when name already exists on insert")
    void insert_ShouldThrowBusinessException_WhenNameAlreadyExists() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(unitMeasureRepository.existsByNameAndCompanyIdAndIsEnableIsTrueAndIdNot(
                anyString(), anyLong(), anyLong())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () ->
                unitMeasureService.insert(unitMeasureRequestDTO, companyId));

        assertEquals("Nome da unidade de medida já existe.", exception.getMessage());
        verify(companyRepository, times(1)).findById(eq(companyId));
        verify(unitMeasureRepository, times(1))
                .existsByNameAndCompanyIdAndIsEnableIsTrueAndIdNot(anyString(), eq(companyId), anyLong());
        verify(unitMeasureRepository, never()).save(any(UnitMeasure.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when acronym already exists on insert")
    void insert_ShouldThrowBusinessException_WhenAcronymAlreadyExists() {
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
        when(unitMeasureRepository.existsByNameAndCompanyIdAndIsEnableIsTrueAndIdNot(
                anyString(), anyLong(), anyLong())).thenReturn(false);
        when(unitMeasureRepository.existsByAcronymAndCompanyIdAndIsEnableIsTrueAndIdNot(
                anyString(), anyLong(), anyLong())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () ->
                unitMeasureService.insert(unitMeasureRequestDTO, companyId));

        assertEquals("Sigla da unidade de medida já existe.", exception.getMessage());
        verify(companyRepository, times(1)).findById(eq(companyId));
        verify(unitMeasureRepository, times(1))
                .existsByNameAndCompanyIdAndIsEnableIsTrueAndIdNot(anyString(), eq(companyId), anyLong());
        verify(unitMeasureRepository, times(1))
                .existsByAcronymAndCompanyIdAndIsEnableIsTrueAndIdNot(anyString(), eq(companyId), anyLong());
        verify(unitMeasureRepository, never()).save(any(UnitMeasure.class));
    }

    @Test
    @DisplayName("Should update an existing unit measure successfully")
    void update_ShouldReturnUnitMeasureResponseDTO() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(unitMeasureRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(unitMeasure));
        when(unitMeasureRepository.existsByNameAndCompanyIdAndIsEnableIsTrueAndIdNot(
                anyString(), anyLong(), anyLong())).thenReturn(false);
        when(unitMeasureRepository.existsByAcronymAndCompanyIdAndIsEnableIsTrueAndIdNot(
                anyString(), anyLong(), anyLong())).thenReturn(false);
        when(unitMeasureRepository.save(any(UnitMeasure.class))).thenReturn(unitMeasure);

        UnitMeasureResponseDTO result = unitMeasureService.update(unitMeasureId, unitMeasureRequestDTO, companyId);

        assertNotNull(result);
        assertEquals(unitMeasureRequestDTO.name(), result.name());
        assertEquals(unitMeasureRequestDTO.acronym(), result.acronym());
        verify(companyRepository, times(1)).findById(eq(companyId));
        verify(unitMeasureRepository, times(1))
                .findByIdAndCompanyId(eq(unitMeasureId), eq(companyId));
        verify(unitMeasureRepository, times(1)).save(any(UnitMeasure.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when company not found on update")
    void update_ShouldThrowResourceNotFoundException_WhenCompanyNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                unitMeasureService.update(unitMeasureId, unitMeasureRequestDTO, companyId));

        assertEquals("Empresa não encontrada.", exception.getMessage());
        verify(companyRepository, times(1)).findById(eq(companyId));
        verify(unitMeasureRepository, never()).findByIdAndCompanyId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when unit measure not found on update")
    void update_ShouldThrowResourceNotFoundException_WhenUnitMeasureNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(unitMeasureRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                unitMeasureService.update(unitMeasureId, unitMeasureRequestDTO, companyId));

        assertEquals("U.M. não encontrada.", exception.getMessage());
        verify(companyRepository, times(1)).findById(eq(companyId));
        verify(unitMeasureRepository, times(1))
                .findByIdAndCompanyId(eq(unitMeasureId), eq(companyId));
        verify(unitMeasureRepository, never()).save(any(UnitMeasure.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when unit measure is disabled on update")
    void update_ShouldThrowBusinessException_WhenUnitMeasureIsDisabled() {
        unitMeasure.disableUnitMeasure(); // Disable the unit measure
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(unitMeasureRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(unitMeasure));

        BusinessException exception = assertThrows(BusinessException.class, () ->
                unitMeasureService.update(unitMeasureId, unitMeasureRequestDTO, companyId));

        assertEquals("A U.M. está desabilitada e não pode ser modificada.", exception.getMessage());
        verify(companyRepository, times(1)).findById(eq(companyId));
        verify(unitMeasureRepository, times(1))
                .findByIdAndCompanyId(eq(unitMeasureId), eq(companyId));
        verify(unitMeasureRepository, never()).save(any(UnitMeasure.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when acronym changes and products exist on update")
    void update_ShouldThrowBusinessException_WhenAcronymChangesAndProductsExist() {
        unitMeasure.setAcronym("NEWACRONYM"); // Change the acronym
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(unitMeasureRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(unitMeasure));
        when(productRepository.existsProductByUnitMeasureIdAndCompanyId(anyLong(), anyLong())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () ->
                unitMeasureService.update(unitMeasureId, unitMeasureRequestDTO, companyId));

        assertEquals("Não é permitido alterar a Sigla, U.M. possui produtos cadastrados.", exception.getMessage());
        verify(companyRepository, times(1)).findById(eq(companyId));
        verify(unitMeasureRepository, times(1))
                .findByIdAndCompanyId(eq(unitMeasureId), eq(companyId));
        verify(productRepository, times(1))
                .existsProductByUnitMeasureIdAndCompanyId(eq(unitMeasureId), eq(companyId));
        verify(unitMeasureRepository, never()).save(any(UnitMeasure.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when name already exists on update")
    void update_ShouldThrowBusinessException_WhenNameAlreadyExists() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(unitMeasureRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(unitMeasure));
        when(unitMeasureRepository.existsByNameAndCompanyIdAndIsEnableIsTrueAndIdNot(
                anyString(), anyLong(), anyLong())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () ->
                unitMeasureService.update(unitMeasureId, unitMeasureRequestDTO, companyId));

        assertEquals("Nome da unidade de medida já existe.", exception.getMessage());
        verify(companyRepository, times(1)).findById(eq(companyId));
        verify(unitMeasureRepository, times(1))
                .findByIdAndCompanyId(eq(unitMeasureId), eq(companyId));
        verify(unitMeasureRepository, times(1)).existsByNameAndCompanyIdAndIsEnableIsTrueAndIdNot(
                anyString(), eq(companyId), eq(unitMeasureId));
        verify(unitMeasureRepository, never()).save(any(UnitMeasure.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when acronym already exists on update")
    void update_ShouldThrowBusinessException_WhenAcronymAlreadyExists() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(unitMeasureRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(unitMeasure));
        when(unitMeasureRepository.existsByNameAndCompanyIdAndIsEnableIsTrueAndIdNot(
                anyString(), anyLong(), anyLong())).thenReturn(false);
        when(unitMeasureRepository.existsByAcronymAndCompanyIdAndIsEnableIsTrueAndIdNot(
                anyString(), anyLong(), anyLong())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () ->
                unitMeasureService.update(unitMeasureId, unitMeasureRequestDTO, companyId));

        assertEquals("Sigla da unidade de medida já existe.", exception.getMessage());
        verify(companyRepository, times(1)).findById(companyId);
        verify(unitMeasureRepository, times(1))
                .findByIdAndCompanyId(unitMeasureId, companyId);
        verify(unitMeasureRepository, times(1))
                .existsByNameAndCompanyIdAndIsEnableIsTrueAndIdNot(anyString(), eq(companyId), eq(unitMeasureId));
        verify(unitMeasureRepository, times(1))
                .existsByAcronymAndCompanyIdAndIsEnableIsTrueAndIdNot(anyString(), eq(companyId), eq(unitMeasureId));
        verify(unitMeasureRepository, never()).save(any(UnitMeasure.class));
    }

    @Test
    @DisplayName("Should delete unit measure successfully when no products are associated")
    void deleteByIdAndCompanyId_ShouldDeleteUnitMeasure() {
        when(unitMeasureRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(unitMeasure));
        when(productRepository.existsProductByUnitMeasureIdAndCompanyId(anyLong(), anyLong())).thenReturn(false);
        doNothing().when(unitMeasureRepository).delete(any(UnitMeasure.class));

        unitMeasureService.deleteByIdAndCompanyId(unitMeasureId, companyId);

        verify(unitMeasureRepository, times(1))
                .findByIdAndCompanyId(eq(unitMeasureId), eq(companyId));
        verify(productRepository, times(1))
                .existsProductByUnitMeasureIdAndCompanyId(eq(unitMeasureId), eq(companyId));
        verify(unitMeasureRepository, times(1)).delete(unitMeasure);
        verify(unitMeasureRepository, never()).save(any(UnitMeasure.class));
    }

    @Test
    @DisplayName("Should disable unit measure successfully when products are associated")
    void deleteByIdAndCompanyId_ShouldDisableUnitMeasure() {
        when(unitMeasureRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(unitMeasure));
        when(productRepository.existsProductByUnitMeasureIdAndCompanyId(anyLong(), anyLong())).thenReturn(true);
        when(unitMeasureRepository.save(any(UnitMeasure.class))).thenReturn(unitMeasure); // Mock save for disabling

        unitMeasureService.deleteByIdAndCompanyId(unitMeasureId, companyId);

        assertFalse(unitMeasure.getIsEnable()); // Should be true before disable, then false after save
        verify(unitMeasureRepository, times(1))
                .findByIdAndCompanyId(eq(unitMeasureId), eq(companyId));
        verify(productRepository, times(1))
                .existsProductByUnitMeasureIdAndCompanyId(eq(unitMeasureId), eq(companyId));
        verify(unitMeasureRepository, times(1)).save(any(UnitMeasure.class));
        verify(unitMeasureRepository, never()).delete(any(UnitMeasure.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when unit measure not found on delete")
    void deleteByIdAndCompanyId_ShouldThrowResourceNotFoundException_WhenUnitMeasureNotFound() {
        when(unitMeasureRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                unitMeasureService.deleteByIdAndCompanyId(unitMeasureId, companyId));

        assertEquals("U.M. não encontrada.", exception.getMessage());
        verify(unitMeasureRepository, times(1))
                .findByIdAndCompanyId(eq(unitMeasureId), eq(companyId));
        verify(productRepository, never()).existsProductByUnitMeasureIdAndCompanyId(anyLong(), anyLong());
        verify(unitMeasureRepository, never()).delete(any(UnitMeasure.class));
        verify(unitMeasureRepository, never()).save(any(UnitMeasure.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when unit measure is disabled on delete")
    void deleteByIdAndCompanyId_UnitMeasureDisabled() {
        unitMeasure.disableUnitMeasure();
        when(unitMeasureRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(unitMeasure));

        BusinessException exception = assertThrows(BusinessException.class, () ->
                unitMeasureService.deleteByIdAndCompanyId(unitMeasureId, companyId));

        assertEquals("A U.M. está desabilitada e não pode ser deletada.", exception.getMessage());
        verify(unitMeasureRepository, times(1))
                .findByIdAndCompanyId(eq(unitMeasureId), eq(companyId));
        verify(productRepository, never()).existsProductByUnitMeasureIdAndCompanyId(anyLong(), anyLong());
        verify(unitMeasureRepository, never()).delete(any(UnitMeasure.class));
        verify(unitMeasureRepository, never()).save(any(UnitMeasure.class));
    }
}
