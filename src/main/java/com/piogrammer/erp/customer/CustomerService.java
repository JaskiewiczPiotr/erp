package com.piogrammer.erp.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public List<Customer> createManyUsers(List<Customer> customers){
        return repo.saveAll(customers);
    }

    public Customer createOneUser(Customer customer){
        if (customer.getName().isBlank()){
            return null;
        }
        return repo.save(customer);
    }
}