package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.CompanyFactory;
import com.hextech.estoque_api.application.tests.StockLocationFactory;
import com.hextech.estoque_api.domain.entities.StockLocation;
import com.hextech.estoque_api.domain.exceptions.DeletionConflictException;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.infrastructure.repositories.CompanyRepository;
import com.hextech.estoque_api.infrastructure.repositories.StockLocationRepository;
import com.hextech.estoque_api.interfaces.dtos.stockLocations.StockLocationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class StockLocationServiceTest {

    @Mock
    private StockLocationRepository repository;
    @Mock
    private CompanyRepository companyRepository;
    @InjectMocks
    private StockLocationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all stock locations paged for the current company")
    void findAllByCompanyIdPagedCase1() {
        Long companyId = 1L;
        Long stockLocationId = 1L;
        long totalElements = 1;

        Pageable pageable = PageRequest.of(0, 8);
        Page<StockLocation> page = new PageImpl<>(List.of(StockLocationFactory.createStockLocation(stockLocationId)), pageable, totalElements);

        when(repository.findByCompanyId(anyLong(), any())).thenReturn(page);

        Page<StockLocationDTO> result = service.findAllByCompanyId(companyId, pageable);

        verify(repository, times(1)).findByCompanyId(anyLong(), any());
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
    }

    @Test
    @DisplayName("Should return stock location by id and company id")
    void findByIdAndCompanyIdCase1() {
        Long companyId = 1L;
        Long stockLocationId = 1L;

        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(stockLocationId)));

        StockLocationDTO result = service.findByIdAndCompanyId(stockLocationId, companyId);

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        assertNotNull(result);
        assertEquals(stockLocationId, result.id());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when stock location not found by id")
    void findByIdAndCompanyIdCase2() {
        Long companyId = 1L;
        Long stockLocationId = 1L;

        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            service.findByIdAndCompanyId(stockLocationId, companyId);
        });

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        assertEquals("Local de estoque não encontrado.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should create a new stock location successfully")
    void insertCase1() {
        Long companyId = 1L;
        Long stockLocationId = 1L;
        StockLocationDTO requestDTO = StockLocationFactory.createStockLocationDTO(stockLocationId);

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(companyId)));
        when(repository.save(any())).thenReturn(StockLocationFactory.createStockLocation(stockLocationId));

        StockLocationDTO result = service.insert(requestDTO, companyId);

        verify(repository, times(1)).save(any());
        verify(companyRepository, times(1)).findById(anyLong());
        assertNotNull(result);
        assertEquals(stockLocationId, result.id());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when non-existing company")
    void insertCase2() {
        Long companyId = 1L;
        Long stockLocationId = 1L;
        StockLocationDTO requestDTO = StockLocationFactory.createStockLocationDTO(stockLocationId);

        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            service.insert(requestDTO, companyId);
        });

        verify(companyRepository, times(1)).findById(anyLong());
        verify(repository, times(0)).save(any());
        assertEquals("Empresa não encontrada.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should update an existing stock location successfully")
    void updateCase1() {
        Long companyId = 1L;
        Long stockLocationId = 1L;
        StockLocationDTO requestDTO = StockLocationFactory.createStockLocationDTO(stockLocationId);

        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(stockLocationId)));
        when(repository.save(any())).thenReturn(StockLocationFactory.createStockLocation(stockLocationId));

        StockLocationDTO result = service.update(stockLocationId, requestDTO, companyId);

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(1)).save(any());

        assertNotNull(result);
        assertEquals(stockLocationId, result.id());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating non-existing stock location")
    void updateCase2() {
        Long companyId = 1L;
        Long stockLocationId = 1L;
        StockLocationDTO requestDTO = StockLocationFactory.createStockLocationDTO(stockLocationId);

        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            service.update(stockLocationId, requestDTO, companyId);
        });

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(0)).save(any());
        assertEquals("Local de estoque não encontrado.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should delete an existing stock location successfully")
    void deleteByIdAndCompanyIdCase1() {
        Long companyId = 1L;
        Long stockLocationId = 1L;

        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(stockLocationId)));
        doNothing().when(repository).delete(any());

        assertDoesNotThrow(() -> service.deleteByIdAndCompanyId(stockLocationId, companyId));

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existing stock location")
    void deleteByIdAndCompanyIdCase2() {
        Long companyId = 1L;
        Long stockLocationId = 1L;

        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteByIdAndCompanyId(stockLocationId, companyId);
        });

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        assertEquals("Local de estoque não encontrado.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw DeletionConflictException when deletion violates integrity constraints")
    void deleteByIdAndCompanyIdCase3() {
        Long companyId = 1L;
        Long stockLocationId = 1L;

        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(stockLocationId)));
        doThrow(new org.springframework.dao.DataIntegrityViolationException("Integrity violation")).when(repository).delete(any());

        Exception thrown = assertThrows(DeletionConflictException.class, () -> {
            service.deleteByIdAndCompanyId(stockLocationId, companyId);
        });

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(1)).delete(any());
        assertEquals("Falha na Integridade referencial.", thrown.getMessage());
    }
}