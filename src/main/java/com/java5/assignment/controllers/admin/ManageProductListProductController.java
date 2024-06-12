package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.AttributeValue;
import com.java5.assignment.entities.Product;
import com.java5.assignment.entities.ProductVersion;
import com.java5.assignment.jpa.*;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;
import java.util.stream.Collectors;

@Controller

public class ManageProductListProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVersionRepository productVersionRepository;

    @Autowired
    AttributeValueRepository attributeValueRepository;

    @Autowired
    VersionAttributeRepository versionAttributeRepository;

    @Autowired
    AttributeRepository attributeRepository;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_PRODUCT_LIST);
    }

    @GetMapping("/manage-product-list")
    public String get() {
        return "admin/layout";
    }


    @ModelAttribute("products")
    public List<Product> getProd() {
        return productRepository.findAll();
    }

    @ModelAttribute("productversions")
    public List<ProductVersion> getProdV() {
        return productVersionRepository.findAll()
                .stream()
                .sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
                .collect(Collectors.toList());
    }

    @ModelAttribute("attributevalues")
    public List<AttributeValue> getAttrValue() {
        return attributeValueRepository.findAll();
    }


}



