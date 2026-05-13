package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.infrastructure.pdf.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PdfService {

    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private PdfGenerator pdfGenerator;

    public byte[] createPdf(String template, Map<String, Object> variables) {
        Context context = new Context();
        variables.forEach(context::setVariable);
        context.setVariable("now", LocalDateTime.now());

        String html = templateEngine.process(template, context);

        return pdfGenerator.generatePdfFromHtml(html);
    }
}
