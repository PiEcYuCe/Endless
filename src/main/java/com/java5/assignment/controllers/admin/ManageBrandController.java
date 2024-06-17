package com.java5.assignment.controllers.admin;


import com.java5.assignment.utils.ErrorModal;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;

import com.java5.assignment.entities.Brand;
import com.java5.assignment.jpa.BrandRepository;

import com.java5.assignment.model.BrandModel;
import com.java5.assignment.services.UploadService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

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
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return brandRepository.findAll(sort);
    }

    @GetMapping("/manage-brand")
    public String get() {
        return "admin/layout";
    }

    @PostMapping("/manage-add-brand")
    public String addBrand(@Valid BrandModel brandModel, BindingResult errors,
                           RedirectAttributes redirectAttributes) {
        if (brandRepository.existsByName(brandModel.getName())) {
            errors.rejectValue("name", "brand.name.exists", "Brand name already exists.");
        }

        String fileName = uploadService.uploadFile(brandModel.getLogo(), "brand");
        if (fileName == null) {
            errors.addError(new FieldError("brandModel", "logo", "Please select a logo"));
        }

        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Unable to create brand. Please check the form and try again."));
            return "redirect:/manage-brand";
        }

        Brand brand = new Brand();
        brand.setName(brandModel.getName());
        brand.setLogo(fileName);
        brand.setStatus(brandModel.isStatus());
        brandRepository.save(brand);

        redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Brand created successfully"));
        return "redirect:/manage-brand";
    }





    @PostMapping("/manage-edit-brand")
    public String editBrand(@RequestParam("id") long id, Model model) {
        Brand brand = brandRepository.findById(id).get();
        model.addAttribute("brand", brand);
        return "admin/layout";
    }

    @PostMapping("/manage-update-brand")
    public String updateBrand(@Valid BrandModel brandModel, BindingResult errors,
                              @RequestParam("id") long id, RedirectAttributes redirectAttributes) {
        Brand existingBrand = brandRepository.findById(id).orElse(null);
        if (existingBrand == null) {
            errors.addError(new FieldError("brandModel", "id", "Brand not found"));
        } else {
            if (!existingBrand.getName().equals(brandModel.getName()) && brandRepository.existsByName(brandModel.getName())) {
                errors.addError(new FieldError("brandModel", "name", "Brand name already exists"));
            }
        }

        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Unable to update brand. Please check the form and try again."));
            return "redirect:/manage-brand";
        }

        existingBrand.setName(brandModel.getName());
        existingBrand.setStatus(brandModel.isStatus());

        String fileName = uploadService.uploadFile(brandModel.getLogo(), "brand");
        if (fileName != null) {
            uploadService.remove(existingBrand.getLogo());
            existingBrand.setLogo(fileName);
        }

        brandRepository.save(existingBrand);
        redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Brand updated successfully"));
        return "redirect:/manage-brand";
    }


    @PostMapping("/manage-delete-brand")
    public String deleteBrand(@RequestParam("id") long id, RedirectAttributes redirectAttributes) {
        Brand brand = brandRepository.findById(id).orElse(null);

        if (brand == null) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Brand not found."));
        } else if (!brand.getProducts().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Cannot delete brand with associated products."));
        } else {
            uploadService.remove(brand.getLogo());
            brandRepository.delete(brand);
            redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Brand deleted successfully"));
        }

        return "redirect:/manage-brand";
    }

    @GetMapping("/manage-clear-formBrand")
    public String clearForm() {
        return "redirect:/manage-brand";
    }
}
