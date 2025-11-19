package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Product;
import com.example.demo.model.ProductImage;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductImageService;

@RestController
@RequestMapping("/api/products")
public class ProductImageController {

    private final ProductImageService imgSvc;
    private final ProductRepository productRepo;

    public ProductImageController(ProductImageService imgSvc, ProductRepository productRepo) {
        this.imgSvc = imgSvc;
        this.productRepo = productRepo;
    }

    @GetMapping("/{productId}/images")
    public List<ProductImage> list(@PathVariable Long productId) {
        return imgSvc.findByProductId(productId);
    }

    @PostMapping("/{productId}/images")
    public ResponseEntity<?> create(@PathVariable Long productId, @RequestBody ProductImage payload) {
        Optional<Product> p = productRepo.findById(productId);
        if (!p.isPresent()) return ResponseEntity.notFound().build();
        payload.setProduct(p.get());
        ProductImage created = imgSvc.create(payload);
        return ResponseEntity.created(URI.create("/api/products/" + productId + "/images/" + created.getId())).body(created);
    }

    @DeleteMapping("/{productId}/images/{imageId}")
    public ResponseEntity<Void> delete(@PathVariable Long productId, @PathVariable Long imageId) {
        // optional: verify product/image match
        imgSvc.delete(imageId);
        return ResponseEntity.noContent().build();
    }
}