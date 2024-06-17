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
import com.java5.assignment.utils.ErrorModal;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import com.java5.assignment.utils.SuccessModal;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String addProductVersion(@Valid ProductVersionCreateModel productVersionCreateModel, BindingResult errors,
                                    RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Unable to add product version. Please check the form and try again."));
            return "redirect:/manage-product-version";
        }

        String fileName = uploadService.uploadFile(productVersionCreateModel.getImage(), "product");
        if (fileName == null) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Please select an image."));
            return "redirect:/manage-product-version";
        }

        ProductVersion productVersion = new ProductVersion();
        Product product = productRepository.findById(productVersionCreateModel.getProductID()).orElse(null);
        if (product == null) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Product not found."));
            return "redirect:/manage-product-version";
        }

        productVersion.setProductID(product);
        productVersion.setVersionName(productVersionCreateModel.getVersionName());
        productVersion.setPurchasePrice(productVersionCreateModel.getPurchasePrice());
        productVersion.setPrice(productVersionCreateModel.getSalePrice());
        productVersion.setQuantity(productVersionCreateModel.getQuantity());
        productVersion.setStatus(productVersionCreateModel.isStatus());
        productVersion.setImage(fileName);

        productVersionRepository.save(productVersion);

        redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Product version added successfully!"));
        return "redirect:/manage-product-version";
    }


    @PostMapping("/productVersionAttr")
    public String addAttr(@RequestBody BindingResult error, Model model) {

        return "admin/layout";
    }




}
