package com.example.mercadonarestapi.service;


import com.example.mercadonarestapi.pojo.Category;
import com.example.mercadonarestapi.pojo.Product;
import com.example.mercadonarestapi.pojo.Promotion;
import com.example.mercadonarestapi.repository.ProductRepository;
import com.example.mercadonarestapi.service.impl.FileStoreServiceImpl;
import com.example.mercadonarestapi.service.impl.ProductServiceImpl;
import org.apache.commons.io.FilenameUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;


    @InjectMocks
    private ProductServiceImpl productService;

    @InjectMocks
    private FileStoreServiceImpl fileStoreService;


    @Test
    public void ProductService_CreateProduct_UniqueFileNameGenerated() {
        String originalFilename = "monImage.png";
        String productTitle = "My Product Test";

        UUID uuid = UUID.randomUUID();
        String fileExtension = FilenameUtils.getExtension(originalFilename);
        String fileName1 = productTitle + uuid +'.'+ fileExtension;

        UUID uuid2 = UUID.randomUUID();
        String fileExtension2 = FilenameUtils.getExtension(originalFilename);
        String fileName2 = productTitle + uuid2 +'.'+ fileExtension2;

        Assertions.assertThat(fileName1).isNotEqualTo(fileName2);
    }

    @Test
    public void ProductService_GetAllProducts_ReturnProducts() {
        Product product1 = new Product("my product title", "my description", 9.99, "imageurl", Category.CHEESE );
        Product product2 = new Product("my product title", "my description", 12.99, "imageurl", Category.HOME );
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> myProducts = productService.getAllProducts();

        Assertions.assertThat(myProducts).isNotNull();
    }

    @Test
    public void ProductService_GetAllProducts_ReturnProductsWithoutExpiredPromotions() {
        Promotion promotion1 = new Promotion( LocalDate.of(2022,11,20), LocalDate.of(2022, 12, 20),20.00);
        Promotion promotion2 = new Promotion( LocalDate.of(2022,11,20), LocalDate.of(2024, 12, 20),10.00);
        Product product1 = new Product("my product title", "my description", 9.99, "imageurl", Category.CHEESE , promotion1);
        Product product2 = new Product("my product title", "my description", 12.99, "imageurl", Category.HOME , promotion2);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> myProducts = productService.getAllProducts();

        Assertions.assertThat(myProducts.get(0).getPromotion()).isNull();

    }

    @Test
    public void ProductService_GetProductsByCategory_FindsMatchingCategory() {
        Product product1 = new Product("my product title", "my description", 9.99, "imageurl", Category.FRUITS );
        Product product2 = new Product("my product title", "my description", 12.99, "imageurl", Category.FRUITS );
        List<Product> fakeProducts = new ArrayList<>();
        fakeProducts.add(product1);
        fakeProducts.add(product2);
        when(productRepository.findAllByCategory(Mockito.any(Category.class))).thenReturn(fakeProducts);

        List<Product> products = productService.getProductsByCategory("FRUITS");

        Assertions.assertThat(products.size()).isEqualTo(2);
    }
    @Test
    public void ProductService_GetProductsByCategory_NoMatchingCategory() {

        List<Product> products = productService.getProductsByCategory("FRUIT");

        Assertions.assertThat(products).isNull();
    }


    @Test
    public void ProductService_DeleteProduct_FilenameExtract() {
        String myurl = "myurl/";
        Product product = new Product("my product title", "my description", 9.99, "myurl/myfile.png", Category.FRUITS );
        String fileName = product.getImage().replaceFirst(myurl, "");

        Assertions.assertThat(fileName).isEqualTo("myfile.png");
    }

}