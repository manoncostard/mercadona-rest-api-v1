package com.example.mercadonarestapi.ws.backOffice;


import com.example.mercadonarestapi.pojo.Product;
import com.example.mercadonarestapi.pojo.Promotion;
import com.example.mercadonarestapi.service.ProductService;
import com.example.mercadonarestapi.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/back-office/promotions")
public class PromotionWs {


    @Autowired
    private PromotionService promotionService;

    @Autowired
    private ProductService productService;


    @PostMapping
    public void createPromotion(@RequestBody Promotion promotion, Long product_id) throws IOException  {
        promotionService.createPromotion(promotion, product_id);
    }

    @PutMapping
    public void updatePromotionById(@RequestBody Promotion promotion , @RequestParam Long id) {
    promotionService.updatePromotionById(promotion, id);
    }

    @DeleteMapping
    public void deletePromotion(@RequestParam Long product_id, Long promotion_id ) {
        Product product = productService.getProductById(product_id);
        product.setPromotion(null);
        promotionService.deletePromotionById(promotion_id);
    }

}
