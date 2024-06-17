package com.java5.assignment.controllers.admin;


import com.java5.assignment.utils.ErrorModal;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;

import com.java5.assignment.entities.Brand;
import com.java5.assignment.entities.Category;
import com.java5.assignment.jpa.CategoryRepository;
import com.java5.assignment.model.CategoryModel;
import com.java5.assignment.utils.SuccessModal;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ManageCategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_CATEGORY);
    }

    @ModelAttribute("Categories")
    public List<Category> getCategories() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return categoryRepository.findAll(sort);
    }

    @GetMapping("/manage-category")
    public String get() {
        return "admin/layout";
    }

    @PostMapping("/manage-add-category")
    public String addCategory(@Valid CategoryModel categoryModel, BindingResult error, RedirectAttributes redirectAttributes) {
        if (error.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Unable to add category. Please check the form and try again."));
            return "redirect:/manage-category";
        }

        if (categoryRepository.existsByName(categoryModel.getName())) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Category name already exists."));
            return "redirect:/manage-category";
        }

        Category category = new Category();
        category.setName(categoryModel.getName());
        category.setDescription(categoryModel.getDescription());
        categoryRepository.save(category);

        redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Category added successfully!"));
        return "redirect:/manage-category";
    }


    @PostMapping("/manage-edit-category")
    public String editCategory(@RequestParam("id") long id, Model model) {
        Category category = categoryRepository.findById(id).get();
        model.addAttribute("category", category);
        return "admin/layout";
    }

    @PostMapping("/manage-update-category")
    public String updateCategory(@Valid CategoryModel categoryModel, BindingResult error,
                                 @RequestParam("id") long id, RedirectAttributes redirectAttributes) {
        if (error.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Unable to update category. Please check the form and try again."));
            return "redirect:/manage-category";
        }

        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Category not found."));
            return "redirect:/manage-category";
        }

        if (!category.getName().equals(categoryModel.getName()) && categoryRepository.existsByName(categoryModel.getName())) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Category name already exists."));
            return "redirect:/manage-category";
        }

        category.setName(categoryModel.getName());
        category.setDescription(categoryModel.getDescription());
        categoryRepository.save(category);

        redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Category updated successfully!"));
        return "redirect:/manage-category";
    }


    @PostMapping("/manage-delete-category")
    public String deleteCategory(@RequestParam("id") long id, RedirectAttributes redirectAttributes) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Category not found."));
            return "redirect:/manage-category";
        }

        if (!category.getProducts().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Cannot delete category with associated products."));
            return "redirect:/manage-category";
        }

        categoryRepository.delete(category);
        redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Category deleted successfully!"));
        return "redirect:/manage-category";
    }


    @GetMapping("/manage-clear-formCategory")
    public String clearForm() {
        return "redirect:/manage-category";
    }
}
