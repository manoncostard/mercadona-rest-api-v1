package com.example.mercadonarestapi.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "promotions")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long promotion_id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double reduction;


    public Promotion(LocalDate startDate, LocalDate endDate, Double reduction) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reduction = reduction;
    }

    public Promotion() {
    }
}
