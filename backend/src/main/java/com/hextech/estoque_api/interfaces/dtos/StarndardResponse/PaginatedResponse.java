package com.hextech.estoque_api.interfaces.dtos.StarndardResponse;

import java.util.List;

public class PaginatedResponse<T> {

    private List<T> content;
    private PageMetadata pagination;

    public PaginatedResponse(List<T> content, PageMetadata pagination) {
        this.content = content;
        this.pagination = pagination;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public PageMetadata getPagination() {
        return pagination;
    }

    public void setPagination(PageMetadata pagination) {
        this.pagination = pagination;
    }
}
