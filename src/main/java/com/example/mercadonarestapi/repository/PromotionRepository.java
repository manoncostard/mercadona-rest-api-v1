package com.example.mercadonarestapi.repository;



import com.example.mercadonarestapi.pojo.Promotion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends CrudRepository<Promotion, Long> {

}