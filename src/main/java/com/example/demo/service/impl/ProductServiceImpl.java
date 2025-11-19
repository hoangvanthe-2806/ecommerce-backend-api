package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo){
        this.repo = repo;
    }

    @Override
    public List<Product> findAll(){
        return repo.findAll();
    }
    @Override
    public Optional<Product> findById(Long id){
        return repo.findById(id);
    }
    @Override
    public List<Product> findByCategoryId(Long categoryId){
        return repo.findByCategoryId(categoryId);
    }
    @Override
    public Product create(Product product){
        if(product.getSlug() == null || product.getSlug().isBlank()){
            product.setName(product.getName()); // generate slug
        }
        return repo.save(product);
    }

    @Override
    public Optional<Product> update(Long id,Product product){
        return repo.findById(id).map(existing ->{
            if(product.getName() != null) existing.setName(product.getName());
            if(product.getCategoryId() != null) existing.setCategoryId(product.getCategoryId());
            if(product.getPrice() != null) existing.setPrice(product.getPrice());
            if (product.getStock() != null) existing.setStock(product.getStock());
            if(product.getStatus() != null) existing.setStatus(product.getStatus());
            return repo.save(existing);
        });
    }
    @Override
    public void deleteById(Long id){
        repo.deleteById(id);
    }
}
