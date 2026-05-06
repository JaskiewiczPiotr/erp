package com.piogrammer.erp.invoice;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.piogrammer.erp.customer.Customer;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Invoice {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }



    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<InvoiceItem> items;

    @ManyToOne
    private Invoice invoice;
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Customer customer;

    private LocalDate date;

    private BigDecimal total;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String number;
}
