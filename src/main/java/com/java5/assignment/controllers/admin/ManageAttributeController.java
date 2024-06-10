package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.Attribute;
import com.java5.assignment.entities.AttributeValue;
import com.java5.assignment.entities.Product;
import com.java5.assignment.entities.Voucher;
import com.java5.assignment.jpa.AttributeRepository;
import com.java5.assignment.jpa.AttributeValueRepository;
import com.java5.assignment.model.ProductModel;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ManageAttributeController {

    @Autowired
    AttributeRepository attributeRepository;

    @Autowired
    AttributeValueRepository attributeValueRepository;


    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_ATTRIBUTE);
    }


    @ModelAttribute("attributes")
    public List<Attribute> getAttr() {
        return attributeRepository.findAll();
    }

    @ModelAttribute("attributeValues")
    public List<AttributeValue> getAttrValue() {
        return attributeValueRepository.findAll();
    }


    @GetMapping("/manage-attribute")
    public String get(Model model) {
        return "admin/layout";
    }


    @PostMapping("/manage-attribute")
    public String post(@RequestParam("attributeName") String attributeName,
                       @RequestParam("attributeNote") String attributeNote,
                       @RequestParam("attributeValue") String attributeValue,
                       RedirectAttributes redirectAttributes) {
        if (attributeName.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please fill in the attribute name");
        }
        if (attributeNote.isEmpty()) {
            redirectAttributes.addFlashAttribute("error1", "Please fill in the attribute Note");
        }
        if (attributeValue.isEmpty()) {
            redirectAttributes.addFlashAttribute("error2", "Please fill in the attribute Value");
        }
        // Xử lý dữ liệu nếu không có lỗi
        return "redirect:/manage-attribute";

    }


    @PostMapping("/add-attribute")
    public String addAttr(@RequestParam("attributeName") String attributeName,
                          @RequestParam("attributeNote") String attributeNote,
                          @RequestParam("attributeValue") String attributeValue,
                          RedirectAttributes redirectAttributes) {
        if (attributeName.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please fill in the attribute name");
        }
        if (attributeNote.isEmpty()) {
            redirectAttributes.addFlashAttribute("error1", "Please fill in the attribute Note");
        }
        if (attributeValue.isEmpty()) {
            redirectAttributes.addFlashAttribute("error2", "Please fill in the attribute Value");
        }
        return "redirect:/manage-attribute";

    }
}

