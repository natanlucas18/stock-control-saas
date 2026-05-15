package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.factories.ReportPeriodFactory;
import com.hextech.estoque_api.application.tests.MovementFactory;
import com.hextech.estoque_api.application.tests.ProductFactory;
import com.hextech.estoque_api.domain.entities.ReportPeriod;
import com.hextech.estoque_api.domain.entities.movement.Movement;
import com.hextech.estoque_api.domain.entities.movement.MovementType;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.infrastructure.repositories.MovementRepository;
import com.hextech.estoque_api.infrastructure.utils.PageableUtils;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovementReportServiceTest {

    @Mock
    private MovementRepository repository;

    @InjectMocks
    private MovementReportService service;

    private String startDate;
    private String endDate;
    private String type;
    private String productId;
    private Long companyId;
    private Pageable pageable;
    private ReportPeriod reportPeriod;
    private Product product;
    private Movement movement1;
    private Movement movement2;

    @BeforeEach
    void setUp() {
        startDate = "2023-01-01";
        endDate = "2023-01-31";
        type = "ENTRADA";
        productId = "1";
        companyId = 1L;
        pageable = PageRequest.of(0, 10);
        reportPeriod = new ReportPeriod(LocalDateTime.parse("2023-01-01T00:00:00"),
                LocalDateTime.parse("2023-01-31T23:59:59.999999999"));

        product = ProductFactory.createProduct(1L);
        movement1 = MovementFactory.createEntryMovement();
        movement2 = MovementFactory.createExitMovement();
    }

    @Test
    @DisplayName("Should return page of MovementResponseDTO when all parameters are valid")
    void getMovementsReport_shouldReturnPageOfMovementResponseDTO_whenAllParametersAreValid() {
        try (MockedStatic<ReportPeriodFactory> mockedReportPeriodFactory = mockStatic(ReportPeriodFactory.class);
             MockedStatic<ProductService> mockedProductService = mockStatic(ProductService.class);
             MockedStatic<PageableUtils> mockedPageableUtils = mockStatic(PageableUtils.class)) {

            mockedReportPeriodFactory.when(() -> ReportPeriodFactory.fromString(startDate, endDate))
                    .thenReturn(reportPeriod);
            mockedProductService.when(() -> ProductService.parseProductId(productId))
                    .thenReturn(Long.valueOf(productId));
            mockedPageableUtils.when(() -> PageableUtils.validatePageable(any(Pageable.class), anyList()))
                    .thenReturn(pageable);

            Page<Movement> movementPage = new PageImpl<>(Collections.singletonList(movement1), pageable, 1);
            when(repository.searchAllMovements(
                    any(LocalDateTime.class), any(LocalDateTime.class), any(MovementType.class),
                    anyLong(), anyLong(), any(Pageable.class)))
                    .thenReturn(movementPage);

            Page<MovementResponseDTO> result = service.getMovementsReport(
                    startDate, endDate, type, productId, companyId, pageable);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
            assertEquals(movement1.getId(), result.getContent().get(0).getId());
            verify(repository, times(1)).searchAllMovements(
                    eq(reportPeriod.getStartDate()), eq(reportPeriod.getEndDate()), eq(MovementType.ENTRADA),
                    eq(Long.valueOf(productId)), eq(companyId), eq(pageable));
            mockedReportPeriodFactory.verify(() ->
                    ReportPeriodFactory.fromString(startDate, endDate), times(1));
            mockedProductService.verify(() -> ProductService.parseProductId(productId), times(1));
            mockedPageableUtils.verify(() ->
                    PageableUtils.validatePageable(any(Pageable.class), anyList()), times(1));
        }
    }

    @Test
    @DisplayName("Should handle null or empty type and productId")
    void getMovementsReport_shouldHandleNullOrEmptyTypeAndProductId() {
        try (MockedStatic<ReportPeriodFactory> mockedReportPeriodFactory = mockStatic(ReportPeriodFactory.class);
             MockedStatic<ProductService> mockedProductService = mockStatic(ProductService.class);
             MockedStatic<PageableUtils> mockedPageableUtils = mockStatic(PageableUtils.class)) {

            mockedReportPeriodFactory.when(() -> ReportPeriodFactory.fromString(startDate, endDate))
                    .thenReturn(reportPeriod);
            mockedProductService.when(() -> ProductService.parseProductId(anyString()))
                    .thenReturn(null); // Simulate null productId
            mockedPageableUtils.when(() -> PageableUtils.validatePageable(any(Pageable.class), anyList()))
                    .thenReturn(pageable);

            Page<Movement> movementPage = new PageImpl<>(Arrays.asList(movement1, movement2), pageable, 2);
            when(repository.searchAllMovements(
                    any(LocalDateTime.class), any(LocalDateTime.class), isNull(),
                    isNull(), anyLong(), any(Pageable.class)))
                    .thenReturn(movementPage);

            Page<MovementResponseDTO> result = service
                    .getMovementsReport(startDate, endDate, "", "", companyId, pageable);

            assertNotNull(result);
            assertEquals(2, result.getTotalElements());
            verify(repository, times(1)).searchAllMovements(
                    eq(reportPeriod.getStartDate()), eq(reportPeriod.getEndDate()), isNull(),
                    isNull(), eq(companyId), eq(pageable));
            mockedReportPeriodFactory.verify(() ->
                    ReportPeriodFactory.fromString(startDate, endDate), times(1));
            mockedProductService.verify(() -> ProductService.parseProductId(""), times(1));
            mockedPageableUtils.verify(() ->
                    PageableUtils.validatePageable(any(Pageable.class), anyList()), times(1));
        }
    }

    @Test
    @DisplayName("Should return list of MovementResponseDTO when multiple pages")
    void getMovementsReportToPdf_shouldReturnListOfMovementResponseDTO_whenMultiplePages() {
        try (MockedStatic<ReportPeriodFactory> mockedReportPeriodFactory = mockStatic(ReportPeriodFactory.class);
             MockedStatic<ProductService> mockedProductService = mockStatic(ProductService.class);
             MockedStatic<PageableUtils> mockedPageableUtils = mockStatic(PageableUtils.class)) {

            Pageable firstPageable = PageRequest.of(0, 1);
            Pageable secondPageable = firstPageable.next();

            mockedReportPeriodFactory.when(() -> ReportPeriodFactory.fromString(startDate, endDate))
                    .thenReturn(reportPeriod);
            mockedProductService.when(() -> ProductService.parseProductId(productId))
                    .thenReturn(Long.valueOf(productId));
            mockedPageableUtils.when(() -> PageableUtils.validatePageable(any(Pageable.class), anyList()))
                    .thenReturn(firstPageable);

            when(repository.searchAllMovements(
                    any(LocalDateTime.class), any(LocalDateTime.class), any(MovementType.class),
                    anyLong(), anyLong(), eq(firstPageable)))
                    .thenReturn(new PageImpl<>(Collections.singletonList(movement1), firstPageable, 2));
            when(repository.searchAllMovements(
                    any(LocalDateTime.class), any(LocalDateTime.class), any(MovementType.class),
                    anyLong(), anyLong(), eq(secondPageable)))
                    .thenReturn(new PageImpl<>(Collections.singletonList(movement2), secondPageable, 2));

            List<MovementResponseDTO> result = service
                    .getMovementsReportToPdf(startDate, endDate, type, productId, companyId, firstPageable);

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(movement1.getId(), result.get(0).getId());
            assertEquals(movement2.getId(), result.get(1).getId());
            verify(repository, times(2)).searchAllMovements(
                    eq(reportPeriod.getStartDate()), eq(reportPeriod.getEndDate()), eq(MovementType.ENTRADA),
                    eq(Long.valueOf(productId)), eq(companyId), any(Pageable.class));
            mockedReportPeriodFactory.verify(() ->
                    ReportPeriodFactory.fromString(startDate, endDate), times(1));
            mockedProductService.verify(() -> ProductService.parseProductId(productId), times(1));
            mockedPageableUtils.verify(() ->
                    PageableUtils.validatePageable(any(Pageable.class), anyList()), times(1));
        }
    }

    @Test
    @DisplayName("Should return list of MovementResponseDTO when single page")
    void getMovementsReportToPdf_shouldReturnListOfMovementResponseDTO_whenSinglePage() {
        try (MockedStatic<ReportPeriodFactory> mockedReportPeriodFactory = mockStatic(ReportPeriodFactory.class);
             MockedStatic<ProductService> mockedProductService = mockStatic(ProductService.class);
             MockedStatic<PageableUtils> mockedPageableUtils = mockStatic(PageableUtils.class)) {

            mockedReportPeriodFactory.when(() -> ReportPeriodFactory.fromString(startDate, endDate))
                    .thenReturn(reportPeriod);
            mockedProductService.when(() -> ProductService.parseProductId(productId))
                    .thenReturn(Long.valueOf(productId));
            mockedPageableUtils.when(() -> PageableUtils.validatePageable(any(Pageable.class), anyList()))
                    .thenReturn(pageable);

            Page<Movement> movementPage = new PageImpl<>(Arrays.asList(movement1, movement2), pageable, 2);
            when(repository.searchAllMovements(
                    any(LocalDateTime.class), any(LocalDateTime.class), any(MovementType.class),
                    anyLong(), anyLong(), any(Pageable.class)))
                    .thenReturn(movementPage);

            List<MovementResponseDTO> result = service
                    .getMovementsReportToPdf(startDate, endDate, type, productId, companyId, pageable);

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(movement1.getId(), result.get(0).getId());
            assertEquals(movement2.getId(), result.get(1).getId());
            verify(repository, times(1)).searchAllMovements(
                    eq(reportPeriod.getStartDate()), eq(reportPeriod.getEndDate()), eq(MovementType.ENTRADA),
                    eq(Long.valueOf(productId)), eq(companyId), eq(pageable));
            mockedReportPeriodFactory.verify(() ->
                    ReportPeriodFactory.fromString(startDate, endDate), times(1));
            mockedProductService.verify(() -> ProductService.parseProductId(productId), times(1));
            mockedPageableUtils.verify(() ->
                    PageableUtils.validatePageable(any(Pageable.class), anyList()), times(1));
        }
    }

    @Test
    @DisplayName("Should handle null or empty type and productId")
    void getMovementsReportToPdf_shouldHandleNullOrEmptyTypeAndProductId() {
        try (MockedStatic<ReportPeriodFactory> mockedReportPeriodFactory = mockStatic(ReportPeriodFactory.class);
             MockedStatic<ProductService> mockedProductService = mockStatic(ProductService.class);
             MockedStatic<PageableUtils> mockedPageableUtils = mockStatic(PageableUtils.class)) {

            mockedReportPeriodFactory.when(() -> ReportPeriodFactory.fromString(startDate, endDate))
                    .thenReturn(reportPeriod);
            mockedProductService.when(() -> ProductService.parseProductId(anyString()))
                    .thenReturn(null);
            mockedPageableUtils.when(() -> PageableUtils.validatePageable(any(Pageable.class), anyList()))
                    .thenReturn(pageable);

            Page<Movement> movementPage = new PageImpl<>(Arrays.asList(movement1, movement2), pageable, 2);
            when(repository.searchAllMovements(
                    any(LocalDateTime.class), any(LocalDateTime.class), isNull(),
                    isNull(), anyLong(), any(Pageable.class)))
                    .thenReturn(movementPage);

            List<MovementResponseDTO> result = service
                    .getMovementsReportToPdf(startDate, endDate, "null", "", companyId, pageable);

            assertNotNull(result);
            assertEquals(2, result.size());
            verify(repository, times(1)).searchAllMovements(
                    eq(reportPeriod.getStartDate()), eq(reportPeriod.getEndDate()), isNull(),
                    isNull(), eq(companyId), eq(pageable));
            mockedReportPeriodFactory.verify(() -> ReportPeriodFactory.fromString(startDate, endDate), times(1));
            mockedProductService.verify(() -> ProductService.parseProductId(""), times(1));
            mockedPageableUtils.verify(() -> PageableUtils.validatePageable(any(Pageable.class), anyList()), times(1));
        }
    }
}
