package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private Long parentId;
    private Integer status;
    private Integer sortOrder;

    private List<CategoryResponse> children = new ArrayList<>();
}