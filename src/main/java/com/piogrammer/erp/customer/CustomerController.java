package com.piogrammer.erp.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository repo;

    public CustomerController(CustomerRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Customer> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        return repo.save(customer);
    }
}