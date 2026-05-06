package com.piogrammer.erp.invoice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequest {
    private Long productId;
    private int quantity;

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
