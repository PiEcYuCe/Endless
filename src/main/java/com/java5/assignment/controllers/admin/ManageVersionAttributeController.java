package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.Attribute;
import com.java5.assignment.entities.AttributeValue;
import com.java5.assignment.entities.ProductVersion;
import com.java5.assignment.entities.VersionAttribute;
import com.java5.assignment.jpa.AttributeValueRepository;
import com.java5.assignment.jpa.ProductVersionRepository;
import com.java5.assignment.jpa.VersionAttributeRepository;
import com.java5.assignment.model.VersionAttributeModel;
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
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ManageVersionAttributeController {
    @Autowired
    ProductVersionRepository productVersionRepository;

    @Autowired
    AttributeValueRepository attributeValueRepository;

    @Autowired
    VersionAttributeRepository versionAttributeRepository;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_VERSION_ATTRIBUTE);
    }

    @GetMapping("/manage-version-attribute")
    public String get() {
        return "admin/layout";
    }

    @ModelAttribute("productVersions")
    public List<ProductVersion> getProductVersions() {
        return productVersionRepository.findAll();
    }

    @ModelAttribute("attributeValues")
    public List<AttributeValue> getAttributeValues() {
        return attributeValueRepository.findAll();
    }

    @ModelAttribute("versionAttributes")
    public List<VersionAttribute> getVersionAttributes() {
        return versionAttributeRepository.findAllByOrderByIdDesc();

    }





    @PostMapping("/manage-add-version-attribute")
    public String addVersionAttribute(@Valid VersionAttributeModel versionAttributeModel, BindingResult error, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }

        VersionAttribute versionAttribute = new VersionAttribute();
        versionAttribute.setProductVersionID(productVersionRepository.findById(versionAttributeModel.getProductVersionID()));
        versionAttribute.setAttributeValueID(attributeValueRepository.findById(versionAttributeModel.getAttributeValueID()).get());

        versionAttributeRepository.save(versionAttribute);

        return "redirect:/manage-version-attribute";
    }

    @PostMapping("/manage-edit-version-attribute")
    public String editVersionAttribute(@RequestParam("id") long id, Model model) {
        VersionAttribute versionAttribute = versionAttributeRepository.findById(id).get();
        model.addAttribute("versionAttribute", versionAttribute);
        return "admin/layout";
    }

    @PostMapping("/manage-update-version-attribute")
    public String updateVersionAttribute(@Valid VersionAttributeModel versionAttributeModel, BindingResult error, Model model,
                                         @RequestParam("id") long id) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }


        VersionAttribute versionAttribute = new VersionAttribute();
        versionAttribute.setProductVersionID(productVersionRepository.findById(versionAttributeModel.getProductVersionID()));
        versionAttribute.setAttributeValueID(attributeValueRepository.findById(versionAttributeModel.getAttributeValueID()).get());

        versionAttributeRepository.save(versionAttribute);
        return "redirect:/manage-version-attribute";
    }

    @PostMapping("/manage-delete-version-attribute")
    public String removeVersionAttribute(@RequestParam("id") long id) {
        VersionAttribute versionAttribute = versionAttributeRepository.findById(id).get();
        versionAttributeRepository.delete(versionAttribute);
        return "redirect:/manage-version-attribute";
    }

    @GetMapping("/manage-clear-version-attribute")
    public String clearForm() {
        return "redirect:/manage-version-attribute";
    }

}
