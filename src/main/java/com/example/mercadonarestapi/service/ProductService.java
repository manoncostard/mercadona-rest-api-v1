package com.example.mercadonarestapi.service;




import com.example.mercadonarestapi.pojo.Product;
import com.example.mercadonarestapi.pojo.ProductDisplayClient;
import com.example.mercadonarestapi.pojo.Promotion;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    Product getProductById(Long id);

    Product createProduct(Product product, MultipartFile picture) throws IOException;

    Product updateProduct(Product product, MultipartFile picture,  Long id) throws IOException;

    void deleteProduct(Long id);

    List<ProductDisplayClient> getAllProductsDisplayInfo();

    List<ProductDisplayClient>getAllProductsDisplayInfoByCategory(String category);
    Boolean isPromotionExpired(Promotion promotion);

}
