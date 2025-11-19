package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.dto.CategoryTreeResponse;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findByParentId(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category create(Category category) {
        if (category.getSlug() == null || category.getSlug().isBlank()) {
            category.setName(category.getName()); // generate slug
        }
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> update(Long id, Category category) {
        return categoryRepository.findById(id).map(existing -> {
            existing.setName(category.getName());
            existing.setParentId(category.getParentId());
            existing.setStatus(category.getStatus());
            existing.setSortOrder(category.getSortOrder());
            return categoryRepository.save(existing);
        });
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void repairSlugs() {
        List<Category> all = categoryRepository.findAll();
        for (Category c : all) {
            c.setName(c.getName()); // regenerate slug
        }
        categoryRepository.saveAll(all);
    }

    
    @Override
    public List<CategoryTreeResponse> getCategoryTree() {
        List<Category> roots = categoryRepository.findByParentId(0L);

        return roots.stream()
                .map(r -> convertToNode(r, new java.util.HashSet<>()))
                .collect(Collectors.toList());
    }

    private CategoryTreeResponse convertToNode(Category category, java.util.Set<Long> visited) {
        CategoryTreeResponse node = new CategoryTreeResponse();
        node.setId(category.getId());
        node.setName(category.getName());
        node.setSlug(category.getSlug());
        node.setParentId(category.getParentId());
        node.setStatus(category.getStatus());
        node.setSortOrder(category.getSortOrder());

        // prevent cycles: if we've already visited this id, stop recursion
        if (category.getId() == null) {
            node.setChildren(java.util.Collections.emptyList());
            return node;
        }

        if (visited.contains(category.getId())) {
            node.setChildren(java.util.Collections.emptyList());
            return node;
        }

        visited.add(category.getId());

        List<Category> children = categoryRepository.findByParentId(category.getId());
        node.setChildren(
                children.stream()
                        .map(c -> convertToNode(c, new java.util.HashSet<>(visited)))
                        .collect(Collectors.toList()));

        return node;
    }
}
