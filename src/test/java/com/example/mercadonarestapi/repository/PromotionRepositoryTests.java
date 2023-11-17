package com.example.mercadonarestapi.repository;


import com.example.mercadonarestapi.pojo.Promotion;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class PromotionRepositoryTests {

    @Autowired
    private PromotionRepository promotionRepository;

    @Test
    public void PromotionRepository_Save_ReturnSavedPromo() {
        Promotion promotion = new Promotion( LocalDate.of(2023,11,20), LocalDate.of(2023, 12, 20),20.00);

        Promotion savedPromotion = promotionRepository.save(promotion);

        Assertions.assertThat(savedPromotion).isNotNull();
        Assertions.assertThat(savedPromotion.getEndDate()).isEqualTo(LocalDate.of(2023, 12, 20));
    }

    @Test
    public void PromotionRepository_FindById_ReturnsPromo() {
        Promotion promotion = new Promotion( LocalDate.of(2023,11,20), LocalDate.of(2023, 12, 20),20.00);
        Promotion savedPromotion = promotionRepository.save(promotion);

        Promotion promotionFound = promotionRepository.findById(savedPromotion.getPromotion_id()).get();

        Assertions.assertThat(promotionFound).isNotNull();
        Assertions.assertThat(promotionFound.getEndDate()).isEqualTo(LocalDate.of(2023, 12, 20));
    }

    @Test
    public void PromotionRepository_DeleteById_ReturnsPromoIsEmpty() {
        Promotion promotion = new Promotion( LocalDate.of(2023,11,20), LocalDate.of(2023, 12, 20),20.00);
        Promotion savedPromotion = promotionRepository.save(promotion);

        promotionRepository.deleteById(savedPromotion.getPromotion_id());
        Optional<Promotion> promotionFound = promotionRepository.findById(savedPromotion.getPromotion_id());

        Assertions.assertThat(promotionFound).isEmpty();
    }
}
