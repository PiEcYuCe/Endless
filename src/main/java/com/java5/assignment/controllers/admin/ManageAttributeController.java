package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.Attribute;
import com.java5.assignment.jpa.AttributeRepository;
import com.java5.assignment.model.AtributeModel;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/manage-add-attribute")
    public String addAttribute(@Valid AtributeModel attributeModel, BindingResult errors,
                               RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Unable to add attribute. Please check the form and try again."));
            return "redirect:/manage-attribute";
        }

        if (attributeRepository.existsByAttributeName(attributeModel.getAttributeName())) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Attribute name already exists."));
            return "redirect:/manage-attribute";
        }

        Attribute attribute = new Attribute();
        attribute.setAttributeName(attributeModel.getAttributeName());
        attribute.setAttributeNote(attributeModel.getAttributeNote());

        attributeRepository.save(attribute);

        redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Attribute added successfully!"));
        return "redirect:/manage-attribute";
    }



    @PostMapping("/manage-edit-attribute")
    public String editVoucher(@RequestParam("id") long id, Model model) {
        Attribute attribute = attributeRepository.findById(id).get();
        model.addAttribute("attribute", attribute);
        return "admin/layout";

    }

    @PostMapping("/manage-update-attribute")
    public String updateAttribute(@Valid AtributeModel attributeModel, BindingResult errors,
                                  RedirectAttributes redirectAttributes, @RequestParam("id") long id) {
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Unable to update attribute. Please check the form and try again."));
            return "redirect:/manage-attribute";
        }

        Attribute attribute = attributeRepository.findById(id).orElse(null);
        if (attribute == null) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Attribute not found."));
            return "redirect:/manage-attribute";
        }

        if (!attribute.getAttributeName().equals(attributeModel.getAttributeName()) &&
                attributeRepository.existsByAttributeName(attributeModel.getAttributeName())) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Attribute name already exists."));
            return "redirect:/manage-attribute";
        }

        attribute.setAttributeName(attributeModel.getAttributeName());
        attribute.setAttributeNote(attributeModel.getAttributeNote());

        attributeRepository.save(attribute);

        redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Attribute updated successfully!"));
        return "redirect:/manage-attribute";
    }


    @PostMapping("/manage-delete-attribute")
    public String removeAttribute(@RequestParam("id") long id, RedirectAttributes redirectAttributes) {
        Attribute attribute = attributeRepository.findById(id).orElse(null);
        if (attribute == null) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Attribute not found."));
            return "redirect:/manage-attribute";
        }

        attributeRepository.delete(attribute);

        redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Attribute deleted successfully!"));
        return "redirect:/manage-attribute";
    }


    @GetMapping("/manage-clear-attribute")
    public String clearForm() {
        return "redirect:/manage-attribute";
    }


}

