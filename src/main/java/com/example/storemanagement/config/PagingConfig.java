package com.example.storemanagement.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "pagination")
@Getter
public class PagingConfig {
    private int defaultPage;
    private int defaultPageSize;
}
