package com.piogrammer.erp.product;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String name,
        BigDecimal price,
        int quantity
) {
}