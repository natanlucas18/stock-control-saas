package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.StockLocationFactory;
import com.hextech.estoque_api.domain.exceptions.DeletionConflictException;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.infrastructure.repositories.StockLocationRepository;
import com.hextech.estoque_api.infrastructure.security.utils.AuthContext;
import com.hextech.estoque_api.interfaces.dtos.stockLocations.StockLocationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class StockLocationServiceTest {

    @Mock
    private AuthContext authContext;
    @Mock
    private StockLocationRepository repository;
    @InjectMocks
    private StockLocationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all stock locations for the current company")
    void findAllByCompanyIdCase1() {
        Long stockLocationId = 1L;

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findByCompanyId(anyLong())).thenReturn(List.of(StockLocationFactory.createStockLocation(stockLocationId)));

        List<StockLocationDTO> result = service.findAllByCompanyId();

        verify(repository, times(1)).findByCompanyId(anyLong());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(stockLocationId, result.get(0).id());
    }

    @Test
    @DisplayName("Should return stock location by id and company id")
    void findByIdAndCompanyIdCase1() {
        Long stockLocationId = 1L;

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(stockLocationId)));

        StockLocationDTO result = service.findByIdAndCompanyId(stockLocationId);

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());

        assertNotNull(result);
        assertEquals(stockLocationId, result.id());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when stock location not found by id")
    void findByIdAndCompanyIdCase2() {
        Long stockLocationId = 1L;

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            service.findByIdAndCompanyId(stockLocationId);
        });

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());

        assertEquals("Local de estoque não encontrado.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should create a new stock location successfully")
    void insertCase1() {
        Long stockLocationId = 1L;
        StockLocationDTO requestDTO = StockLocationFactory.createStockLocationDTO(stockLocationId);

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.save(any())).thenReturn(StockLocationFactory.createStockLocation(stockLocationId));

        StockLocationDTO result = service.insert(requestDTO);

        verify(repository, times(1)).save(any());

        assertNotNull(result);
        assertEquals(stockLocationId, result.id());
    }

    @Test
    @DisplayName("Should update an existing stock location successfully")
    void updateCase1() {
        Long stockLocationId = 1L;
        StockLocationDTO requestDTO = StockLocationFactory.createStockLocationDTO(stockLocationId);

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(stockLocationId)));
        when(repository.save(any())).thenReturn(StockLocationFactory.createStockLocation(stockLocationId));

        StockLocationDTO result = service.update(stockLocationId, requestDTO);

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(1)).save(any());

        assertNotNull(result);
        assertEquals(stockLocationId, result.id());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating non-existing stock location")
    void updateCase2() {
        Long stockLocationId = 1L;
        StockLocationDTO requestDTO = StockLocationFactory.createStockLocationDTO(stockLocationId);

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            service.update(stockLocationId, requestDTO);
        });

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        assertEquals("Local de estoque não encontrado.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should delete an existing stock location successfully")
    void deleteByIdAndCompanyIdCase1() {
        Long stockLocationId = 1L;

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(stockLocationId)));
        doNothing().when(repository).delete(any());

        assertDoesNotThrow(() -> service.deleteByIdAndCompanyId(stockLocationId));

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existing stock location")
    void deleteByIdAndCompanyIdCase2() {
        Long stockLocationId = 1L;

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteByIdAndCompanyId(stockLocationId);
        });

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        assertEquals("Local de estoque não encontrado.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw DeletionConflictException when deletion violates integrity constraints")
    void deleteByIdAndCompanyIdCase3() {
        Long stockLocationId = 1L;

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(stockLocationId)));
        doThrow(new org.springframework.dao.DataIntegrityViolationException("Integrity violation")).when(repository).delete(any());

        Exception thrown = assertThrows(DeletionConflictException.class, () -> {
            service.deleteByIdAndCompanyId(stockLocationId);
        });

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(1)).delete(any());
        assertEquals("Falha na Integridade referencial.", thrown.getMessage());
    }
}