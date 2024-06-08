package com.java5.assignment.services;

import com.java5.assignment.entities.ProductVersion;
import com.java5.assignment.jpa.ProductVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductVersionService {
    @Autowired
    private ProductVersionRepository productVersionRepository;

    public List<ProductVersion> getTop3ProductVersionsByCategory(Long categoryId) {
        Pageable pageable = PageRequest.of(0, 3);
        List<ProductVersion> list = productVersionRepository.findByCategoryId(categoryId, pageable);
        return list;
    }
}
