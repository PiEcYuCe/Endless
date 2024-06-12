package com.java5.assignment.controllers.client;

import com.java5.assignment.dto.ProductInfoDTO;
import com.java5.assignment.entities.Brand;
import com.java5.assignment.entities.Category;
import com.java5.assignment.jpa.*;
import com.java5.assignment.services.ProductVersionService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductVersionRepository productVersionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    AttributeValueRepository attributeValueRepository;

    @Autowired
    ProductVersionService productVersionService;


    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.PRODUCT);
    }

    @GetMapping("/product")
    public String listProducts(Model model,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "9") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        org.springframework.data.domain.Page<ProductInfoDTO> productPage = productVersionService.getAllProducts(pageable);
        model.addAttribute("productPage", productPage);

        return "client/index";
    }


    @ModelAttribute("attributes")
    public String showColors(Model model) {
        List<Category> categories = categoryRepository.findAll();
        List<Brand> brands = brandRepository.findAll();
//        BigDecimal minPrice = productVersionRepository.findMinPrice();
//        BigDecimal maxPrice = productVersionRepository.findMaxPrice();
//        List<AttributeValue> colors = attributeValueRepository.findValuesByAttributeName("Color");
//        List<AttributeValue> ramValues = attributeValueRepository.findValuesByAttributeName("RAM");
//        List<AttributeValue> storageValues = attributeValueRepository.findValuesByAttributeName("Storage");
//        List<AttributeValue> screenSizeValues = attributeValueRepository.findValuesByAttributeName("Screen Size");
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
//        model.addAttribute("minPrice", minPrice);
//        model.addAttribute("maxPrice", maxPrice);
//        model.addAttribute("colors", colors);
//        model.addAttribute("ramValues", ramValues);
//        model.addAttribute("storageValues", storageValues);
//        model.addAttribute("screenSizeValues", screenSizeValues);
        return "client/index";
    }

    @ModelAttribute("productLists")
    public String showProducts(Model model) {
        List<ProductInfoDTO> productInfoDTOS = new ArrayList<>();
        model.addAttribute("productInfoDTOS",productVersionService.getAllProductInfo());
        return "client/index";
    }
}
