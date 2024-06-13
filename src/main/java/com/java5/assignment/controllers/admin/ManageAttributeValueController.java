package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.Attribute;
import com.java5.assignment.entities.AttributeValue;
import com.java5.assignment.jpa.AttributeRepository;
import com.java5.assignment.jpa.AttributeValueRepository;
import com.java5.assignment.model.AttributeValueModel;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
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
    public String addVoucher(@Valid AttributeValueModel attributeValueModel, BindingResult error, Model model) {
        if (attributeValueRepository.existsByValue(attributeValueModel.getValue())) {
            error.addError(new FieldError("attributeValueModel", "value", "Attribute value already exists"));
        }
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }

        AttributeValue attributeValue = new AttributeValue();
        attributeValue.setAttributeID(attributeRepository.findById(attributeValueModel.getAttributeID()).get());
        attributeValue.setValue(attributeValueModel.getValue());

        attributeValueRepository.save(attributeValue);

        return "redirect:/manage-attribute-value";
    }

    @PostMapping("/manage-edit-attribute-value")
    public String editVoucher(@RequestParam("id") long id, Model model) {
        AttributeValue attributeValue = attributeValueRepository.findById(id).get();
        model.addAttribute("attributeValue", attributeValue);
        return "admin/layout";

    }

    @PostMapping("/manage-update-attribute-value")
    public String updateVoucher(@Valid AttributeValueModel attributeValueModel, BindingResult error, Model model,
                                @RequestParam("id") long id) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";

        }
        AttributeValue attributeValue = new AttributeValue();
        attributeValue.setAttributeID(attributeRepository.findById(attributeValueModel.getAttributeID()).get());
        attributeValue.setValue(attributeValueModel.getValue());

        attributeValueRepository.save(attributeValue);
        return "redirect:/manage-attribute-value";
    }

    @PostMapping("/manage-delete-attribute-value")
    public String removeVoucher(@RequestParam("id") long id) {
        AttributeValue attributeValue = attributeValueRepository.findById(id).get();
        attributeValueRepository.delete(attributeValue);
        return "redirect:/manage-attribute-value";
    }

    @GetMapping("/manage-clear-attribute-value")
    public String clearForm() {
        return "redirect:/manage-attribute-value";
    }


}
