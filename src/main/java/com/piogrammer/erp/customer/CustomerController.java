package com.piogrammer.erp.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository repo;

    public CustomerController(CustomerRepository repo, CustomerService serivce) {
        this.repo = repo;
        this.serivce = serivce;
    }

    public final  CustomerService serivce;

    @GetMapping
    public List<Customer> getAll() {
        return repo.findAll();
    }

    @PostMapping("/bulk")
    public List<Customer> createManyUsers(@RequestBody List<Customer> customers){
        return serivce.createManyUsers(customers);
    }

    @PostMapping("onecustomer")
    public Customer createOneCustomer(@RequestBody Customer customer){
        return serivce.createOneUser(customer);
    }


}