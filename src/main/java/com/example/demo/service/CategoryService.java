package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.CategoryTreeResponse;
import com.example.demo.model.Category;

public interface CategoryService {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category create(Category category);
    Optional<Category> update(Long id, Category category);
    void deleteById(Long id);
    List<Category> findByParentId(Long parentId);
    void repairSlugs();
    List<CategoryTreeResponse> getCategoryTree();
}
