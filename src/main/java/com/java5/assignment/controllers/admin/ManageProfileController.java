package com.java5.assignment.controllers.admin;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import com.java5.assignment.entities.User;
import com.java5.assignment.jpa.UserRepository;
import com.java5.assignment.model.user.UpdateProfileModel;
import com.java5.assignment.services.UploadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManageProfileController {

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.PROFILE);
    }

    @GetMapping("/manage-profile")
    public String get() {
        return "admin/layout";
    }

}
