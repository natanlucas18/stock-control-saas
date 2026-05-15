package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.ProductFactory;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.product.StockStatus;
import com.hextech.estoque_api.infrastructure.repositories.ProductRepository;
import com.hextech.estoque_api.infrastructure.utils.PageableUtils;
import com.hextech.estoque_api.interfaces.dtos.products.ProductReportDTO;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductReportServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductReportService service;

    private Product product1;
    private Product product2;
    private Pageable pageable;
    private Long companyId;

    @BeforeEach
    void setUp() {
        product1 = ProductFactory.createProduct(1L);
        product2 = ProductFactory.createProduct(2L);
        pageable = PageRequest.of(0, 10);
        companyId = 1L;
    }

    @Test
    @DisplayName("Should return page of ProductReportDTO when status is valid")
    void getProductsReport_shouldReturnPageOfProductReportDTO_whenStatusIsValid() {
        try (MockedStatic<PageableUtils> mockedPageableUtils = mockStatic(PageableUtils.class)) {
            mockedPageableUtils.when(() -> PageableUtils.validatePageable(any(Pageable.class), anyList()))
                    .thenReturn(pageable);

            Page<Product> productPage = new PageImpl<>(Collections.singletonList(product1), pageable, 1);
            when(repository.searchProductsReport(anyString(), anyLong(), any(Pageable.class)))
                    .thenReturn(productPage);

            Page<ProductReportDTO> result = service.getProductsReport(StockStatus.NORMAL.name(), companyId, pageable);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
            assertEquals(product1.getName(), result.getContent().get(0).getName());
            verify(repository, times(1))
                    .searchProductsReport(eq(StockStatus.NORMAL.name()), eq(companyId), eq(pageable));
            mockedPageableUtils.verify(() ->
                    PageableUtils.validatePageable(any(Pageable.class), anyList()), times(1));
        }
    }

    @Test
    @DisplayName("Should return page of ProductReportDTO when status is null")
    void getProductsReport_shouldReturnPageOfProductReportDTO_whenStatusIsNull() {
        try (MockedStatic<PageableUtils> mockedPageableUtils = mockStatic(PageableUtils.class)) {
            mockedPageableUtils.when(() -> PageableUtils.validatePageable(any(Pageable.class), anyList()))
                    .thenReturn(pageable);

            Page<Product> productPage = new PageImpl<>(Arrays.asList(product1, product2), pageable, 2);
            when(repository.searchProductsReport(isNull(), anyLong(), any(Pageable.class)))
                    .thenReturn(productPage);

            Page<ProductReportDTO> result = service.getProductsReport(null, companyId, pageable);

            assertNotNull(result);
            assertEquals(2, result.getTotalElements());
            assertEquals(product1.getName(), result.getContent().get(0).getName());
            assertEquals(product2.getName(), result.getContent().get(1).getName());
            verify(repository, times(1))
                    .searchProductsReport(isNull(), eq(companyId), eq(pageable));
            mockedPageableUtils.verify(() ->
                    PageableUtils.validatePageable(any(Pageable.class), anyList()), times(1));
        }
    }

    @Test
    @DisplayName("Should return page of ProductReportDTO when status is blank")
    void getProductsReport_shouldReturnPageOfProductReportDTO_whenStatusIsBlank() {
        try (MockedStatic<PageableUtils> mockedPageableUtils = mockStatic(PageableUtils.class)) {
            mockedPageableUtils.when(() -> PageableUtils.validatePageable(any(Pageable.class), anyList()))
                    .thenReturn(pageable);

            Page<Product> productPage = new PageImpl<>(Arrays.asList(product1, product2), pageable, 2);
            when(repository.searchProductsReport(isNull(), anyLong(), any(Pageable.class)))
                    .thenReturn(productPage);

            Page<ProductReportDTO> result = service.getProductsReport("   ", companyId, pageable);

            assertNotNull(result);
            assertEquals(2, result.getTotalElements());
            verify(repository, times(1))
                    .searchProductsReport(isNull(), eq(companyId), eq(pageable));
            mockedPageableUtils.verify(() ->
                    PageableUtils.validatePageable(any(Pageable.class), anyList()), times(1));
        }
    }

    @Test
    @DisplayName("Should return list of ProductReportDTO when multiple pages")
    void getProductsReportToPdf_shouldReturnListOfProductReportDTO_whenMultiplePages() {
        try (MockedStatic<PageableUtils> mockedPageableUtils = mockStatic(PageableUtils.class)) {
            Pageable firstPageable = PageRequest.of(0, 1);
            Pageable secondPageable = firstPageable.next();
            mockedPageableUtils.when(() -> PageableUtils.validatePageable(any(Pageable.class), anyList()))
                    .thenReturn(firstPageable);

            when(repository.searchProductsReport(isNull(), anyLong(), eq(firstPageable)))
                    .thenReturn(new PageImpl<>(Collections.singletonList(product1), firstPageable, 2));
            when(repository.searchProductsReport(isNull(), anyLong(), eq(secondPageable)))
                    .thenReturn(new PageImpl<>(Collections.singletonList(product2), secondPageable, 2));

            List<ProductReportDTO> result = service.getProductsReportToPdf(null, companyId, firstPageable);

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(product1.getName(), result.get(0).getName());
            assertEquals(product2.getName(), result.get(1).getName());
            verify(repository, times(2))
                    .searchProductsReport(isNull(), eq(companyId), any(Pageable.class));
            mockedPageableUtils.verify(() ->
                    PageableUtils.validatePageable(any(Pageable.class), anyList()), times(1));
        }
    }

    @Test
    @DisplayName("Should return list of ProductReportDTO when single page")
    void getProductsReportToPdf_shouldReturnListOfProductReportDTO_whenSinglePage() {
        try (MockedStatic<PageableUtils> mockedPageableUtils = mockStatic(PageableUtils.class)) {
            mockedPageableUtils.when(() -> PageableUtils.validatePageable(any(Pageable.class), anyList()))
                    .thenReturn(pageable);

            Page<Product> productPage = new PageImpl<>(Arrays.asList(product1, product2), pageable, 2);
            when(repository.searchProductsReport(isNull(), anyLong(), any(Pageable.class)))
                    .thenReturn(productPage);

            List<ProductReportDTO> result = service.getProductsReportToPdf(null, companyId, pageable);

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(product1.getName(), result.get(0).getName());
            assertEquals(product2.getName(), result.get(1).getName());
            verify(repository, times(1))
                    .searchProductsReport(isNull(), eq(companyId), eq(pageable));
            mockedPageableUtils.verify(() ->
                    PageableUtils.validatePageable(any(Pageable.class), anyList()), times(1));
        }
    }

    @Test
    @DisplayName("Should handle valid status")
    void getProductsReportToPdf_shouldHandleValidStatus() {
        try (MockedStatic<PageableUtils> mockedPageableUtils = mockStatic(PageableUtils.class)) {
            mockedPageableUtils.when(() -> PageableUtils.validatePageable(any(Pageable.class), anyList()))
                    .thenReturn(pageable);

            Page<Product> productPage = new PageImpl<>(Collections.singletonList(product1), pageable, 1);
            when(repository.searchProductsReport(eq(StockStatus.NORMAL.name()), anyLong(), any(Pageable.class)))
                    .thenReturn(productPage);

            List<ProductReportDTO> result = service.getProductsReportToPdf(StockStatus.NORMAL.name(), companyId, pageable);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(product1.getName(), result.get(0).getName());
            verify(repository, times(1))
                    .searchProductsReport(eq(StockStatus.NORMAL.name()), eq(companyId), eq(pageable));
            mockedPageableUtils.verify(() ->
                    PageableUtils.validatePageable(any(Pageable.class), anyList()), times(1));
        }
    }
}
