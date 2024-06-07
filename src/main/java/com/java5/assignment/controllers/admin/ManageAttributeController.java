package com.java5.assignment.controllers.admin;

import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ManageAttributeController {
    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_ATTRIBUTE);
    }

    @GetMapping("/manage-attribute")
    public String get() {
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


}

