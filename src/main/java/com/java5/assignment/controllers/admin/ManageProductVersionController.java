package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.Attribute;
import com.java5.assignment.entities.AttributeValue;
import com.java5.assignment.entities.Product;
import com.java5.assignment.entities.ProductVersion;
import com.java5.assignment.jpa.AttributeRepository;
import com.java5.assignment.jpa.AttributeValueRepository;
import com.java5.assignment.jpa.ProductRepository;
import com.java5.assignment.jpa.ProductVersionRepository;
import com.java5.assignment.model.admin.productVersion.ProductVersionCreateModel;
import com.java5.assignment.services.UploadService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ManageProductVersionController {

    @Autowired
    AttributeRepository attributeRepository;

    @Autowired
    AttributeValueRepository attributeValueRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVersionRepository productVersionRepository;

    @Autowired
    private UploadService uploadService;


    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_PRODUCT_VERSION);
    }


    //    List
    @ModelAttribute("products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @ModelAttribute("attributes")
    public List<Attribute> getAttr() {
        return attributeRepository.findAll();

    }

    @ModelAttribute("attributeValues")
    public List<AttributeValue> getAtrrValue() {
        return attributeValueRepository.findAll();
    }

    @ModelAttribute("productVersions")
    public List<ProductVersion> getProductVersions() {
        return productVersionRepository.findAll();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////




    @GetMapping("/manage-product-version")
    public String get() {
        return "admin/layout";
    }



    @PostMapping("/manage-product-version")
    public String addPro(@Valid ProductVersionCreateModel productVersionCreateModel, BindingResult error, Model model) {
        String fileName = uploadService.uploadFile(productVersionCreateModel.getImage(), "product");
        if (fileName == null) {
            error.addError(new FieldError("product", "image", "Please select a image"));
        }
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }

        ProductVersion productVersion = new ProductVersion();
        Product product = productRepository.findById(productVersionCreateModel.getProductID()).get();
        productVersion.setProductID(product);
        productVersion.setVersionName(productVersionCreateModel.getVersionName());
        productVersion.setPurchasePrice(productVersionCreateModel.getPurchasePrice());
        productVersion.setPrice(productVersionCreateModel.getSalePrice());
        productVersion.setQuantity(productVersionCreateModel.getQuantity());
        productVersion.setStatus(productVersionCreateModel.isStatus());
        productVersion.setImage(fileName);


        productVersionRepository.save(productVersion);

        return "admin/layout";

    }

    @PostMapping("/productVersionAttr")
    public String addAttr(@RequestBody BindingResult error, Model model) {

        return "admin/layout";
    }




}
