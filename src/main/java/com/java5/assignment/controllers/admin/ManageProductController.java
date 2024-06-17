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
import com.java5.assignment.utils.ErrorModal;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import com.java5.assignment.model.ProductModel;
import com.java5.assignment.utils.SuccessModal;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    @PostMapping("/manage-add-product")
    public String addPro(@Valid ProductModel productModel, BindingResult errors, RedirectAttributes redirectAttributes) {
        if (productRepository.existsByName(productModel.getProductName())) {
            errors.addError(new FieldError("productModel", "productName", "Product name already exists"));
        }

        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Unable to add product. Please check the form and try again."));
            return "redirect:/manage-product";
        }

        Product product = new Product();
        product.setName(productModel.getProductName());
        product.setStatus(productModel.isProductStatus());
        product.setDescription(productModel.getProductDescription());
        product.setCategoryID(categoryRepository.findById(productModel.getProductCategory()).orElse(null));
        product.setBrandID(brandRepository.findById(productModel.getProductBrand()).orElse(null));

        productRepository.save(product);

        redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Product added successfully!"));
        return "redirect:/manage-product";
    }


    @PostMapping("/manage-edit-product")
    public String editPro(@RequestParam("id") long id, Model model) {
        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        return "admin/layout";
    }

    @PostMapping("/manage-update-product")
    public String updateProduct(@Valid ProductModel productModel, BindingResult error,
                                @RequestParam("id") long id, RedirectAttributes redirectAttributes) {
        if (error.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Unable to update product. Please check the form and try again."));
            return "redirect:/manage-product";
        }

        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setName(productModel.getProductName());
            product.setStatus(productModel.isProductStatus());
            product.setDescription(productModel.getProductDescription());
            product.setCategoryID(categoryRepository.findById(productModel.getProductCategory()).orElse(null));
            product.setBrandID(brandRepository.findById(productModel.getProductBrand()).orElse(null));

            productRepository.save(product);

            redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Product updated successfully!"));
        } else {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Product not found."));
        }

        return "redirect:/manage-product";
    }


    @PostMapping("/manage-delete-product")
    public String deleteProduct(@RequestParam("id") long id, RedirectAttributes redirectAttributes) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.delete(product);
            redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Product deleted successfully!"));
        } else {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Product not found."));
        }

        return "redirect:/manage-product";
    }


    @GetMapping("/manage-clear-product")
    public String clearForm() {
        return "redirect:/manage-product";
    }


}
