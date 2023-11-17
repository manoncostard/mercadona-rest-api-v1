package com.example.mercadonarestapi.service;




import com.example.mercadonarestapi.pojo.Promotion;

import java.io.IOException;

public interface PromotionService {

    Promotion getPromotionById(Long promotion_id);

    void createPromotion(Promotion promotion, Long product_id) throws IOException;

    void updatePromotionById(Promotion promotion, Long id);

    void deletePromotionById(Long id);


}
