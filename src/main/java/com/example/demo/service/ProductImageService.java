package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;
import com.example.demo.model.ProductImage;

public interface ProductImageService {
    List<ProductImage> findByProductId(Long productId);
    Optional<ProductImage> findById(Long id);
    ProductImage create(ProductImage image);
    void delete(Long id);

    ProductImage uploadAndSave(Long productId, MultipartFile file, Product product) throws IOException;
}