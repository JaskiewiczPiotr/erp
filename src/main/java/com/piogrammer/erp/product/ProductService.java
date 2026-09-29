package com.piogrammer.erp.product;

import com.piogrammer.erp.errorhandler.InvalidProductDataException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
       existingProduct.setName(updatedProduct.getName());
       existingProduct.setPrice(updatedProduct.getPrice());
       existingProduct.setQuantity(updatedProduct.getQuantity());

        return productRepository.save(existingProduct);
    }



    public ProductDto createProduct(CreateProductRequest request){
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());


        Product savedProduct = productRepository.save(product);

        return new ProductDto(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice(), savedProduct.getQuantity());
    }
}
