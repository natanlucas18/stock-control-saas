package com.hextech.estoque_api.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PdfService {

    @Autowired
    private SpringTemplateEngine templateEngine;

    public byte[] createPdf(String template, Map<String, Object> variables) {
        Context context = new Context();
        variables.forEach(context::setVariable);
        context.setVariable("now", LocalDateTime.now());

        String html = templateEngine.process(template, context);

        return generatePdfFromHtml(html);
    }

    private byte[] generatePdfFromHtml(String html) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF.", e);
        }
    }
}
