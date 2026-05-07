package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.CompanyFactory;
import com.hextech.estoque_api.application.tests.StockLocationFactory;
import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;
import com.hextech.estoque_api.domain.exceptions.DeletionConflictException;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.infrastructure.repositories.CompanyRepository;
import com.hextech.estoque_api.infrastructure.repositories.MovementRepository;
import com.hextech.estoque_api.infrastructure.repositories.StockLocationRepository;
import com.hextech.estoque_api.interfaces.dtos.stockLocations.StockLocationDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockLocationServiceTest {

    @Mock
    private StockLocationRepository stockLocationRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private MovementRepository movementRepository;
    @InjectMocks
    private StockLocationService stockLocationService;

    private Long existingStockLocationId;
    private Long nonExistingStockLocationId;
    private Long existingCompanyId;
    private Long nonExistingCompanyId;
    private StockLocation stockLocation;
    private Company company;
    private StockLocationDTO stockLocationDTO;

    @BeforeEach
    void setUp() {
        existingStockLocationId = 1L;
        nonExistingStockLocationId = 99L;
        existingCompanyId = 1L;
        nonExistingCompanyId = 99L;

        company = CompanyFactory.createCompany(existingCompanyId);

        stockLocation = StockLocationFactory.createStockLocation(existingStockLocationId);

        stockLocationDTO = StockLocationFactory.createStockLocationDTO(existingStockLocationId);
    }

    @Test
    @DisplayName("Should find all stock locations by company ID successfully")
    void findAllByCompanyId_ShouldReturnPageOfStockLocationDTO() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<StockLocation> stockLocationPage = new PageImpl<>(List.of(stockLocation), pageable, 1);

        when(stockLocationRepository.findAllByNameAndCompanyId(anyString(), anyLong(), any(Pageable.class)))
                .thenReturn(stockLocationPage);

        Page<StockLocationDTO> result = stockLocationService.findAllByCompanyId("Test", existingCompanyId, pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals(stockLocationDTO.name(), result.getContent().get(0).name());
        verify(stockLocationRepository, times(1))
                .findAllByNameAndCompanyId(anyString(), eq(existingCompanyId), any(Pageable.class));
    }

    @Test
    @DisplayName("Should find stock location by ID and company ID successfully")
    void findByIdAndCompanyId_ShouldReturnStockLocationDTO() {
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(stockLocation));

        StockLocationDTO result = stockLocationService.findByIdAndCompanyId(existingStockLocationId, existingCompanyId);

        assertNotNull(result);
        assertEquals(stockLocationDTO.id(), result.id());
        assertEquals(stockLocationDTO.name(), result.name());
        verify(stockLocationRepository, times(1))
                .findByIdAndCompanyId(eq(existingStockLocationId), eq(existingCompanyId));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when stock location not found by ID and company ID")
    void findByIdAndCompanyId_ShouldThrowResourceNotFoundException_WhenStockLocationNotFound() {
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                stockLocationService.findByIdAndCompanyId(nonExistingStockLocationId, existingCompanyId));

        assertEquals("Local de estoque não encontrado.", exception.getMessage());
        verify(stockLocationRepository, times(1))
                .findByIdAndCompanyId(eq(nonExistingStockLocationId), eq(existingCompanyId));
    }

    @Test
    @DisplayName("Should insert a new stock location successfully")
    void insert_ShouldReturnStockLocationDTO() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(stockLocationRepository.save(any(StockLocation.class))).thenReturn(stockLocation);

        StockLocationDTO result = stockLocationService.insert(stockLocationDTO, existingCompanyId);

        assertNotNull(result);
        assertEquals(stockLocationDTO.id(), result.id());
        assertEquals(stockLocationDTO.name(), result.name());
        verify(companyRepository, times(1)).findById(eq(existingCompanyId));
        verify(stockLocationRepository, times(1)).save(any(StockLocation.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when company not found during insert")
    void insert_ShouldThrowResourceNotFoundException_WhenCompanyNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                stockLocationService.insert(stockLocationDTO, nonExistingCompanyId));

        assertEquals("Empresa não encontrada.", exception.getMessage());
        verify(companyRepository, times(1)).findById(eq(nonExistingCompanyId));
        verify(stockLocationRepository, never()).save(any(StockLocation.class));
    }

    @Test
    @DisplayName("Should update an existing stock location successfully")
    void update_ShouldReturnStockLocationDTO() {
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(stockLocation));
        when(stockLocationRepository.save(any(StockLocation.class))).thenReturn(stockLocation);

        StockLocationDTO result = stockLocationService.update(existingStockLocationId, stockLocationDTO, existingCompanyId);

        assertNotNull(result);
        assertEquals(stockLocationDTO.id(), result.id());
        assertEquals(stockLocationDTO.name(), result.name());
        verify(stockLocationRepository, times(1))
                .findByIdAndCompanyId(eq(existingStockLocationId), eq(existingCompanyId));
        verify(stockLocationRepository, times(1)).save(any(StockLocation.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when stock location not found during update")
    void update_ShouldThrowResourceNotFoundException_WhenStockLocationNotFound() {
        StockLocationDTO updatedStockLocationDTO = new StockLocationDTO(nonExistingStockLocationId, "Updated Location");

        when(stockLocationRepository.findByIdAndCompanyId(eq(nonExistingStockLocationId), eq(existingCompanyId)))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                stockLocationService.update(nonExistingStockLocationId, updatedStockLocationDTO, existingCompanyId));

        assertEquals("Local de estoque não encontrado.", exception.getMessage());
        verify(stockLocationRepository, times(1))
                .findByIdAndCompanyId(eq(nonExistingStockLocationId), eq(existingCompanyId));
        verify(stockLocationRepository, never()).save(any(StockLocation.class));
    }

    @Test
    @DisplayName("Should delete stock location successfully")
    void deleteByIdAndCompanyId_ShouldDeleteStockLocation() {
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong()))
                .thenReturn(Optional.of(stockLocation));
        when(movementRepository.existsMovementByStockLocationId(anyLong())).thenReturn(false);
        doNothing().when(stockLocationRepository).delete(any(StockLocation.class));

        assertDoesNotThrow(() -> stockLocationService.deleteByIdAndCompanyId(existingStockLocationId, existingCompanyId));

        verify(stockLocationRepository, times(1))
                .findByIdAndCompanyId(eq(existingStockLocationId), eq(existingCompanyId));
        verify(movementRepository, times(1))
                .existsMovementByStockLocationId(eq(existingStockLocationId));
        verify(stockLocationRepository, times(1)).delete(any(StockLocation.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when stock location not found during delete")
    void deleteByIdAndCompanyId_ShouldThrowResourceNotFoundException_WhenStockLocationNotFound() {
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                stockLocationService.deleteByIdAndCompanyId(nonExistingStockLocationId, existingCompanyId));

        assertEquals("Local de estoque não encontrado.", exception.getMessage());
        verify(stockLocationRepository, times(1))
                .findByIdAndCompanyId(eq(nonExistingStockLocationId), eq(existingCompanyId));
        verify(movementRepository, never()).existsMovementByStockLocationId(anyLong());
        verify(stockLocationRepository, never()).delete(any(StockLocation.class));
    }

    @Test
    @DisplayName("Should throw DeletionConflictException when stock location has associated movements")
    void deleteByIdAndCompanyId_ShouldThrowDeletionConflictException_WhenStockLocationHasMovements() {
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong()))
                .thenReturn(Optional.of(stockLocation));
        when(movementRepository.existsMovementByStockLocationId(anyLong())).thenReturn(true);

        DeletionConflictException exception = assertThrows(DeletionConflictException.class, () ->
                stockLocationService.deleteByIdAndCompanyId(existingStockLocationId, existingCompanyId));

        assertEquals("O local de estoque possui movimentações e não pode ser deletado.", exception.getMessage());
        verify(stockLocationRepository, times(1))
                .findByIdAndCompanyId(eq(existingStockLocationId), eq(existingCompanyId));
        verify(movementRepository, times(1)).existsMovementByStockLocationId(eq(existingStockLocationId));
        verify(stockLocationRepository, never()).delete(any(StockLocation.class));
    }
}
