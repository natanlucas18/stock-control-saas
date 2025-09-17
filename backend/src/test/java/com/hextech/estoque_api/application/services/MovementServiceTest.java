package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.*;
import com.hextech.estoque_api.domain.entities.MovementType;
import com.hextech.estoque_api.domain.exceptions.InvalidMovementTypeException;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.domain.services.StockMovementDomainService;
import com.hextech.estoque_api.infrastructure.repositories.*;
import com.hextech.estoque_api.infrastructure.security.utils.AuthContext;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
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
    private AuthContext authContext;
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
        MovementRequestDTO requestDTO = MovementFactory.createMovementRequestDTO();

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(1L)));
        when(authContext.getCurrentUserId()).thenReturn(1L);
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(UserFactory.createUser(1L)));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(1L)));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(ProductFactory.createProduct(1L)));
        when(domainService.processMovement(any(), anyInt(), anyString(), any(), any(), any(), any())).thenReturn(MovementFactory.createEntryMovement());
        when(productRepository.save(any())).thenReturn(ProductFactory.createProduct(1L));
        when(repository.save(any())).thenReturn(MovementFactory.createEntryMovement());

        MovementResponseDTO responseDTO = movementService.createAndProcessMovement(requestDTO);

        verify(companyRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(stockLocationRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(productRepository, times(1)).findByIdAndCompanyId(anyLong(), anyLong());
        verify(domainService, times(1)).processMovement(any(), anyInt(), anyString(), any(), any(), any(), any());
        verify(productRepository, times(1)).save(any());
        verify(repository, times(1)).save(any());

        assert responseDTO != null;
    }

    @Test
    @DisplayName("Should propagate exception when company not found")
    void createAndProcessMovementCase2() {
        MovementRequestDTO requestDTO = MovementFactory.createMovementRequestDTO();

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            movementService.createAndProcessMovement(requestDTO);
        });

        assert thrown.getMessage().equals("Empresa não encontrada.");
    }

    @Test
    @DisplayName("Should propagate exception when user not found")
    void createAndProcessMovementCase3() {
        MovementRequestDTO requestDTO = MovementFactory.createMovementRequestDTO();

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(1L)));
        when(authContext.getCurrentUserId()).thenReturn(1L);
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            movementService.createAndProcessMovement(requestDTO);
        });

        assert thrown.getMessage().equals("Usuário não encontrado.");
    }

    @Test
    @DisplayName("Should propagate exception when stock location not found")
    void createAndProcessMovementCase4() {
        MovementRequestDTO requestDTO = MovementFactory.createMovementRequestDTO();

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(1L)));
        when(authContext.getCurrentUserId()).thenReturn(1L);
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(UserFactory.createUser(1L)));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            movementService.createAndProcessMovement(requestDTO);
        });
        assert thrown.getMessage().equals("Local de estoque não encontrado.");
    }

    @Test
    @DisplayName("Should propagate exception when product not found")
    void createAndProcessMovementCase5() {
        MovementRequestDTO requestDTO = MovementFactory.createMovementRequestDTO();

        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(1L)));
        when(authContext.getCurrentUserId()).thenReturn(1L);
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(UserFactory.createUser(1L)));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(1L)));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            movementService.createAndProcessMovement(requestDTO);
        });
        assert thrown.getMessage().equals("Produto não encontrado.");
    }

    @Test
    @DisplayName("Should propagate exception when movement type is invalid")
    void createAndProcessMovementCase6() {
        MovementRequestDTO requestDTO = MovementFactory.createMovementRequestDTO();
        requestDTO.setType("INVALID_TYPE");
        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(1L)));
        when(authContext.getCurrentUserId()).thenReturn(1L);
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(UserFactory.createUser(1L)));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(1L)));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(ProductFactory.createProduct(1L)));

        Exception thrown = assertThrows(InvalidMovementTypeException.class, () -> {
            movementService.createAndProcessMovement(requestDTO);
        });

        assert thrown.getMessage().equals("Tipo de movimentação inválida.");
    }

    @Test
    @DisplayName("Should propagate exception when domain not process movement")
    void createAndProcessMovementCase7() {
        MovementRequestDTO requestDTO = MovementFactory.createMovementRequestDTO();
        when(authContext.getCurrentCompanyId()).thenReturn(1L);
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(1L)));
        when(authContext.getCurrentUserId()).thenReturn(1L);
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(UserFactory.createUser(1L)));
        when(stockLocationRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(StockLocationFactory.createStockLocation(1L)));
        when(productRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(ProductFactory.createProduct(1L)));
        when(domainService.processMovement(any(), anyInt(), anyString(), any(), any(), any(), any())).thenThrow(new InvalidMovementTypeException("Tipo de movimentação inválida."));

        Exception thrown = assertThrows(InvalidMovementTypeException.class, () -> {
            movementService.createAndProcessMovement(requestDTO);
        });

        assert thrown.getMessage().equals("Tipo de movimentação inválida.");
    }

    @Test
    @DisplayName("Should get movements report successfully when data is valid")
    void getMovementsReportCase1() {
        String startDate = "2025-08-25";
        String endDate = "2025-08-30";
        Long companyId = 1L;

        when(repository.searchAllByCompanyIdAndDate(anyLong(), any(), any())).thenReturn(List.of(MovementFactory.createEntryMovement()));

        List<MovementResponseDTO> result = movementService.getMovementsReport(startDate, endDate, companyId);

        verify(repository, times(1)).searchAllByCompanyIdAndDate(anyLong(), any(), any());

        assert !result.isEmpty();
        assert result.get(0).getType().equals(MovementType.ENTRADA.toString());
    }

    @Test
    @DisplayName("Should get movements report successfully when dates are null")
    void getMovementsReportCase2() {
        String startDate = null;
        String endDate = null;
        Long companyId = 1L;

        when(repository.searchAllByCompanyIdAndDate(anyLong(), any(), any())).thenReturn(List.of());
        List<MovementResponseDTO> result = movementService.getMovementsReport(startDate, endDate, companyId);

        verify(repository, times(1)).searchAllByCompanyIdAndDate(anyLong(), any(), any());

        assert result.isEmpty();
    }

    @Test
    @DisplayName("Should propagate exception when dates are invalid")
    void getMovementsReportCase3() {
        String startDate = "invalid-date";
        String endDate = "2025-08-30";
        Long companyId = 1L;

        Exception thrown = assertThrows(IllegalArgumentException.class, () -> {
            movementService.getMovementsReport(startDate, endDate, companyId);
        });

        assert Objects.equals(thrown.getMessage(), "Datas inválidas.");
    }
}