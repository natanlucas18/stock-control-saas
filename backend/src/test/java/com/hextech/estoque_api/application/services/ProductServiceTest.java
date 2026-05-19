package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.CompanyFactory;
import com.hextech.estoque_api.application.tests.ProductFactory;
import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.unitMeasure.UnitMeasure;
import com.hextech.estoque_api.domain.exceptions.BusinessException;
import com.hextech.estoque_api.domain.exceptions.ProductCodeAlreadyExistsException;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.infrastructure.repositories.*;
import com.hextech.estoque_api.interfaces.dtos.products.ProductRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.products.ProductResponseDTO;
import com.hextech.estoque_api.interfaces.dtos.products.ProductResumeDTO;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private MovementRepository movementRepository;
    @Mock
    private StockLocationRepository stockLocationRepository;
    @Mock
    private UnitMeasureRepository unitMeasureRepository;
    @Mock
    private StockProductRepository stockProductRepository;

    private Long existingProductId;
    private Long nonExistingProductId;
    private Long existingCompanyId;
    private Long nonExistingCompanyId;
    private Long existingUnitMeasureId;
    private Long nonExistingUnitMeasureId;
    private Product product;
    private Company company;
    private UnitMeasure unitMeasure;
    private ProductRequestDTO productRequestDTO;

    @BeforeEach
    void setUp() {
        existingProductId = 1L;
        nonExistingProductId = 99L;
        existingCompanyId = 10L;
        nonExistingCompanyId = 999L;
        existingUnitMeasureId = 100L;
        nonExistingUnitMeasureId = 9999L;

        company = CompanyFactory.createCompany(existingCompanyId);

        unitMeasure = new UnitMeasure();
        unitMeasure.setId(existingUnitMeasureId);
        unitMeasure.setName("UNIDADE");
        unitMeasure.setAcronym("UN");

        product = ProductFactory.createProduct(existingProductId);

        productRequestDTO = ProductFactory.createProductRequestDTO();
    }

    @Test
    @DisplayName("Should find all products by company ID successfully")
    void findAllByCompanyId_ShouldReturnPageOfProductResumeDTO() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(List.of(product), pageable, 1);

        when(productRepository.findAllByNameAndCompanyId(anyString(), anyLong(), any(Pageable.class)))
                .thenReturn(productPage);

        Page<ProductResumeDTO> result = productService.findAllByCompanyId("", existingCompanyId, pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals(product.getName(), result.getContent().get(0).getName());
        verify(productRepository, times(1)).findAllByNameAndCompanyId(anyString(), anyLong(), any(Pageable.class));
    }

    @Test
    @DisplayName("Should find product by ID and company ID successfully")
    void findByIdAndCompanyId_ShouldReturnProductResponseDTO() {
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong()))
                .thenReturn(Optional.of(product));

        ProductResponseDTO result = productService.findByIdAndCompanyId(existingProductId, existingCompanyId);

        assertNotNull(result);
        assertEquals(product.getName(), result.getName());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when product not found by ID and company ID")
    void findByIdAndCompanyId_ShouldThrowResourceNotFoundException_WhenProductNotFound() {
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                productService.findByIdAndCompanyId(nonExistingProductId, existingCompanyId));

        assertEquals("Produto não encontrado.", exception.getMessage());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("Should insert a new product successfully")
    void insert_ShouldReturnProductResponseDTO() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(unitMeasureRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(unitMeasure));
        when(productRepository.existsByCodeAndCompanyId(anyString(), anyLong())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO result = productService.insert(productRequestDTO, existingCompanyId);

        assertNotNull(result);
        assertEquals(product.getName(), result.getName());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(unitMeasureRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(1)).existsByCodeAndCompanyId(anyString(), anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when company not found during insert")
    void insert_ShouldThrowResourceNotFoundException_WhenCompanyNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                productService.insert(productRequestDTO, nonExistingCompanyId));

        assertEquals("Empresa não encontrada.", exception.getMessage());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(unitMeasureRepository, never()).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, never()).existsByCodeAndCompanyId(anyString(), anyLong());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when unit measure not found during insert")
    void insert_ShouldThrowResourceNotFoundException_WhenUnitMeasureNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(unitMeasureRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        productRequestDTO.setUnitMeasureId(nonExistingUnitMeasureId);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                productService.insert(productRequestDTO, existingCompanyId));

        assertEquals("Unidade de medida não encontrada.", exception.getMessage());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(unitMeasureRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, never()).existsByCodeAndCompanyId(anyString(), anyLong());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw ProductCodeAlreadyExistsException when product code already exists during insert")
    void insert_ShouldThrowProductCodeAlreadyExistsException_WhenCodeExists() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(unitMeasureRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(unitMeasure));
        when(productRepository.existsByCodeAndCompanyId(anyString(), anyLong())).thenReturn(true);

        ProductCodeAlreadyExistsException exception = assertThrows(ProductCodeAlreadyExistsException.class, () ->
                productService.insert(productRequestDTO, existingCompanyId));

        assertEquals("Código de produto já existente.", exception.getMessage());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(unitMeasureRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(1)).existsByCodeAndCompanyId(anyString(), anyLong());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should update an existing product successfully")
    void update_ShouldReturnProductResponseDTO() {
        productRequestDTO.setCode("NEWCODE");
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong()))
                .thenReturn(Optional.of(product));
        when(unitMeasureRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(unitMeasure));
        when(productRepository.existsByCodeAndCompanyId(anyString(), anyLong())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO result = productService.update(existingProductId, productRequestDTO, existingCompanyId);

        assertNotNull(result);
        assertEquals(productRequestDTO.getName(), result.getName());
        assertEquals(productRequestDTO.getCode(), result.getCode());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(unitMeasureRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(1)).existsByCodeAndCompanyId(anyString(), anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when product not found during update")
    void update_ShouldThrowResourceNotFoundException_WhenProductNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                productService.update(nonExistingProductId, productRequestDTO, existingCompanyId));

        assertEquals("Produto não encontrado.", exception.getMessage());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(unitMeasureRepository, never()).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, never()).existsByCodeAndCompanyId(anyString(), anyLong());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when product is disabled during update")
    void update_ShouldThrowBusinessException_WhenProductIsDisabled() {
        product.setIsEnable(false);
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong()))
                .thenReturn(Optional.of(product));

        BusinessException exception = assertThrows(BusinessException.class, () ->
                productService.update(existingProductId, productRequestDTO, existingCompanyId));

        assertEquals("O produto está desabilitado e não pode ser modificado.", exception.getMessage());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(unitMeasureRepository, never()).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, never()).existsByCodeAndCompanyId(anyString(), anyLong());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw ProductCodeAlreadyExistsException when new product code already exists during update")
    void update_ShouldThrowProductCodeAlreadyExistsException_WhenNewCodeExists() {
        product.setCode("NEWCODE");
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong()))
                .thenReturn(Optional.of(product));
        when(productRepository.existsByCodeAndCompanyId(anyString(), anyLong())).thenReturn(true);

        ProductCodeAlreadyExistsException exception = assertThrows(ProductCodeAlreadyExistsException.class, () ->
                productService.update(existingProductId, productRequestDTO, existingCompanyId));

        assertEquals("Código de produto já existente.", exception.getMessage());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(1)).existsByCodeAndCompanyId(anyString(), anyLong());
        verify(unitMeasureRepository, never()).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when unit measure changes and product has movements")
    void update_ShouldThrowBusinessException_WhenUnitMeasureChangesAndProductHasMovements() {
        unitMeasure.setName("UNIDADE2");
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong()))
                .thenReturn(Optional.of(product));
        when(unitMeasureRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(unitMeasure));
        when(movementRepository.existsMovementByProductId(anyLong())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () ->
                productService.update(existingProductId, productRequestDTO, existingCompanyId));

        assertEquals("Não é permitido alterar a U.M., produto possui movimentações.", exception.getMessage());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(unitMeasureRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(movementRepository, times(1)).existsMovementByProductId(anyLong());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should delete product successfully when no movements exist")
    void deleteByIdAndCompanyId_ShouldDeleteProduct_WhenNoMovements() {
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong()))
                .thenReturn(Optional.of(product));
        when(movementRepository.existsMovementByProductId(anyLong())).thenReturn(false);
        doNothing().when(productRepository).delete(any(Product.class));

        productService.deleteByIdAndCompanyId(existingProductId, existingCompanyId);

        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(movementRepository, times(1)).existsMovementByProductId(anyLong());
        verify(productRepository, times(1)).delete(any(Product.class));
        verify(productRepository, never()).save(any(Product.class)); // Should not save if deleted
    }

    @Test
    @DisplayName("Should disable product successfully when movements exist")
    void deleteByIdAndCompanyId_ShouldDisableProduct_WhenMovementsExist() {
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(product));
        when(movementRepository.existsMovementByProductId(anyLong())).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(product); // Mock save for disabling

        productService.deleteByIdAndCompanyId(existingProductId, existingCompanyId);

        assertFalse(product.getIsEnable()); // Verify product was disabled
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(movementRepository, times(1)).existsMovementByProductId(anyLong());
        verify(productRepository, times(1)).save(any(Product.class)); // Should save to disable
        verify(productRepository, never()).delete(any(Product.class)); // Should not delete
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when product not found during delete")
    void deleteByIdAndCompanyId_ShouldThrowResourceNotFoundException_WhenProductNotFound() {
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                productService.deleteByIdAndCompanyId(nonExistingProductId, existingCompanyId));

        assertEquals("Produto não encontrado.", exception.getMessage());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(movementRepository, never()).existsMovementByProductId(anyLong());
        verify(productRepository, never()).delete(any(Product.class));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when product is disabled during delete")
    void deleteByIdAndCompanyId_ShouldThrowBusinessException_WhenProductIsDisabled() {
        product.setIsEnable(false); // Disable the product
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong()))
                .thenReturn(Optional.of(product));

        BusinessException exception = assertThrows(BusinessException.class, () ->
                productService.deleteByIdAndCompanyId(existingProductId, existingCompanyId));

        assertEquals("O produto está desabilitado e não pode ser deletado.", exception.getMessage());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(movementRepository, never()).existsMovementByProductId(anyLong());
        verify(productRepository, never()).delete(any(Product.class));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should check product code successfully when code does not exist")
    void checkProductCode_ShouldNotThrowException_WhenCodeDoesNotExist() {
        when(productRepository.existsByCodeAndCompanyId(anyString(), anyLong())).thenReturn(false);

        assertDoesNotThrow(() -> productService.checkProductCode("NEWCODE", existingCompanyId));

        verify(productRepository, times(1)).existsByCodeAndCompanyId(anyString(), anyLong());
    }

    @Test
    @DisplayName("Should throw ProductCodeAlreadyExistsException when product code already exists during check")
    void checkProductCode_ShouldThrowProductCodeAlreadyExistsException_WhenCodeExists() {
        when(productRepository.existsByCodeAndCompanyId(anyString(), anyLong())).thenReturn(true);

        ProductCodeAlreadyExistsException exception = assertThrows(ProductCodeAlreadyExistsException.class, () ->
                productService.checkProductCode("EXISTINGCODE", existingCompanyId));

        assertEquals("Código de produto já existente.", exception.getMessage());
        verify(productRepository, times(1)).existsByCodeAndCompanyId(anyString(), anyLong());
    }

    @Test
    @DisplayName("Should update total quantity successfully")
    void updateTotalQuantity_ShouldCallRepositoryMethod() {
        BigDecimal totalQuantity = BigDecimal.valueOf(500.00);
        when(stockProductRepository.sumByProductId(anyLong())).thenReturn(totalQuantity);
        doNothing().when(productRepository).updateTotalQuantity(anyLong(), any(BigDecimal.class));

        productService.updateTotalQuantity(existingProductId);

        verify(stockProductRepository, times(1)).sumByProductId(anyLong());
        verify(productRepository, times(1)).updateTotalQuantity(anyLong(), any(BigDecimal.class));
    }

    @Test
    @DisplayName("Should parse product ID string to Long correctly")
    void parseProductId_ShouldReturnCorrectLong() {
        String productId = "123";

        Long result = ProductService.parseProductId(productId);

        assertEquals(123L, result);
        assertNull(ProductService.parseProductId(""));
        assertNull(ProductService.parseProductId("null"));
        assertNull(ProductService.parseProductId(null));
    }

    @Test
    @DisplayName("Should throw NumberFormatException for invalid product ID string")
    void parseProductId_ShouldThrowNumberFormatExceptionForInvalidString() {
        assertThrows(NumberFormatException.class, () -> ProductService.parseProductId("abc"));
    }
}
