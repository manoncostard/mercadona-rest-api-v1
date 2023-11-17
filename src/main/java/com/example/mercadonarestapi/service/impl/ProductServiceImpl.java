package com.example.mercadonarestapi.service.impl;



import com.example.mercadonarestapi.pojo.*;
import com.example.mercadonarestapi.repository.ProductRepository;
import com.example.mercadonarestapi.service.FileStoreService;
import com.example.mercadonarestapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private final String AWS_BUCKET_PATH="https://mercadonaproductimagesbucket.s3.eu-north-1.amazonaws.com/";


    @Autowired
    private FileStoreService fileStoreService;

    @Override
    public List<Product> getAllProducts() {
        List<Product> products =  (List<Product>) productRepository.findAll();
        for(Product product : products) {
            if(product.getPromotion() != null) {
                Promotion promotion = product.getPromotion();
                if(isPromotionExpired(promotion)) {
                    product.setPromotion(null);
                }
            }
        }
        return products;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        Category cat = null;
        for(Category c : Category.values()) {
            if(c.name().equals(category)) {
                cat = c;
            }
        }
        if(cat != null) {
            List<Product> products = productRepository.findAllByCategory(cat);
            for(Product product : products) {
                if(product.getPromotion() != null) {
                    if(isPromotionExpired(product.getPromotion())) {
                        product.setPromotion(null);
                    }
                }
            }
            return (List<Product>) productRepository.findAllByCategory(cat);
        } else {
            return null;
        }
    }

    @Override
    public Product getProductById(Long id) {
        Product productFound = productRepository.findById(id).orElse(null);
        if(productFound != null) {
            if(productFound.getPromotion() != null) {
                if (isPromotionExpired(productFound.getPromotion())) {
                    productFound.setPromotion(null);
                }
            }
        }
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product, MultipartFile picture) throws IOException {
        String fileName = picture.getOriginalFilename();
        String mimeType = product.getImage();
        PictureUtil pictureUtil = new PictureUtil();
        String newName = pictureUtil.buildFileNameWithCorrectExt(fileName, mimeType);
        if( picture != null) {
            fileStoreService.uploadMultipartFileS3(picture, newName);

//        UUID uuid = UUID.randomUUID();
//        String fileExtension = FilenameUtils.getExtension(picture.getOriginalFilename());

            String filePath = AWS_BUCKET_PATH + fileName;
            product.setImage(filePath);
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, MultipartFile picture, Long id) throws IOException {
        Product exProduct = getProductById(id);
        if(exProduct != null) {
            if( picture != null) {
                String fileName = picture.getOriginalFilename();
                String mimeType = product.getImage();
                PictureUtil pictureUtil = new PictureUtil();
                String newName = pictureUtil.buildFileNameWithCorrectExt(fileName, mimeType);
                fileStoreService.uploadMultipartFileS3(picture, newName);

                String filePath = AWS_BUCKET_PATH + fileName;
                exProduct.setImage(filePath);
            }
            exProduct.setCategory(product.getCategory());
            exProduct.setTitle(product.getTitle());
            exProduct.setPrice(product.getPrice());
            exProduct.setDescription(product.getDescription());
            if(exProduct.getPromotion() != null) {
                if(isPromotionExpired(exProduct.getPromotion())) {
                    exProduct.setPromotion(null);
                }
            }
            return productRepository.save(exProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDisplayClient> getAllProductsDisplayInfo() {

        return (List<ProductDisplayClient>) productRepository.findDisplayInfo();
    }

    @Override
    public List<ProductDisplayClient> getAllProductsDisplayInfoByCategory(String category) {
        return productRepository.findDisplayInfoByCategory(category);
    }

    @Override
    public Boolean isPromotionExpired(Promotion promotion) {
        return promotion.getEndDate().isBefore(LocalDate.now());
    }


}