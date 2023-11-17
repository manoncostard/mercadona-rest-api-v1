package com.example.mercadonarestapi.repository;



import com.example.mercadonarestapi.pojo.Category;
import com.example.mercadonarestapi.pojo.Product;
import com.example.mercadonarestapi.pojo.ProductDisplayClient;
import com.example.mercadonarestapi.pojo.Promotion;
import com.example.mercadonarestapi.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository ;

    @Test
    public void testProduct_Save_ReturnProduct() {
        //Arrange
        Product product = new Product("title", "description", 20.58, "image", Category.HOME);

        //Act
        Product savedProduct = productRepository.save(product);

        //Assert
        Assertions.assertThat(savedProduct).isNotNull();

    }
    @Test
    public void ProductRepository_FindAll_ReturnMoreThanOneProduct() {
        Product product = new Product("title", "description", 20.58, "image", Category.HOME);
        Product product2 = new Product("other title", "other description", 30.58, "image", Category.HOME);
        productRepository.save(product);
        productRepository.save(product2);

        List<Product> productList = (List<Product>) productRepository.findAll();

        Assertions.assertThat(productList).isNotNull();
        Assertions.assertThat(productList.size()).isGreaterThan(2);
    }

    @Test
    public void ProductRepository_FindById_ReturnsProduct() {
        Product product = new Product("title", "description", 20.58, "image", Category.HOME);
        productRepository.save(product);

        Product productFound =  productRepository.findById(product.getProduct_id()).get();

        Assertions.assertThat(productFound).isNotNull();
    }

    @Test
    public void ProductRepository_FindByCategory_ReturnProductFromFish() {
        Product product = new Product("title", "description", 20.58, "image", Category.FISH);
        Product product2 = new Product("other title", "other description", 30.58, "image", Category.HOME);
        productRepository.save(product);
        productRepository.save(product2);

        List<Product> productList = (List<Product>) productRepository.findAllByCategory(Category.FISH);

        Assertions.assertThat(productList).isNotNull();
        Assertions.assertThat(productList.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void ProductRepository_DeleteProduct_ReturnProductIsEmpty() {
        Product product = new Product("title", "description", 20.58, "image", Category.FISH);
        productRepository.save(product);

        productRepository.deleteById(product.getProduct_id());
        Optional<Product> productReturn = productRepository.findById(product.getProduct_id());

        Assertions.assertThat(productReturn).isEmpty();
    }

    @Test
    public void ProductRepository_FindDisplayInfo_ReturnProductDisplayInfo() {
        Promotion promotion = new Promotion( LocalDate.of(2023,11,20), LocalDate.of(2023, 12, 20),20.00);
        Product product = new Product("title", "description", 20.58, "image", Category.FISH, promotion);
        productRepository.save(product);

        List<ProductDisplayClient> productDisplayList = (List<ProductDisplayClient>) productRepository.findDisplayInfo();

        Assertions.assertThat(productDisplayList.size()).isGreaterThan(1);
        Assertions.assertThat(productDisplayList.get(0).getFinalPrice()).isNotNull();
    }
    @Test
    public void ProductRepository_FindDisplayInfo_CalculatesFinalPriceCorrectly() {
        Promotion promotion = new Promotion( LocalDate.of(2023,10,20), LocalDate.of(2023, 12, 20),20.00);
        Product product = new Product("title", "description", 100.00, "image", Category.FISH, promotion);
        productRepository.save(product);
        Long productId = product.getProduct_id();

        List<ProductDisplayClient> productDisplayList = (List<ProductDisplayClient>) productRepository.findDisplayInfo();

        for (ProductDisplayClient productDisplay : productDisplayList) {
            if(productDisplay.getProductId().equals(productId)) {
                Assertions.assertThat(productDisplay.getFinalPrice()).isEqualTo(80);
            }
        }

    }
    @Test
    public void ProductRepository_FindDisplayInfoByCategory_ReturnProductDisplayInfo() {
        Promotion promotion = new Promotion( LocalDate.of(2023,11,20), LocalDate.of(2023, 12, 20),20.00);
        Product product = new Product("title", "description", 20.58, "image", Category.FISH, promotion);
        productRepository.save(product);

        List<ProductDisplayClient> productDisplayList = (List<ProductDisplayClient>) productRepository.findDisplayInfoByCategory("FISH");

        Assertions.assertThat(productDisplayList.size()).isGreaterThanOrEqualTo(1);
        Assertions.assertThat(productDisplayList.get(0).getFinalPrice()).isNotNull();
    }
    @Test
    public void ProductRepository_FindDisplayInfoByCategory_CalculatesFinalPriceCorrectly() {
        Promotion promotion1 = new Promotion( LocalDate.of(2023,10,20), LocalDate.of(2023, 12, 20),20.00);
        Product product1 = new Product("title", "description", 100.00, "image", Category.FISH, promotion1);
        productRepository.save(product1);
        Long productId1 = product1.getProduct_id();


        Promotion promotion2 = new Promotion( LocalDate.of(2024,11,20), LocalDate.of(2024, 12, 20),20.00);
        Product product2 = new Product("title", "description", 100.00, "image", Category.FISH, promotion2);
        productRepository.save(product2);
        Long productId2 = product1.getProduct_id();

        List<ProductDisplayClient> productDisplayList = (List<ProductDisplayClient>) productRepository.findDisplayInfoByCategory("FISH");

        for (ProductDisplayClient productDisplay : productDisplayList) {
            if(productDisplay.getProductId().equals(productId1)) {
                Assertions.assertThat(productDisplay.getFinalPrice()).isEqualTo(80);
            } else if(productDisplay.getProductId().equals(productId2)) {
                Assertions.assertThat(productDisplay.getFinalPrice()).isEqualTo(100);
            }

        }

    }



}

