package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.ProductFactory;
import com.hextech.estoque_api.domain.exceptions.DeletionConflictException;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.infrastructure.repositories.ProductRepository;
import com.hextech.estoque_api.infrastructure.security.utils.AuthContext;
import com.hextech.estoque_api.interfaces.dtos.products.ProductResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private AuthContext authContext;
    @Mock
    private StockLocationService stockLocationService;
    @Mock
    private ProductRepository repository;
    @InjectMocks
    private ProductService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all products for the current company")
    void findAllByCompanyIdCase1() {

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findAllByCompanyId(anyLong())).thenReturn(List.of(ProductFactory.createProduct(1L)));

        List<ProductResponseDTO> result = service.findAllByCompanyId();

        verify(repository, times(1)).findAllByCompanyId(anyLong());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    @DisplayName("Should return product by id and company id")
    void findByIdAndCompanyIdCase1() {
        Long productId = 1L;

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(ProductFactory.createProduct(productId)));

        ProductResponseDTO result = service.findByIdAndCompanyId(productId);

        verify(repository , times(1)).findByIdAndCompanyId(anyLong(), anyLong());

        assertNotNull(result);
        assertEquals(productId, result.getId());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when product id does not exist")
    void findByIdAndCompanyIdCase2() {
        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.findByIdAndCompanyId(999L);
        });

        assertEquals("Produto não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Should create a new product successfully")
    void insertCase1() {
        Long productId = 1L;

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.save(any())).thenReturn(ProductFactory.createProduct(productId));

        ProductResponseDTO result = service.insert(ProductFactory.createProductRequestDTO());

        verify(repository, times(1)).save(any());

        assertNotNull(result);
        assertEquals(productId, result.getId());
    }

    @Test
    @DisplayName("Should update an existing product successfully")
    void updateCase1() {
        Long productId = 1L;

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(ProductFactory.createProduct(productId)));
        when(repository.save(any())).thenReturn(ProductFactory.createProduct(productId));

        ProductResponseDTO result = service.update(productId, ProductFactory.createProductRequestDTO());

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(1)).save(any());

        assertNotNull(result);
        assertEquals(productId, result.getId());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating a non-existing product")
    void updateCase2() {
        Long productId = 999L;
        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.update(productId, ProductFactory.createProductRequestDTO());
        });

        assertEquals("Produto não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Should delete a product successfully")
    void deleteByIdAndCompanyIdCase1() {
        Long productId = 1L;

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(ProductFactory.createProduct(productId)));
        doNothing().when(repository).delete(any());

        assertDoesNotThrow(() -> service.deleteByIdAndCompanyId(productId));

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting a non-existing product")
    void deleteByIdAndCompanyIdCase2() {
        Long productId = 999L;

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteByIdAndCompanyId(productId);
        });

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());

        assertEquals("Produto não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw DeletionConflictException when deletion violates integrity constraints")
    void deleteByIdAndCompanyIdCase3() {
        Long productId = 1L;

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(ProductFactory.createProduct(productId)));
        doThrow(new DataIntegrityViolationException("Integrity violation")).when(repository).delete(any());

        Exception exception = assertThrows(DeletionConflictException.class, () -> {
            service.deleteByIdAndCompanyId(productId);
        });

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(1)).delete(any());

        assertEquals("Falha na Integridade referencial.", exception.getMessage());
    }
}