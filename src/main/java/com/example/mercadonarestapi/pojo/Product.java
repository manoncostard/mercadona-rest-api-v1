package com.example.mercadonarestapi.pojo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@NamedNativeQuery(name = "Product.findDisplayInfo",
        query = "SELECT  products.product_id as productId, products.category, products.title, products.description, products.image, products.price, CASE WHEN promotions.reduction ISNULL THEN products.price WHEN promotions.reduction IS NOT NULL THEN products.price * (1 - (promotions.reduction/100)) END as finalPrice FROM products LEFT JOIN promotions ON products.promotion_id = promotions.promotion_id AND promotions.end_date >= NOW() AND promotions.start_date <= NOW()",
        resultSetMapping = "Mapping.ProductDisplayInfo")
@SqlResultSetMapping(
        name = "Mapping.ProductDisplayInfo",
        classes = {@ConstructorResult(
                targetClass = ProductDisplayClient.class,
                columns = {
                        @ColumnResult(name = "productId"),
                        @ColumnResult(name = "category"),
                        @ColumnResult(name = "title"),
                        @ColumnResult(name = "description"),
                        @ColumnResult(name = "image"),
                        @ColumnResult(name = "price"),
                        @ColumnResult(name = "finalPrice")
                }
        )}
)


@NamedNativeQuery(name = "Product.findDisplayInfoByCategory",
        query = "SELECT  products.product_id as productId, products.category, products.title, products.description, products.image, products.price, CASE WHEN promotions.reduction ISNULL THEN products.price WHEN promotions.reduction IS NOT NULL THEN products.price * (1 - (promotions.reduction/100)) END as finalPrice FROM products LEFT JOIN promotions ON products.promotion_id = promotions.promotion_id AND promotions.end_date >= NOW() AND promotions.start_date <= NOW() WHERE products.category = :category ;",
        resultSetMapping = "Mapping.ProductDisplayInfoByCategory")
@SqlResultSetMapping(
        name = "Mapping.ProductDisplayInfoByCategory",
        classes = {@ConstructorResult(
                targetClass = ProductDisplayClient.class,
                columns = {
                        @ColumnResult(name = "productId"),
                        @ColumnResult(name = "category"),
                        @ColumnResult(name = "title"),
                        @ColumnResult(name = "description"),
                        @ColumnResult(name = "image"),
                        @ColumnResult(name = "price"),
                        @ColumnResult(name = "finalPrice")
                }
        )}
)

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long product_id;

    private String title;

    private String description;

    private Double price;

    private String image;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    public Product(String title, String description, Double price, String image, Category category, Promotion promotion) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.promotion = promotion;
    }

    public Product(String title, String description, Double price, String image, Category category) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    public Product() {
    }
}
