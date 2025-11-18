package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.*;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.exceptions.InvalidMovementTypeException;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.domain.services.StockMovementDomainService;
import com.hextech.estoque_api.infrastructure.repositories.*;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MovementServiceTest {

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private StockLocationRepository stockLocationRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private StockMovementDomainService domainService;
    @Mock
    private MovementRepository repository;
    @InjectMocks
    private MovementService movementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create and process an entry movement successfully")
    void createAndProcessMovementCase1() {
        Long companyId = 1L;
        Long userId = 1L;
        MovementRequestDTO requestDTO = MovementFactory.createMovementRequestDTO();
        Product product = ProductFactory.createProduct(requestDTO.getProductId());

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(companyId)));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(UserFactory.createUser(userId)));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(requestDTO.getStockLocationId())));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(product));
        when(domainService.processMovement(any(), any(), anyString(), any(), any(), any(), any())).thenReturn(MovementFactory.createEntryMovement());
        when(productRepository.save(any())).thenReturn(product);
        when(repository.save(any())).thenReturn(MovementFactory.createEntryMovement());

        MovementResponseDTO responseDTO = movementService.createAndProcessMovement(requestDTO, companyId, userId);

        verify(companyRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(domainService, times(1)).processMovement(any(), any(), anyString(), any(), any(), any(), any());
        verify(productRepository, times(1)).save(any());
        verify(repository, times(1)).save(any());

        assert responseDTO != null;
    }

    @Test
    @DisplayName("Should propagate exception when company not found")
    void createAndProcessMovementCase2() {
        Long companyId = 1L;
        Long userId = 1L;
        MovementRequestDTO requestDTO = MovementFactory.createMovementRequestDTO();

        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            movementService.createAndProcessMovement(requestDTO, companyId, userId);
        });

        verify(companyRepository, times(1)).findById(anyLong());
        verify(userRepository, times(0)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(0)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(0)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(domainService, times(0)).processMovement(any(), any(), anyString(), any(), any(), any(), any());
        verify(productRepository, times(0)).save(any());
        verify(repository, times(0)).save(any());
        assert thrown.getMessage().equals("Empresa não encontrada.");
    }

    @Test
    @DisplayName("Should propagate exception when user not found")
    void createAndProcessMovementCase3() {
        Long companyId = 1L;
        Long userId = 1L;
        MovementRequestDTO requestDTO = MovementFactory.createMovementRequestDTO();

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(1L)));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            movementService.createAndProcessMovement(requestDTO, companyId, userId);
        });

        verify(companyRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(0)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(0)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(domainService, times(0)).processMovement(any(), any(), anyString(), any(), any(), any(), any());
        verify(productRepository, times(0)).save(any());
        verify(repository, times(0)).save(any());
        assert thrown.getMessage().equals("Usuário não encontrado.");
    }

    @Test
    @DisplayName("Should propagate exception when stock location not found")
    void createAndProcessMovementCase4() {
        Long companyId = 1L;
        Long userId = 1L;
        MovementRequestDTO requestDTO = MovementFactory.createMovementRequestDTO();

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(companyId)));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(UserFactory.createUser(userId)));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            movementService.createAndProcessMovement(requestDTO, companyId, userId);
        });
        verify(companyRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(0)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(domainService, times(0)).processMovement(any(), any(), anyString(), any(), any(), any(), any());
        verify(productRepository, times(0)).save(any());
        verify(repository, times(0)).save(any());
        assert thrown.getMessage().equals("Local de estoque não encontrado.");
    }

    @Test
    @DisplayName("Should propagate exception when product not found")
    void createAndProcessMovementCase5() {
        Long companyId = 1L;
        Long userId = 1L;
        MovementRequestDTO requestDTO = MovementFactory.createMovementRequestDTO();

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(companyId)));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(UserFactory.createUser(userId)));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(requestDTO.getStockLocationId())));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            movementService.createAndProcessMovement(requestDTO, companyId, userId);
        });

        verify(companyRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(domainService, times(0)).processMovement(any(), any(), anyString(), any(), any(), any(), any());
        verify(productRepository, times(0)).save(any());
        verify(repository, times(0)).save(any());
        assert thrown.getMessage().equals("Produto não encontrado.");
    }

    @Test
    @DisplayName("Should propagate exception when movement type is invalid")
    void createAndProcessMovementCase6() {
        Long companyId = 1L;
        Long userId = 1L;
        MovementRequestDTO requestDTO = MovementFactory.createMovementRequestDTO();
        requestDTO.setType("INVALID_TYPE");

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(companyId)));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(UserFactory.createUser(userId)));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(requestDTO.getStockLocationId())));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(ProductFactory.createProduct(requestDTO.getProductId())));

        Exception thrown = assertThrows(InvalidMovementTypeException.class, () -> {
            movementService.createAndProcessMovement(requestDTO, companyId, userId);
        });

        verify(companyRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(domainService, times(0)).processMovement(any(), any(), anyString(), any(), any(), any(), any());
        verify(productRepository, times(0)).save(any());
        verify(repository, times(0)).save(any());
        assert thrown.getMessage().equals("Tipo de movimentação inválida.");
    }

    @Test
    @DisplayName("Should propagate exception when domain not process movement")
    void createAndProcessMovementCase7() {
        Long companyId = 1L;
        Long userId = 1L;
        MovementRequestDTO requestDTO = MovementFactory.createMovementRequestDTO();
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(companyId)));
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(UserFactory.createUser(userId)));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(requestDTO.getStockLocationId())));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(ProductFactory.createProduct(requestDTO.getProductId())));
        when(domainService.processMovement(any(), any(), anyString(), any(), any(), any(), any())).thenThrow(new InvalidMovementTypeException("Tipo de movimentação inválida."));

        Exception thrown = assertThrows(InvalidMovementTypeException.class, () -> {
            movementService.createAndProcessMovement(requestDTO, companyId, userId);
        });

        verify(companyRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(domainService, times(1)).processMovement(any(), any(), anyString(), any(), any(), any(), any());
        verify(productRepository, times(0)).save(any());
        verify(repository, times(0)).save(any());
        assert thrown.getMessage().equals("Tipo de movimentação inválida.");
    }
}