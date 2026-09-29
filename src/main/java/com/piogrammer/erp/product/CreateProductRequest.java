package com.piogrammer.erp.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "Product name is required")
        String name,

        @NotNull(message = "Product price is required")
        @DecimalMin(value = "0.01", inclusive = false, message = "Product price must be greater than 0")
        BigDecimal price,

        @Min(value = 0, message = "Product quantity must be greater than or equal to 0")
        int quantity
) {
}
