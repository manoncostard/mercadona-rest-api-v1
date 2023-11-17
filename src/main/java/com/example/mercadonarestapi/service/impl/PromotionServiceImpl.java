package com.example.mercadonarestapi.service.impl;



import com.example.mercadonarestapi.pojo.Product;
import com.example.mercadonarestapi.pojo.Promotion;
import com.example.mercadonarestapi.repository.PromotionRepository;
import com.example.mercadonarestapi.service.ProductService;
import com.example.mercadonarestapi.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private ProductService productService;

    @Override
    public Promotion getPromotionById(Long promotion_id) {
        return promotionRepository.findById(promotion_id).orElse(null);
    }

    @Override
    public void createPromotion(Promotion promotion, Long product_id) throws IOException {
        Promotion newPromotion = promotionRepository.save(promotion);
        Product product = productService.getProductById(product_id);
        product.setPromotion(newPromotion);
        productService.updateProduct(product, null, product_id);
    }
    @Override
    public void updatePromotionById(Promotion promotion, Long id) {
        Promotion exPromo = getPromotionById(id);
        if(exPromo != null) {
            exPromo.setReduction(promotion.getReduction());
            exPromo.setEndDate(promotion.getEndDate());
            exPromo.setStartDate(promotion.getStartDate());
            promotionRepository.save(exPromo);
        }
    }

    @Override
    public void deletePromotionById(Long id) {
        promotionRepository.deleteById(id);
    }

}
