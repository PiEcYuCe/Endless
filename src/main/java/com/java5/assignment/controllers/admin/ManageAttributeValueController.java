package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.Attribute;
import com.java5.assignment.entities.AttributeValue;
import com.java5.assignment.jpa.AttributeRepository;
import com.java5.assignment.jpa.AttributeValueRepository;
import com.java5.assignment.model.AttributeValueModel;
import com.java5.assignment.utils.ErrorModal;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
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

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ManageAttributeValueController {

    @Autowired
    AttributeRepository attributeRepository;

    @Autowired
    AttributeValueRepository attributeValueRepository;


    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_ATTRIBUTE_VALUE);
    }

    @GetMapping("/manage-attribute-value")
    public String get() {
        return "admin/layout";
    }

    @ModelAttribute("attributes")
    public List<Attribute> getAttributes() {
        return attributeRepository.findAll();
    }

    @ModelAttribute("attributeValues")
    public List<AttributeValue> getAttributeValues() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return attributeValueRepository.findAll(sort);
    }


    @PostMapping("/manage-add-attribute-value")
    public String addAttributeValue(@Valid AttributeValueModel attributeValueModel, BindingResult errors,
                                    RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Unable to add attribute value. Please check the form and try again."));
            return "redirect:/manage-attribute-value";
        }

        if (attributeValueRepository.existsByValue(attributeValueModel.getValue())) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Attribute value already exists."));
            return "redirect:/manage-attribute-value";
        }

        AttributeValue attributeValue = new AttributeValue();
        attributeValue.setAttributeID(attributeRepository.findById(attributeValueModel.getAttributeID()).orElse(null));
        attributeValue.setValue(attributeValueModel.getValue());

        attributeValueRepository.save(attributeValue);

        redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Attribute value added successfully!"));
        return "redirect:/manage-attribute-value";
    }


    @PostMapping("/manage-edit-attribute-value")
    public String editVoucher(@RequestParam("id") long id, Model model) {
        AttributeValue attributeValue = attributeValueRepository.findById(id).get();
        model.addAttribute("attributeValue", attributeValue);
        return "admin/layout";

    }

    @PostMapping("/manage-update-attribute-value")
    public String updateAttributeValue(@Valid AttributeValueModel attributeValueModel, BindingResult errors,
                                       RedirectAttributes redirectAttributes, @RequestParam("id") long id) {
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Unable to update attribute value. Please check the form and try again."));
            return "redirect:/manage-attribute-value";
        }

        AttributeValue attributeValue = attributeValueRepository.findById(id).orElse(null);
        if (attributeValue == null) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Attribute value not found."));
            return "redirect:/manage-attribute-value";
        }

        if (!attributeValue.getValue().equals(attributeValueModel.getValue()) &&
                attributeValueRepository.existsByValue(attributeValueModel.getValue())) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Attribute value already exists."));
            return "redirect:/manage-attribute-value";
        }

        attributeValue.setValue(attributeValueModel.getValue());
        attributeValue.setAttributeID(attributeRepository.findById(attributeValueModel.getAttributeID()).orElse(null));

        attributeValueRepository.save(attributeValue);

        redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Attribute value updated successfully!"));
        return "redirect:/manage-attribute-value";
    }


    @PostMapping("/manage-delete-attribute-value")
    public String removeAttributeValue(@RequestParam("id") long id, RedirectAttributes redirectAttributes) {
        AttributeValue attributeValue = attributeValueRepository.findById(id).orElse(null);
        if (attributeValue == null) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Attribute value not found."));
        } else {
            attributeValueRepository.delete(attributeValue);
            redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Attribute value deleted successfully!"));
        }
        return "redirect:/manage-attribute-value";
    }


    @GetMapping("/manage-clear-attribute-value")
    public String clearForm() {
        return "redirect:/manage-attribute-value";
    }


}
