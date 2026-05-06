package com.piogrammer.erp.invoice;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CreateInvoiceRequest {

    private Long customerId;
    private List<ItemRequest> items;

    public Long getCustomerId(){
        return customerId;
    }
    public List<ItemRequest> getItems(){
        return items;
    }

}
