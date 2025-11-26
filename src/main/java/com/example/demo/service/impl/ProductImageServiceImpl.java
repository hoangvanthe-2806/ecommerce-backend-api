package com.example.demo.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;
import com.example.demo.model.ProductImage;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.service.CloudinaryService;
import com.example.demo.service.ProductImageService;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository repo;
    private final CloudinaryService cloudinaryService;
    public ProductImageServiceImpl(ProductImageRepository repo, CloudinaryService cloudinaryService) {
        this.repo = repo;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public List<ProductImage> findByProductId(Long productId) {
        return repo.findByProductIdOrderBySortOrderAsc(productId);
    }

    @Override
    public Optional<ProductImage> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public ProductImage create(ProductImage image) {
        return repo.save(image);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public ProductImage uploadAndSave(Long productId, MultipartFile file, Product product) throws IOException {
        String url = cloudinaryService.upload(file);
        ProductImage img = new ProductImage();
        img.setProduct(product);
        img.setUrl(url);
        img.setAltText(file.getOriginalFilename());
        //set isPrimary/sortOrder theo logic
        return repo.save(img);
    }
}