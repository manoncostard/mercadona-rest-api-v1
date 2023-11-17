package com.example.mercadonarestapi.repository;




import com.example.mercadonarestapi.pojo.Category;
import com.example.mercadonarestapi.pojo.Product;
import com.example.mercadonarestapi.pojo.ProductDisplayClient;
import com.example.mercadonarestapi.pojo.Promotion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAllByCategory(Category category);

    @Query(nativeQuery = true)
    List<ProductDisplayClient> findDisplayInfo();

    @Query(nativeQuery = true)
    List<ProductDisplayClient> findDisplayInfoByCategory(@Param("category") String category);


    Product findByPromotion(Promotion promotion);
}
