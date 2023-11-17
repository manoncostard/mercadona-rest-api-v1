package com.example.mercadonarestapi.service;


import com.example.mercadonarestapi.pojo.Promotion;
import com.example.mercadonarestapi.repository.ProductRepository;
import com.example.mercadonarestapi.repository.PromotionRepository;
import com.example.mercadonarestapi.service.impl.ProductServiceImpl;
import com.example.mercadonarestapi.service.impl.PromotionServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PromotionServiceTests {

    @Mock
    private PromotionRepository promotionRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private PromotionServiceImpl promotionService;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void PromotionService_GetPromotionById_ReturnsPromotion() {
        Promotion promotion = new Promotion(LocalDate.of(2023,10,20), LocalDate.of(2023,12,26), 10.0);

        when(promotionRepository.findById(1L)).thenReturn(Optional.ofNullable(promotion));

        Promotion foundPromotion = promotionService.getPromotionById(1l);

        Assertions.assertThat(foundPromotion).isNotNull();
    }

}