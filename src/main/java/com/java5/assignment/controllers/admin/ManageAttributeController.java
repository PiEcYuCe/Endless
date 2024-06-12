package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.Attribute;
import com.java5.assignment.jpa.AttributeRepository;
import com.java5.assignment.model.AtributeModel;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ManageAttributeController {

    @Autowired
    AttributeRepository attributeRepository;


    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_ATTRIBUTE);
    }


    @ModelAttribute("attributes")
    public List<Attribute> getAttr() {
        return attributeRepository.findAll()
                .stream()
                .sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
                .collect(Collectors.toList());
    }


    @GetMapping("/manage-attribute")
    public String get(Model model) {
        return "admin/layout";
    }

    @PostMapping("/add-attribute")
    public String addVoucher(@Valid AtributeModel atributeModel, BindingResult error, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }

        Attribute attribute = new Attribute();
        attribute.setAttributeName(atributeModel.getAttributeName());
        attribute.setAttributeNote(atributeModel.getAttributeNote());

        attributeRepository.save(attribute);

        return "redirect:/manage-attribute";
    }

    @PostMapping("/edit-attribute")
    public String editVoucher(@RequestParam("id") long id, Model model) {
        Attribute attribute = attributeRepository.findById(id).get();
        model.addAttribute("attribute", attribute);
        return "admin/layout";

    }

    @PostMapping("/update-attribute")
    public String updateVoucher(@Valid AtributeModel atributeModel, BindingResult error, Model model, @RequestParam("id") long id) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";

        }

        Attribute attribute = new Attribute();
        attribute.setAttributeName(atributeModel.getAttributeName());
        attribute.setAttributeNote(atributeModel.getAttributeNote());

        attributeRepository.save(attribute);
        return "redirect:/manage-attribute";
    }

    @PostMapping("/delete-attribute")
    public String removeVoucher(@RequestParam("id") long id) {
        Attribute attribute = attributeRepository.findById(id).get();
        attributeRepository.delete(attribute);
        return "redirect:/manage-attribute";
    }

    @GetMapping("/clear-attribute")
    public String clearForm() {
        return "redirect:/manage-attribute";
    }


}

