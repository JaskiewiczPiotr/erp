package com.piogrammer.erp.customer;

import com.piogrammer.erp.errorhandler.CustomerNotFoundException;
import com.piogrammer.erp.errorhandler.InvalidCustomerException;
import com.piogrammer.erp.errorhandler.InvalidMailCustomerException;
import org.apache.catalina.util.CustomObjectInputStream;
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
        validateName(customer.getName());
        validateEmail(customer.getEmail());
        checkNameAlreadyExists(customer.getName());
        return repo.save(customer);
    }

    public void validateName(String name){
        if(name == null || name.contains("1") || name.isBlank()){
            throw new InvalidCustomerException("Invalid name");
        }
    }

    public void checkNameAlreadyExists(String name){
        if(repo.findAll().stream().anyMatch(c -> c.getName().equals(name))){
            throw new InvalidCustomerException("Name already exists");
        }
    }

    public void validateEmail(String email){
        if (email == null || !email.contains("@")){
            throw new InvalidMailCustomerException("Invalid email");
        }
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existingCustomer = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        validateName(updatedCustomer.getName());
        validateEmail(updatedCustomer.getEmail());

        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());

        return repo.save(existingCustomer);
    }

    public void deleteCustomer(Long id) {
        Customer existingCustomer = repo.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        repo.delete(existingCustomer);


    }
}