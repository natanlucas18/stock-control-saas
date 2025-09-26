package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.CompanyFactory;
import com.hextech.estoque_api.application.tests.ProductFactory;
import com.hextech.estoque_api.application.tests.StockLocationFactory;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.exceptions.DeletionConflictException;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.infrastructure.repositories.CompanyRepository;
import com.hextech.estoque_api.infrastructure.repositories.ProductRepository;
import com.hextech.estoque_api.infrastructure.repositories.StockLocationRepository;
import com.hextech.estoque_api.interfaces.dtos.products.ProductResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private StockLocationRepository stockLocationRepository;
    @Mock
    private ProductRepository repository;
    @InjectMocks
    private ProductService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all products paged for the current company")
    void findAllByCompanyIdPagedCase1() {
        Product product = ProductFactory.createProduct(1L);
        String productName = product.getName();
        Long companyId = 1L;
        long totalElements = 1;

        Pageable pageable = PageRequest.of(0, 8);
        Page<Product> page = new PageImpl<>(List.of(product), pageable, totalElements);

        when(repository.findAllByNameAndCompanyId(any(), anyLong(), any())).thenReturn(page);

        Page<ProductResponseDTO> result = service.findAllByCompanyId(productName, companyId, pageable);

        verify(repository, times(1)).findAllByNameAndCompanyId(any(), anyLong(), any());
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
    }

    @Test
    @DisplayName("Should return product by id and company id")
    void findByIdAndCompanyIdCase1() {
        Long productId = 1L;
        Long companyId = 1L;

        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(ProductFactory.createProduct(productId)));

        ProductResponseDTO result = service.findByIdAndCompanyId(productId, companyId);

        verify(repository , times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when product id does not exist")
    void findByIdAndCompanyIdCase2() {
        Long companyId = 1L;

        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.findByIdAndCompanyId(999L, companyId);
        });

        verify(repository , times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        assertEquals("Produto não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Should create a new product successfully")
    void insertCase1() {
        Long productId = 1L;
        Long companyId = 1L;
        Long stockLocationId = 1L;

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(companyId)));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(stockLocationId)));
        when(repository.save(any())).thenReturn(ProductFactory.createProduct(productId));

        ProductResponseDTO result = service.insert(ProductFactory.createProductRequestDTO(), companyId);

        verify(companyRepository, times(1)).findById(anyLong());
        verify(stockLocationRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(1)).save(any());
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when company does not exist")
    void insertCase2() {
        Long companyId = 999L;

        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.insert(ProductFactory.createProductRequestDTO(), companyId);
        });

        verify(companyRepository, times(1)).findById(anyLong());
        verify(stockLocationRepository, times(0)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(0)).save(any());

        assertEquals("Empresa não encontrada.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when stock location does not exist")
    void insertCase3() {
        Long companyId = 1L;

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(companyId)));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.insert(ProductFactory.createProductRequestDTO(), companyId);
        });

        verify(companyRepository, times(1)).findById(anyLong());
        verify(stockLocationRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(0)).save(any());
        assertEquals("Local de estoque não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Should update an existing product successfully")
    void updateCase1() {
        Long productId = 1L;
        Long companyId = 1L;
        Long stockLocationId = 1L;

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(companyId)));
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(ProductFactory.createProduct(productId)));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(stockLocationId)));
        when(repository.save(any())).thenReturn(ProductFactory.createProduct(productId));

        ProductResponseDTO result = service.update(productId, ProductFactory.createProductRequestDTO(), companyId);

        verify(companyRepository, times(1)).findById(anyLong());
        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(1)).save(any());
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when company does not exist")
    void updateCase2() {
        Long productId = 1L;
        Long companyId = 999L;

        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.update(productId, ProductFactory.createProductRequestDTO(), companyId);
        });

        verify(companyRepository, times(1)).findById(anyLong());
        verify(repository, times(0)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(0)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(0)).save(any());
        assertEquals("Empresa não encontrada.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when product does not exist")
    void updateCase3() {
        Long productId = 999L;
        Long companyId = 1L;

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(companyId)));
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.update(productId, ProductFactory.createProductRequestDTO(), companyId);
        });

        verify(companyRepository, times(1)).findById(anyLong());
        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(0)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(0)).save(any());
        assertEquals("Produto não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when stock location does not exist")
    void updateCase4() {
        Long productId = 1L;
        Long companyId = 1L;

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(companyId)));
        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(ProductFactory.createProduct(productId)));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.update(productId, ProductFactory.createProductRequestDTO(), companyId);
        });

        verify(companyRepository, times(1)).findById(anyLong());
        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(0)).save(any());
        assertEquals("Local de estoque não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Should delete a product successfully")
    void deleteByIdAndCompanyIdCase1() {
        Long productId = 1L;
        Long companyId = 1L;

        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(ProductFactory.createProduct(productId)));
        doNothing().when(repository).delete(any());

        assertDoesNotThrow(() -> service.deleteByIdAndCompanyId(productId, companyId));

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting a non-existing product")
    void deleteByIdAndCompanyIdCase2() {
        Long productId = 999L;
        Long companyId = 1L;

        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteByIdAndCompanyId(productId, companyId);
        });

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        assertEquals("Produto não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw DeletionConflictException when deletion violates integrity constraints")
    void deleteByIdAndCompanyIdCase3() {
        Long productId = 1L;
        Long companyId = 1L;

        when(repository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(ProductFactory.createProduct(productId)));
        doThrow(new DataIntegrityViolationException("Integrity violation")).when(repository).delete(any());

        Exception exception = assertThrows(DeletionConflictException.class, () -> {
            service.deleteByIdAndCompanyId(productId, companyId);
        });

        verify(repository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(repository, times(1)).delete(any());
        assertEquals("Falha na Integridade referencial.", exception.getMessage());
    }
}