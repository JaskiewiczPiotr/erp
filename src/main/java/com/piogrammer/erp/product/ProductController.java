package com.piogrammer.erp.product;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;
    public final ProductService productService;

    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }


    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @GetMapping("/one")
    public Product getById(@RequestParam Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestParam Long id) {
        productRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Product update(@RequestParam Long id, @RequestBody Product updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }/*
    @PostMapping
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }
*/
    @PostMapping
    public ProductDto createProduct(
            @Valid @RequestBody CreateProductRequest request){
        return productService.createProduct(request);
    }
}