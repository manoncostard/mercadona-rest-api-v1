package com.example.mercadonarestapi.ws.backOffice;



import com.example.mercadonarestapi.pojo.Product;
import com.example.mercadonarestapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/back-office/products")
public class ProductWs {

    @Autowired
    private ProductService productService;


    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/by-category")
    public List<Product> getProductsByCategory(@RequestParam( name = "category") String category) {
        return productService.getProductsByCategory(category);
    }

    @GetMapping("/by-id")
    public Product getProductById(@RequestParam(name = "id") Long id) {
        return productService.getProductById(id);
    }


    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestPart("product") Product product, @RequestPart("picture") MultipartFile picture) throws IOException {
        Product createProduct = productService.createProduct(product, picture);
        return ResponseEntity.status(HttpStatus.OK).body(createProduct);
    }


    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestPart Product product, @RequestPart(value = "picture", required = false) MultipartFile picture ,  @RequestParam Long product_id) throws IOException {
        Product updateProduct = productService.updateProduct(product, picture, product_id);
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }


    @DeleteMapping
    public void deleteProduct(@RequestParam Long product_id) {
        productService.deleteProduct(product_id);
    }

    @GetMapping("/checkAuth")
    public Boolean checkAuth() {
        return true;
    }
}
