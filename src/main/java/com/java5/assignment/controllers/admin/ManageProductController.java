package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.Brand;
import com.java5.assignment.entities.Category;
import com.java5.assignment.entities.Product;
import com.java5.assignment.entities.Voucher;
import com.java5.assignment.jpa.BrandRepository;
import com.java5.assignment.jpa.CategoryRepository;
import com.java5.assignment.jpa.ProductRepository;
import com.java5.assignment.model.BrandModel;
import com.java5.assignment.model.VoucherModel;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import com.java5.assignment.model.ProductModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ManageProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BrandRepository brandRepository;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_PRODUCT);
    }

    @ModelAttribute("products")
    public List<Product> getProducts() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return productRepository.findAll(sort);
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @ModelAttribute("brands")
    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    @GetMapping("/manage-product")
    public String get() {

        return "admin/layout";
    }


    @PostMapping("/manage-product")
    public String profile(@Valid ProductModel pro, BindingResult error, Model model) {

        model.addAttribute("error", error);

        return "admin/layout";
    }


    @PostMapping("/add-product")
    public String addPro(@Valid ProductModel productModel, BindingResult error, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }

        Product product = new Product();
        product.setName(productModel.getProductName());
        product.setStatus(productModel.isProductStatus());
        product.setDescription(productModel.getProductDescription());

        product.setCategoryID(categoryRepository.findById(productModel.getProductCategory()).get());
        product.setBrandID(brandRepository.findById(productModel.getProductBrand()).get());


        productRepository.save(product);

        return "redirect:/manage-product";
    }

    @PostMapping("/edit-product")
    public String editPro(@RequestParam("id") long id, Model model) {
        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        return "admin/layout";
    }

    @PostMapping("/update-product")
    public String updateBrand(@Valid ProductModel productModel, BindingResult error,
                              @RequestParam("id") long id, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }
        Product product = new Product();
        product.setName(productModel.getProductName());
        product.setStatus(productModel.isProductStatus());
        product.setDescription(productModel.getProductDescription());
        product.setCategoryID(categoryRepository.findById(productModel.getProductCategory()).get());
        product.setBrandID(brandRepository.findById(productModel.getProductBrand()).get());


        productRepository.save(product);
        return "redirect:/manage-product";
    }

    @PostMapping("/delete-product")
    public String deleteBrand(@RequestParam("id") long id) {
        Product product = productRepository.findById(id).get();
        productRepository.delete(product);
        return "redirect:/manage-product";
    }

    @GetMapping("/clear-product")
    public String clearForm() {
        return "redirect:/manage-product";
    }


}
