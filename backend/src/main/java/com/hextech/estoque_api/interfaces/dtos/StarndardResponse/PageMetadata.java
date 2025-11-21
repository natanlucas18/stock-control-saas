package com.hextech.estoque_api.interfaces.dtos.StarndardResponse;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
public class PageMetadata {

    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;
    private boolean isFirst;

    public PageMetadata(Page<?> page) {
        this.pageNumber = (page.getNumber() > 0) ? page.getNumber() + 1:1;
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.isLast = page.isLast();
        this.isFirst = page.isFirst();
    }
}
