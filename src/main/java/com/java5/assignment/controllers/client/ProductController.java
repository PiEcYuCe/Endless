package com.java5.assignment.controllers.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java5.assignment.entities.AttributeValue;
import com.java5.assignment.entities.Brand;
import com.java5.assignment.entities.Category;
import com.java5.assignment.jpa.*;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import com.java5.assignment.model.ProductVersionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class ProductController {
    private static final int PRODUCTS_PER_PAGE = 6;

    @Autowired
    ProductVersionRepository productVersionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    AttributeValueRepository attributeValueRepository;

    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.PRODUCT);
    }

    @GetMapping("/product")
    public String goToPage(Model model, @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "0") int pre,
                           @RequestParam(defaultValue = "0") int next) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/static/json/product-version.json");
        byte[] jsonData = inputStream.readAllBytes();
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductVersionModel> productVersionModels = objectMapper.readValue(jsonData, new TypeReference<List<ProductVersionModel>>() {
        });

        if (pre == 1) {
            page = (int) productVersionModels.size() / 6;
        } else if (pre > 1) {
            page = page - 1;
        }

        if (next == productVersionModels.size() / 6) {
            page = 1;
        } else if (next <= productVersionModels.size() / 6 && next > 0) {
            page = page + 1;
        }

        int startIndex = (page - 1) * PRODUCTS_PER_PAGE;
        int endIndex = Math.min(startIndex + PRODUCTS_PER_PAGE, productVersionModels.size());


        List<ProductVersionModel> productsOnPage = productVersionModels.subList(startIndex, endIndex);

        model.addAttribute("productVersions", productsOnPage);
        model.addAttribute("currentPage", page);

        int totalPages = (int) Math.ceil((double) productVersionModels.size() / PRODUCTS_PER_PAGE);
        model.addAttribute("totalPages", totalPages);

        return "client/index";
    }

    @ModelAttribute("attributes")
    public String showColors(Model model) {
        List<Category> categories = categoryRepository.findAll();
        List<Brand> brands = brandRepository.findAll();
        BigDecimal minPrice = productVersionRepository.findMinPrice();
        BigDecimal maxPrice = productVersionRepository.findMaxPrice();
        List<AttributeValue> colors = attributeValueRepository.findValuesByAttributeName("Color");
        List<AttributeValue> ramValues = attributeValueRepository.findValuesByAttributeName("RAM");
        List<AttributeValue> storageValues = attributeValueRepository.findValuesByAttributeName("Storage");
        List<AttributeValue> screenSizeValues = attributeValueRepository.findValuesByAttributeName("Screen Size");
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("colors", colors);
        model.addAttribute("ramValues", ramValues);
        model.addAttribute("storageValues", storageValues);
        model.addAttribute("screenSizeValues", screenSizeValues);
        return "client/index";
    }
}
