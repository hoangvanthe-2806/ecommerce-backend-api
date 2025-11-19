package com.example.demo.model;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String slug;
    @Column(length = 2000)
    private String description;
    @Column(name = "category_id")
    private Long categoryId;
    @Column(precision = 19, scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    private Integer stock = 0;
    private Integer status = 1;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Product(String name, String description, Long categoryId, BigDecimal price, Integer stock, Integer status) {
        this.name = name;
        this.slug = toSlug(name);
        this.description = description;
        this.categoryId = categoryId;
        this.price = price != null ? price : BigDecimal.ZERO;
        this.stock = stock != null ? stock : 0;
        this.status = status != null ? status : 1;
    }

    // khi thay doi name, slug cung thay doi theo
    public void setName(String name) {
        this.name = name;
        this.slug = toSlug(name);
    }

    private String toSlug(String input) {
        if (input == null)
            return null;
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", "");
        normalized = normalized.replace('đ', 'd').replace('Đ', 'D');
        return normalized.toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("[\\s-]+", "-");
    }
}
