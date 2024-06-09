package com.java5.assignment.controllers.client;

import com.java5.assignment.dto.ProductInfoDTO;
import com.java5.assignment.entities.Category;
import com.java5.assignment.entities.Product;
import com.java5.assignment.entities.ProductVersion;
import com.java5.assignment.jpa.CategoryRepository;
import com.java5.assignment.jpa.ProductRepository;
import com.java5.assignment.jpa.ProductVersionRepository;
import com.java5.assignment.services.ProductVersionService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import com.java5.assignment.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("")
public class HomeController {
    @Autowired
    HttpSession session;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVersionRepository productVersionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductVersionService productVersionService;

    @ModelAttribute("productCatList")
    public Map<Category, List<ProductInfoDTO>> getTop3ProductVersionsByCategory() {
        Map<Category, List<ProductInfoDTO>> productCatList = new HashMap<>();
        List<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            List<ProductInfoDTO> top3ProductVersions = productVersionService.getProductInfoByCategory(category, PageRequest.of(0, 3));
            productCatList.put(category, top3ProductVersions);
        }

        return productCatList;
    }

    @ModelAttribute("topSale")
    public List<Product> getTopSale() {
        return productRepository.findBestSellers(PageRequest.of(0, 4));
    }

    @ModelAttribute("hotProduct")
    public Product getHotProduct() {
        return productRepository.findTopRatedProducts(PageRequest.of(0, 1)).iterator().next();
    }

    @ModelAttribute("specialProduct")
    public Product getSpecialProduct() {
        return productRepository.findTopSellingProductsInLast100Orders(PageRequest.of(0, 1)).iterator().next();
    }



    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.HOME);
    }


    @ModelAttribute("account")
    public UserDto account() {
        UserDto user = (UserDto) session.getAttribute("user");
        return user;
    }

    @GetMapping("/home")
    public String get() {
        return "client/index";
    }

//
//    @ResponseBody
//    @GetMapping("/demo-product")
//    public Map<Category, List<ProductVersion>> getTop3ProductVersions() {
//        Map<Category, List<ProductVersion>> productCatList = new HashMap<>();
//        List<Category> categories = categoryRepository.findAll();
//
//        for (Category category : categories) {
//            List<ProductVersion> top3ProductVersions = productVersionService.getTop3ProductVersionsByCategory(category.getId());
//            productCatList.put(category, top3ProductVersions);
//        }
//
//        return productCatList;
//    }


    @PostMapping("/home")
    public String post(@ModelAttribute("page") Page page) {
        return "client/index";
    }
}


