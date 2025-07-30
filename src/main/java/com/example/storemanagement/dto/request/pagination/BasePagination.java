package com.example.storemanagement.dto.request.pagination;

import lombok.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasePagination<T> {

    private List<T> data;
    private int page;
    private int size;
    private int totalElements;
    private int totalPages;

    public static <T> BasePagination<T> builder(List<T> data, Pageable pageable, long total) {
        BasePagination<T> pageResult = new BasePagination<>();
        pageResult.setData(data);
        pageResult.setPage(pageable.getPageNumber());
        pageResult.setSize(pageable.getPageSize());
        pageResult.setTotalElements((int) total);
        pageResult.setTotalPages((int) Math.ceil((double) total / pageable.getPageSize()));
        return pageResult;
    }
}
