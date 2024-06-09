package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.Product;
import com.java5.assignment.entities.ProductVersion;
import com.java5.assignment.jpa.ProductRepository;
import com.java5.assignment.jpa.ProductVersionRepository;
import com.java5.assignment.model.ProductModel;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import com.java5.assignment.model.ProductVersionModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ManageProductVersionController {


    @Autowired
    ProductRepository productRepository;


    @Autowired
    ProductVersionRepository productVersionRepository;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_PRODUCT_VERSION);
    }


    @ModelAttribute("products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @ModelAttribute("productVersions")
    public List<ProductVersion> getProductVersions() {
        return productVersionRepository.findAll();
    }


    @GetMapping("/manage-product-version")
    public String get() {
        return "admin/layout";
    }


    @PostMapping("/manage-product-version")
    public String profile(@Valid ProductVersionModel pro, BindingResult error, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
        }
        model.addAttribute("pro", pro);
        return "admin/layout";
    }


    @PostMapping("/add-product-version")
    public String addPro(@Valid ProductVersionModel productVersionModel, BindingResult error, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }

        ProductVersion productVersion = new ProductVersion();
//        productVersion.setProductID(productRepository.findById(productVersionModel.getProductID().));


        return "redirect:/manage-product-version";
    }


}
