package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.*;
import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.movement.Movement;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;
import com.hextech.estoque_api.domain.entities.stockProduct.StockProduct;
import com.hextech.estoque_api.domain.entities.stockProduct.StockProductId;
import com.hextech.estoque_api.domain.entities.user.User;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.domain.services.StockMovementDomainService;
import com.hextech.estoque_api.infrastructure.repositories.*;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovementServiceTest {

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private StockLocationRepository stockLocationRepository;
    @Mock
    private MovementRepository movementRepository;
    @Mock
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private StockProductRepository stockProductRepository;
    @Mock
    private StockMovementDomainService domainService;

    @InjectMocks
    private MovementService movementService;

    private Company company;
    private User user;
    private StockLocation toStockLocation;
    private StockLocation fromStockLocation;
    private Product product;
    private StockProduct toStockProduct;
    private StockProduct fromStockProduct;
    private Movement entryMovement;
    private Movement exitMovement;
    private Movement transferMovement;
    private Movement returnMovement;
    private MovementRequestDTO entryRequestDTO;
    private MovementRequestDTO exitRequestDTO;
    private MovementRequestDTO transferRequestDTO;
    private MovementRequestDTO returnRequestDTO;


    @BeforeEach
    void setUp() {
        company = CompanyFactory.createCompany(1L);
        user = UserFactory.createUser(1L);
        toStockLocation = StockLocationFactory.createStockLocation(1L);
        fromStockLocation = StockLocationFactory.createStockLocation(2L);
        product = ProductFactory.createProduct(1L);

        toStockProduct = new StockProduct();
        toStockProduct.setId(new StockProductId(toStockLocation.getId(), product.getId()));
        toStockProduct.setStockLocation(toStockLocation);
        toStockProduct.setProduct(product);
        toStockProduct.setQuantity(BigDecimal.valueOf(5));

        fromStockProduct = new StockProduct();
        fromStockProduct.setId(new StockProductId(fromStockLocation.getId(), product.getId()));
        fromStockProduct.setStockLocation(fromStockLocation);
        fromStockProduct.setProduct(product);
        fromStockProduct.setQuantity(BigDecimal.valueOf(5));

        entryMovement = MovementFactory.createEntryMovement();
        exitMovement = MovementFactory.createExitMovement();
        transferMovement = MovementFactory.createTransferMovement();
        returnMovement = MovementFactory.createReturnMovement();

        entryRequestDTO = MovementFactory.createMovementRequestDTO("ENTRADA", BigDecimal.valueOf(10),
                "Entrada de teste", product.getId(), null, toStockLocation.getId());
        exitRequestDTO = MovementFactory.createMovementRequestDTO("SAIDA", BigDecimal.valueOf(10),
                "Saida de teste", product.getId(), fromStockLocation.getId(), null);
        transferRequestDTO = MovementFactory.createMovementRequestDTO("TRANSFERENCIA", BigDecimal.valueOf(10),
                "Transferência de teste", product.getId(), fromStockLocation.getId(), toStockLocation.getId());
        returnRequestDTO = MovementFactory.createMovementRequestDTO("DEVOLUCAO", BigDecimal.valueOf(10),
                "Devolução de teste", product.getId(), null, toStockLocation.getId());
    }

    @Test
    void findById_shouldReturnMovementResponseDTO_whenMovementExists() {
        when(movementRepository.findById(anyLong())).thenReturn(Optional.of(entryMovement));

        MovementResponseDTO result = movementService.findById(1L);

        assertNotNull(result);
        assertEquals(entryMovement.getId(), result.getId());
        verify(movementRepository, times(1)).findById(anyLong());
    }

    @Test
    void findById_shouldThrowResourceNotFoundException_whenMovementDoesNotExist() {
        when(movementRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.findById(1L));

        assertEquals("Movimentação não encontrada.", exception.getMessage());
        verify(movementRepository, times(1)).findById(anyLong());
    }

    @Test
    void createEntryMovement_shouldCreateMovementSuccessfully() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(toStockLocation));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(product));
        when(stockProductRepository.findByStockLocationIdAndProductId(anyLong(), anyLong())).thenReturn(Optional.of(toStockProduct));
        when(domainService.processEntryMovement(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(entryMovement);
        when(stockProductRepository.save(any(StockProduct.class))).thenReturn(toStockProduct);
        when(movementRepository.save(any(Movement.class))).thenReturn(entryMovement);

        MovementResponseDTO result = movementService.createEntryMovement(entryRequestDTO, 1L, 1L);

        assertNotNull(result);
        assertEquals(entryMovement.getId(), result.getId());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockProductRepository, times(1)).findByStockLocationIdAndProductId(any(), anyLong());
        verify(domainService, times(1)).processEntryMovement(any(), any(), any(), any(), any(), any(), any(), any());
        verify(productService, times(1)).updateTotalQuantity(anyLong());
        verify(stockProductRepository, times(1)).save(toStockProduct);
        verify(movementRepository, times(1)).save(entryMovement);
    }

    @Test
    void createEntryMovement_shouldThrowResourceNotFoundException_whenCompanyNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createEntryMovement(entryRequestDTO, 1L, 1L));

        assertEquals("Empresa não encontrada.", exception.getMessage());
    }

    @Test
    void createEntryMovement_shouldThrowResourceNotFoundException_whenUserNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createEntryMovement(entryRequestDTO, 1L, 1L));

        assertEquals("Usuário não encontrado.", exception.getMessage());
    }

    @Test
    void createEntryMovement_shouldThrowResourceNotFoundException_whenToStockLocationNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createEntryMovement(entryRequestDTO, 1L, 1L));

        assertEquals("Local de estoque não encontrado.", exception.getMessage());
    }

    @Test
    void createEntryMovement_shouldThrowResourceNotFoundException_whenProductNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(toStockLocation));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createEntryMovement(entryRequestDTO, 1L, 1L));

        assertEquals("Produto não encontrado.", exception.getMessage());
    }

    @Test
    void createEntryMovement_shouldThrowResourceNotFoundException_whenStockProductNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(toStockLocation));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(product));
        when(stockProductRepository.findByStockLocationIdAndProductId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createEntryMovement(entryRequestDTO, company.getId(), user.getId()));

        assertEquals("Produto não encontrado no local de estoque.", exception.getMessage());
    }

    @Test
    void createExitMovement_shouldCreateMovementSuccessfully() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(fromStockLocation));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(product));
        when(stockProductRepository.findByStockLocationIdAndProductId(anyLong(), anyLong())).thenReturn(Optional.of(fromStockProduct));
        when(domainService.processExitMovement(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(exitMovement);
        when(stockProductRepository.save(any(StockProduct.class))).thenReturn(fromStockProduct);
        when(movementRepository.save(any(Movement.class))).thenReturn(exitMovement);

        MovementResponseDTO result = movementService.createExitMovement(exitRequestDTO, company.getId(), user.getId());

        assertNotNull(result);
        assertEquals(exitMovement.getId(), result.getId());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockProductRepository, times(1)).findByStockLocationIdAndProductId(anyLong(), anyLong());
        verify(domainService, times(1)).processExitMovement(any(), any(), any(), any(), any(), any(), any(), any());
        verify(productService, times(1)).updateTotalQuantity(anyLong());
        verify(stockProductRepository, times(1)).save(fromStockProduct);
        verify(movementRepository, times(1)).save(exitMovement);
    }

    @Test
    void createExitMovement_shouldThrowResourceNotFoundException_whenFromStockLocationNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createExitMovement(exitRequestDTO, 1L, 1L));

        assertEquals("Local de estoque não encontrado.", exception.getMessage());
    }

    @Test
    void createExitMovement_shouldThrowResourceNotFoundException_whenProductNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(fromStockLocation));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createExitMovement(exitRequestDTO, 1L, 1L));

        assertEquals("Produto não encontrado.", exception.getMessage());
    }

    @Test
    void createExitMovement_shouldThrowResourceNotFoundException_whenStockProductNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(fromStockLocation));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(product));
        when(stockProductRepository.findByStockLocationIdAndProductId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createExitMovement(exitRequestDTO, 1L, 1L));

        assertEquals("Produto não encontrado no local de estoque.", exception.getMessage());
    }

    @Test
    void createTransferMovement_shouldCreateMovementSuccessfully() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(eq(transferRequestDTO.getFromStockLocationId()), anyLong())).thenReturn(Optional.of(fromStockLocation));
        when(stockLocationRepository.findByIdAndCompanyId(eq(transferRequestDTO.getToStockLocationId()), anyLong())).thenReturn(Optional.of(toStockLocation));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(product));
        when(stockProductRepository.findByStockLocationIdAndProductId(eq(transferRequestDTO.getFromStockLocationId()), anyLong())).thenReturn(Optional.of(fromStockProduct));
        when(stockProductRepository.findByStockLocationIdAndProductId(eq(transferRequestDTO.getToStockLocationId()), anyLong())).thenReturn(Optional.of(toStockProduct));
        when(domainService.processTransferMovement(any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(transferMovement);
        when(stockProductRepository.save(any(StockProduct.class))).thenReturn(this.toStockProduct);
        when(movementRepository.save(any(Movement.class))).thenReturn(entryMovement);

        MovementResponseDTO result = movementService.createTransferMovement(transferRequestDTO, 1L, 1L);

        assertNotNull(result);
        assertEquals(entryMovement.getId(), result.getId());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(2)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockProductRepository, times(2)).findByStockLocationIdAndProductId(anyLong(), anyLong());
        verify(domainService, times(1)).processTransferMovement(any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
        verify(productService, times(1)).updateTotalQuantity(anyLong());
        verify(stockProductRepository, times(2)).save(any(StockProduct.class)); // Both fromStockProduct and toStockProduct
        verify(movementRepository, times(1)).save(entryMovement);
    }

    @Test
    void createTransferMovement_shouldThrowResourceNotFoundException_whenFromStockLocationNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(eq(transferRequestDTO.getFromStockLocationId()), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createTransferMovement(transferRequestDTO, 1L, 1L));

        assertEquals("Local de estoque não encontrado.", exception.getMessage());
    }

    @Test
    void createTransferMovement_shouldThrowResourceNotFoundException_whenToStockLocationNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(eq(transferRequestDTO.getFromStockLocationId()), anyLong())).thenReturn(Optional.of(fromStockLocation));
        when(stockLocationRepository.findByIdAndCompanyId(eq(transferRequestDTO.getToStockLocationId()), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createTransferMovement(transferRequestDTO, 1L, 1L));

        assertEquals("Local de estoque não encontrado.", exception.getMessage());
    }

    @Test
    void createTransferMovement_shouldThrowResourceNotFoundException_whenProductNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(eq(transferRequestDTO.getFromStockLocationId()), anyLong())).thenReturn(Optional.of(fromStockLocation));
        when(stockLocationRepository.findByIdAndCompanyId(eq(transferRequestDTO.getToStockLocationId()), anyLong())).thenReturn(Optional.of(toStockLocation));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createTransferMovement(transferRequestDTO, 1L, 1L));

        assertEquals("Produto não encontrado.", exception.getMessage());
    }

    @Test
    void createTransferMovement_shouldThrowResourceNotFoundException_whenFromStockProductNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(eq(transferRequestDTO.getFromStockLocationId()), anyLong())).thenReturn(Optional.of(fromStockLocation));
        when(stockLocationRepository.findByIdAndCompanyId(eq(transferRequestDTO.getToStockLocationId()), anyLong())).thenReturn(Optional.of(toStockLocation));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(product));
        when(stockProductRepository.findByStockLocationIdAndProductId(eq(transferRequestDTO.getFromStockLocationId()), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createTransferMovement(transferRequestDTO, 1L, 1L));

        assertEquals("Produto não encontrado no local de estoque.", exception.getMessage());
    }

    @Test
    void createTransferMovement_shouldThrowResourceNotFoundException_whenToStockProductNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(eq(transferRequestDTO.getFromStockLocationId()), anyLong())).thenReturn(Optional.of(fromStockLocation));
        when(stockLocationRepository.findByIdAndCompanyId(eq(transferRequestDTO.getToStockLocationId()), anyLong())).thenReturn(Optional.of(toStockLocation));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(product));
        when(stockProductRepository.findByStockLocationIdAndProductId(eq(transferRequestDTO.getFromStockLocationId()), anyLong())).thenReturn(Optional.of(fromStockProduct));
        when(stockProductRepository.findByStockLocationIdAndProductId(eq(transferRequestDTO.getToStockLocationId()), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createTransferMovement(transferRequestDTO, 1L, 1L));

        assertEquals("Produto não encontrado no local de estoque.", exception.getMessage());
    }

    @Test
    void createReturnMovement_shouldCreateMovementSuccessfully() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(toStockLocation));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(product));
        when(stockProductRepository.findByStockLocationIdAndProductId(anyLong(), anyLong())).thenReturn(Optional.of(toStockProduct));
        when(domainService.processReturnMovement(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(returnMovement);
        when(stockProductRepository.save(any(StockProduct.class))).thenReturn(toStockProduct);
        when(movementRepository.save(any(Movement.class))).thenReturn(returnMovement);

        MovementResponseDTO result = movementService.createReturnMovement(returnRequestDTO, 1L, 1L);

        assertNotNull(result);
        assertEquals(entryMovement.getId(), result.getId());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockProductRepository, times(1)).findByStockLocationIdAndProductId(anyLong(), anyLong());
        verify(domainService, times(1)).processReturnMovement(any(), any(), any(), any(), any(), any(), any(), any());
        verify(productService, times(1)).updateTotalQuantity(anyLong());
        verify(stockProductRepository, times(1)).save(toStockProduct);
        verify(movementRepository, times(1)).save(returnMovement);
    }

    @Test
    void createReturnMovement_shouldThrowResourceNotFoundException_whenCompanyNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createReturnMovement(returnRequestDTO, 1L, 1L));

        assertEquals("Empresa não encontrada.", exception.getMessage());
    }

    @Test
    void createReturnMovement_shouldThrowResourceNotFoundException_whenUserNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createReturnMovement(returnRequestDTO, 1L, 1L));

        assertEquals("Usuário não encontrado.", exception.getMessage());
    }

    @Test
    void createReturnMovement_shouldThrowResourceNotFoundException_whenToStockLocationNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createReturnMovement(returnRequestDTO, 1L, 1L));

        assertEquals("Local de estoque não encontrado.", exception.getMessage());
    }

    @Test
    void createReturnMovement_shouldThrowResourceNotFoundException_whenProductNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(toStockLocation));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createReturnMovement(returnRequestDTO, 1L, 1L));

        assertEquals("Produto não encontrado.", exception.getMessage());
    }

    @Test
    void createReturnMovement_shouldThrowResourceNotFoundException_whenStockProductNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(toStockLocation));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(product));
        when(stockProductRepository.findByStockLocationIdAndProductId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                movementService.createReturnMovement(returnRequestDTO, 1L, 1L));

        assertEquals("Produto não encontrado no local de estoque.", exception.getMessage());
    }
}
