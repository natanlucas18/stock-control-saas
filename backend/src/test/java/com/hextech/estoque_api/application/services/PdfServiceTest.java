package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.infrastructure.pdf.PdfGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PdfServiceTest {
    @InjectMocks
    private PdfService pdfService;

    @Mock
    private SpringTemplateEngine templateEngine;

    @Mock
    private PdfGenerator pdfGenerator;

    String templateName;
    Map<String, Object> variables;
    String processedHtml;
    byte[] expectedPdf;

    @BeforeEach
    void setUp() {
        templateName = "testTemplate";
        variables = new HashMap<>();
        variables.put("name", "Test");
        processedHtml = "<html><body>Hello Test</body></html>";
        expectedPdf = "pdf_content".getBytes();
    }

    @Test
    @DisplayName("Should create PDF successfully")
    void createPdf_shouldCreatePdfSuccessfully() {
        // Arrange
        when(templateEngine.process(eq(templateName), any(Context.class))).thenReturn(processedHtml);
        when(pdfGenerator.generatePdfFromHtml(processedHtml)).thenReturn(expectedPdf);
        // Act
        byte[] result = pdfService.createPdf(templateName, variables);
        // Assert
        assertNotNull(result);
        assertArrayEquals(expectedPdf, result);
        verify(templateEngine).process(eq(templateName), any(Context.class));
        verify(pdfGenerator).generatePdfFromHtml(processedHtml);
    }
}
