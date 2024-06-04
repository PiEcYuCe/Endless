package com.java5.assignment.controllers.admin;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import com.java5.assignment.entities.Brand;
import com.java5.assignment.jpa.BrandRepository;
import com.java5.assignment.model.BrandModel;
import com.java5.assignment.services.UploadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ManageBrandController {

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    UploadService uploadService;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_BRAND);
    }

    @ModelAttribute("Brands")
    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    @GetMapping("/manage-brand")
    public String get() {
        return "admin/layout";
    }

    @PostMapping("/add-brand")
    public String addBrand(@Valid BrandModel brandModel, BindingResult error, Model model) {

        String fileName = uploadService.uploadFile(brandModel.getLogo(), "brand");
        if (fileName == null){
            error.addError(new FieldError("brand", "logo", "Please select a logo"));
        }
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }
        Brand brand = new Brand();
        brand.setName(brandModel.getName());
        brand.setLogo(fileName);
        brand.setStatus(brandModel.isStatus());
        brandRepository.save(brand);
        return "redirect:/manage-brand";
    }

    @PostMapping("/edit-brand")
    public String editBrand(@RequestParam("id") long id, Model model) {
        Brand brand = brandRepository.findById(id).get();
        model.addAttribute("brand", brand);
        return "admin/layout";
    }

    @PostMapping("/update-brand")
    public String updateBrand(@Valid BrandModel brandModel, BindingResult error,
                              @RequestParam("id") long id, Model model){
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }
        Brand brand = brandRepository.findById(id).get();
        brand.setName(brandModel.getName());
        brand.setStatus(brandModel.isStatus());
        String fileName = uploadService.uploadFile(brandModel.getLogo(), "brand");
        if (fileName == null){
            fileName = brandRepository.findById(id).get().getLogo();
        } else {
            uploadService.remove(brandRepository.findById(id).get().getLogo());
        }
        brand.setLogo(fileName);
        brandRepository.save(brand);
        return "redirect:/manage-brand";
    }

    @PostMapping("/delete-brand")
    public String deleteBrand(@RequestParam("id") long id) {
        Brand brand = brandRepository.findById(id).get();
        uploadService.remove(brandRepository.findById(id).get().getLogo());
        brandRepository.delete(brand);
        return "redirect:/manage-brand";
    }

    @GetMapping("/clear-formBrand")
    public String clearForm() {
        return "redirect:/manage-brand";
    }
}
