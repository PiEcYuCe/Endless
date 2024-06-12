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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
        return attributeRepository.findAll();
    }


    @GetMapping("/manage-attribute")
    public String get(Model model) {
        return "admin/layout";
    }

    @PostMapping("/manage-add-attribute")
    public String addVoucher(@Valid AtributeModel atributeModel, BindingResult error, Model model) {

        if (attributeRepository.existsByAttributeName(atributeModel.getAttributeName())) {
            error.addError(new FieldError("atributeModel", "attributeName", "Attribute name already exists"));
        }
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

    @PostMapping("/manage-edit-attribute")
    public String editVoucher(@RequestParam("id") long id, Model model) {
        Attribute attribute = attributeRepository.findById(id).get();
        model.addAttribute("attribute", attribute);
        return "admin/layout";

    }

    @PostMapping("/manage-update-attribute")
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

    @PostMapping("/manage-delete-attribute")
    public String removeVoucher(@RequestParam("id") long id) {
        Attribute attribute = attributeRepository.findById(id).get();
        attributeRepository.delete(attribute);
        return "redirect:/manage-attribute";
    }

    @GetMapping("/manage-clear-attribute")
    public String clearForm() {
        return "redirect:/manage-attribute";
    }


}

