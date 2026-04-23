package com.hextech.estoque_api.infrastructure.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageableUtils {

    public static Pageable validatePageable(Pageable pageable, List<String> allowedSortFields) {
        Sort validSort = Sort.unsorted();

        for (Sort.Order order : pageable.getSort()) {
            String field = order.getProperty();
            if(allowedSortFields.contains(field)) {
                validSort = validSort.and(Sort.by(order.getDirection(), field));
            }
        }

        if (validSort.isUnsorted()) {
            validSort = Sort.by("id").ascending();
        }

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), validSort);
    }

}
