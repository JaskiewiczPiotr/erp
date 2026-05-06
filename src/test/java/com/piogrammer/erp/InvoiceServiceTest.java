package com.piogrammer.erp;

import com.piogrammer.erp.customer.Customer;
import com.piogrammer.erp.customer.CustomerRepository;
import com.piogrammer.erp.invoice.*;
import com.piogrammer.erp.product.Product;
import com.piogrammer.erp.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class InvoiceServiceTest {

    private final InvoiceRepository invoiceRepo = mock(InvoiceRepository.class);
    private final ProductRepository productRepo = mock(ProductRepository.class);
    private final CustomerRepository customerRepo = mock(CustomerRepository.class);

    private final InvoiceService service =
            new InvoiceService(invoiceRepo, productRepo, customerRepo);

    @Test
    void shouldCreateInvoiceAndCalculateTotal() {

        // 🔹 dane testowe
        Customer customer = new Customer();
        customer.setId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setPrice(new BigDecimal("10"));
        product.setQuantity(10);

        // 🔹 mocki
        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(invoiceRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        // 🔹 request
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setProductId(1L);
        itemRequest.setQuantity(2);

        CreateInvoiceRequest request = new CreateInvoiceRequest();
        request.setCustomerId(1L);
        request.setItems(List.of(itemRequest));

        // 🔹 wykonanie
        Invoice invoice = service.createInvoice(request);

        // 🔹 sprawdzenie
        assertThat(invoice.getTotal()).isEqualByComparingTo("20");
        assertThat(product.getQuantity()).isEqualTo(8); // magazyn zmniejszony
        assertThat(invoice.getItems()).hasSize(1);
    }
}