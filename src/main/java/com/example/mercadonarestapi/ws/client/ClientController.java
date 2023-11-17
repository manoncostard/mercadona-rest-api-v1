package com.example.mercadonarestapi.ws.client;



import com.example.mercadonarestapi.pojo.Category;
import com.example.mercadonarestapi.pojo.ProductDisplayClient;
import com.example.mercadonarestapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<ProductDisplayClient> getAllProductsDisplayInfo() {
        return productService.getAllProductsDisplayInfo();
    }

    @GetMapping("/products/{category}")
    public List<ProductDisplayClient> getAllProductsDisplayInfoByCategory(@PathVariable String category) {
        return productService.getAllProductsDisplayInfoByCategory(category);
    }

    @GetMapping("/categories")
    public Object[] getCategories() {
        return Category.class.getEnumConstants();
    }
}
