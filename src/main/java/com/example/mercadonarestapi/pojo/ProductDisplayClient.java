package com.example.mercadonarestapi.pojo;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductDisplayClient implements Serializable {
    private Long productId;
    private String category;
    private String title;
    private String description;
    private String image;
    private Double price;
    private Double finalPrice;

    public ProductDisplayClient(Long productId, String category, String title, String description, String image, Double price, Double finalPrice) {
        this.productId = productId;
        this.category = category;
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
        this.finalPrice = finalPrice;
    }

    public ProductDisplayClient() {
    }

}