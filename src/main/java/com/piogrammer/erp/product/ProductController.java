package com.piogrammer.erp.product;

import com.piogrammer.erp.customer.Customer;
import com.piogrammer.erp.customer.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }
}