package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.AttributeValue;
import com.java5.assignment.entities.Product;
import com.java5.assignment.entities.ProductVersion;
import com.java5.assignment.jpa.*;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return productVersionRepository.findAll(sort);
    }

    @ModelAttribute("attributevalues")
    public List<AttributeValue> getAttrValue() {
        return attributeValueRepository.findAll();
    }

    @PostMapping("/manage-product-delete")
    public String deleteProductVersion(@RequestParam("id") Long id) {
        // Xoá sản phẩm với id được chỉ định
        productVersionRepository.deleteById(id);
        // Redirect về trang quản lý danh sách sản phẩm sau khi xoá
        return "redirect:/manage-product-list";
    }

}



