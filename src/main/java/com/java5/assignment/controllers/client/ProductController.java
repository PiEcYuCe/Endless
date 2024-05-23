package com.java5.assignment.controllers.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import com.java5.assignment.model.ProductVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class ProductController {
    private static final int PRODUCTS_PER_PAGE = 6;

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
        List<ProductVersion> productVersions = objectMapper.readValue(jsonData, new TypeReference<List<ProductVersion>>() {
        });

        if(pre==1){
            page =(int) productVersions.size() / 6;
        }
        else if (pre >1){
            page = page-1;
        }

        if(next==productVersions.size() / 6){
            page = 1;
        }
        else if (next<=productVersions.size()/6 && next>0){
            page = page+1;
        }

        int startIndex = (page - 1) * PRODUCTS_PER_PAGE;
        int endIndex = Math.min(startIndex + PRODUCTS_PER_PAGE, productVersions.size());


        List<ProductVersion> productsOnPage = productVersions.subList(startIndex, endIndex);

        model.addAttribute("productVersions", productsOnPage);
        model.addAttribute("currentPage", page);

        int totalPages = (int) Math.ceil((double) productVersions.size() / PRODUCTS_PER_PAGE);
        model.addAttribute("totalPages", totalPages);

        return "client/index";
    }

}
