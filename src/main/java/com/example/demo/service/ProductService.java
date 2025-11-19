package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Product;

public interface  ProductService {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    List<Product> findByCategoryId(Long categoryId);
    Product create(Product product);
    Optional<Product>update(Long id, Product product);
    void deleteById(Long id);
}
