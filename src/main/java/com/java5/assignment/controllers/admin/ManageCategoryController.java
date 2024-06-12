package com.java5.assignment.controllers.admin;


import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;

import com.java5.assignment.entities.Brand;
import com.java5.assignment.entities.Category;
import com.java5.assignment.jpa.CategoryRepository;
import com.java5.assignment.model.CategoryModel;
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

    @PostMapping("/add-category")
    public String addCategory(@Valid CategoryModel categoryModel, BindingResult error, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }
        Category category = new Category();
        category.setName(categoryModel.getName());
        category.setDescription(categoryModel.getDescription());
        categoryRepository.save(category);
        return "redirect:/manage-category";
    }

    @PostMapping("/edit-category")
    public String editCategory(@RequestParam("id") long id, Model model) {
        Category category = categoryRepository.findById(id).get();
        model.addAttribute("category", category);
        return "admin/layout";
    }

    @PostMapping("/update-category")
    public String updateCategory(@Valid CategoryModel categoryModel, BindingResult error,
                                 @RequestParam("id") long id, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }
        Category category = categoryRepository.findById(id).get();
        category.setName(categoryModel.getName());
        category.setDescription(categoryModel.getDescription());
        categoryRepository.save(category);
        return "redirect:/manage-category";
    }

    @PostMapping("/delete-category")
    public String deleteCategory(@RequestParam("id") long id) {
        Category category = categoryRepository.findById(id).get();
        categoryRepository.delete(category);
        return "redirect:/manage-category";
    }

    @GetMapping("/clear-formCategory")
    public String clearForm() {
        return "redirect:/manage-category";
    }
}
