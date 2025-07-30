package com.example.storemanagement.utils;

import com.example.storemanagement.config.PagingConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PagingUtils {
    private final PagingConfig config;

    public Pageable resolve(Integer page, Integer size) {
        return PageRequest.of(
                page != null ? page : config.getDefaultPage(),
                size != null ? size : config.getDefaultPageSize()
        );
    }

}
