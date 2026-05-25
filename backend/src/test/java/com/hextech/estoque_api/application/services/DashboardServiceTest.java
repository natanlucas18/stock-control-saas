package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.MovementFactory;
import com.hextech.estoque_api.application.tests.ProductFactory;
import com.hextech.estoque_api.domain.entities.movement.Movement;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.infrastructure.repositories.MovementRepository;
import com.hextech.estoque_api.infrastructure.repositories.ProductRepository;
import com.hextech.estoque_api.interfaces.dtos.dashboard.DashboardDTO;
import com.hextech.estoque_api.interfaces.dtos.dashboard.TopMovingProducts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MovementRepository movementRepository;

    @InjectMocks
    private DashboardService dashboardService;

    private Long companyId;
    private Product product1;
    private Product product2;
    private Movement movement1;
    private Movement movement2;
    private TopMovingProducts topMovingProduct1;
    private TopMovingProducts topMovingProduct2;

    @BeforeEach
    void setUp() {
        companyId = 1L;

        product1 = ProductFactory.createProduct(1L);
        product2 = ProductFactory.createProduct(2L);

        movement1 = MovementFactory.createEntryMovement();
        movement2 = MovementFactory.createExitMovement();

        topMovingProduct1 = new TopMovingProducts(product1, 10L);
        topMovingProduct2 = new TopMovingProducts(product2, 8L);
    }

    @Test
    void getDashboard_shouldReturnCorrectDashboardDTO() {
        // Arrange
        when(productRepository.countByCompanyId(anyLong())).thenReturn(2L);
        when(productRepository.countByCompanyIdAndStockStatus(anyLong(), anyString())).thenReturn(1L);
        when(productRepository.findTop5ByStockStatus(anyLong(), anyString(), any(PageRequest.class)))
                .thenReturn(Collections.singletonList(product1));
        when(movementRepository.countByCompanyId(anyLong())).thenReturn(2L);
        when(movementRepository.findTop5ByCompanyIdOrderByMomentDesc(anyLong()))
                .thenReturn(Arrays.asList(movement1, movement2));
        when(movementRepository.findMostMovedProducts(anyLong(), any(PageRequest.class)))
                .thenReturn(Arrays.asList(topMovingProduct1, topMovingProduct2));

        // Act
        DashboardDTO result = dashboardService.getDashboard(companyId);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.totalProducts());
        assertEquals(1L, result.lowStockCount());
        assertEquals(2L, result.totalMovements());
        assertEquals(2, result.recentMovements().size());
        assertEquals(product1.getName(), result.recentMovements().get(0).product().name());
        assertEquals(product1.getName(), result.recentMovements().get(1).product().name());
        assertEquals(2, result.topMovingProducts().size());
        assertEquals(product1.getName(), result.topMovingProducts().get(0).product().name());
        assertEquals(product2.getName(), result.topMovingProducts().get(1).product().name());
        assertEquals(1, result.lowStockProducts().size());
        assertEquals(product1.getName(), result.lowStockProducts().get(0).getName());
        verify(productRepository, times(1)).countByCompanyId(eq(companyId));
        verify(productRepository, times(1))
                .countByCompanyIdAndStockStatus(eq(companyId), eq("LOW"));
        verify(productRepository, times(1))
                .findTop5ByStockStatus(eq(companyId), eq("LOW"), any(Pageable.class));
        verify(movementRepository, times(1)).countByCompanyId(eq(companyId));
        verify(movementRepository, times(1))
                .findTop5ByCompanyIdOrderByMomentDesc(eq(companyId));
        verify(movementRepository, times(1))
                .findMostMovedProducts(eq(companyId), any(PageRequest.class));
    }
}
