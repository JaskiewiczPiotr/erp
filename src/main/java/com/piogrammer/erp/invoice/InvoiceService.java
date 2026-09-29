package com.piogrammer.erp.invoice;

import com.piogrammer.erp.customer.Customer;
import com.piogrammer.erp.customer.CustomerRepository;
import com.piogrammer.erp.errorhandler.InvalidInvoiceId;
import com.piogrammer.erp.exception.NotEnoughStockException;
import com.piogrammer.erp.invoice.*;
import com.piogrammer.erp.invoice.dto.InvoiceResponse;
import com.piogrammer.erp.product.Product;
import com.piogrammer.erp.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepo;
    private final ProductRepository productRepo;
    private final CustomerRepository customerRepo;

    public InvoiceService(InvoiceRepository invoiceRepo,
                          ProductRepository productRepo,
                          CustomerRepository customerRepo) {
        this.invoiceRepo = invoiceRepo;
        this.productRepo = productRepo;
        this.customerRepo = customerRepo;
    }

    @Transactional
    public Invoice createInvoice(CreateInvoiceRequest request) {

        Customer customer = getCustomer(request.getCustomerId());

        Invoice invoice = createEmptyInvoice(customer);

        List<InvoiceItem> items = buildItems(request, invoice);

        BigDecimal total = calculateTotal(items);

        invoice.setItems(items);
        invoice.setTotal(total);

        return invoiceRepo.save(invoice); // 🔥 FIX
    }


    ///obsługa DTO

    public InvoiceResponse mapToResponse(Invoice invoice){

        InvoiceResponse response = new InvoiceResponse();

        response.setId(invoice.getId());

        response.setCustomerName(
                invoice.getCustomer().getName()
        );

        response.setTotal(invoice.getTotal());

        response.setDate(invoice.getDate());

        return response;
    }

    public List<InvoiceResponse> getAllInvoicesResponse(){

        List<Invoice> invoices = invoiceRepo.findAll();

        List<InvoiceResponse> responses = new ArrayList<>();

        for(Invoice invoice : invoices){
            responses.add(mapToResponse(invoice));
        }

        return responses;
    }


    // 🔹 pobranie klienta
    public Customer getCustomer(Long id){
        return customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    // 🔹 pustą faktura
    public Invoice createEmptyInvoice(Customer customer){
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setDate(LocalDate.now());
        return invoice;
    }

    // 🔹 itemy
    public List<InvoiceItem> buildItems(CreateInvoiceRequest request, Invoice invoice){

        List<InvoiceItem> items = new ArrayList<>();

        for(ItemRequest i : request.getItems()){

            Product product = getProduct(i.getProductId());
            validateStock(product, i.getQuantity());
            /*
            product.setQuantity(product.getQuantity() - i.getQuantity());
            */

            updateStock(product, i.getQuantity());
            InvoiceItem item = createInvoiceItem(product, i.getQuantity(), invoice);

            items.add(item);
        }

        return items;
    }

    // 🔹 total
    public BigDecimal calculateTotal(List<InvoiceItem> items){

        BigDecimal total = BigDecimal.ZERO;

        for(InvoiceItem item : items){
            BigDecimal sum = item.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            total = total.add(sum);
        }

        return total;
    }

    public void validate(int stock, int requested){
        if(stock<requested){
            throw new RuntimeException("Not enough stock");
        }
    }

    public void validateStock(Product product, int requested){

        if(product.getQuantity()<requested){
            throw new NotEnoughStockException();
        }
    }

    public InvoiceItem createInvoiceItem(Product product, int quantity, Invoice invoice){
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setProduct(product);
        invoiceItem.setQuantity(quantity);
        invoiceItem.setPrice(product.getPrice());
        invoiceItem.setInvoice(invoice);
        return invoiceItem;
    }

    public void updateStock(Product product, int quantity){
         int newQuantity = product.getQuantity()-quantity;
         product.setQuantity(newQuantity);
    }



    public Product getProduct(Long productId){
        return productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

    }

    public List<Invoice> getAllInvoices(){
        return invoiceRepo.findAll();

    }

    public Invoice getInvoice(Long id){
        checkIfInvoiceExists(id);
        return invoiceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    public void checkIfInvoiceExists(Long id){
        if (invoiceRepo.existsById(id)) {
            getInvoice(id);
        }else {
            throw new InvalidInvoiceId("Invoice with id " + id + " does not exist");
        }
    }

    public void restoreStock(Product product, int quantity){
        int newQuantity = product.getQuantity()+quantity;
        product.setQuantity(newQuantity);
    }



    @Transactional
    public void deleteInvoice(Long id){
        Invoice invoice = invoiceRepo.getReferenceById(id);
        for(InvoiceItem item : invoice.getItems()){
            Product product = item.getProduct();
            restoreStock(product,item.getQuantity());
        }

        invoiceRepo.delete(invoice);
    }

    @Transactional
    public void updateInvoice(Long id, CreateInvoiceRequest request){
        Invoice invoice = invoiceRepo.getReferenceById(id);

        // Restore stock for existing items
        for(InvoiceItem item : invoice.getItems()){
            Product product = item.getProduct();
            restoreStock(product,item.getQuantity());
        }

        // Clear existing items
        invoice.getItems().clear();

        // Build new items
        List<InvoiceItem> newItems = buildItems(request, invoice);
        BigDecimal total = calculateTotal(newItems);

        invoice.setItems(newItems);
        invoice.setTotal(total);

        invoiceRepo.save(invoice);
    }


}