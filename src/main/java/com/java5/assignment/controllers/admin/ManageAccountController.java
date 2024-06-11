package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.Promotion;
import com.java5.assignment.entities.User;
import com.java5.assignment.jpa.UserRepository;
import com.java5.assignment.model.PromotionModel;
import com.java5.assignment.services.EncodeService;
import com.java5.assignment.services.UploadService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import com.java5.assignment.model.UserModel;
import com.java5.assignment.services.AuthService;
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
public class ManageAccountController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    EncodeService encode;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_ACCOUNT);
    }


    @ModelAttribute("accounts")
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @GetMapping("/manage-account")
    public String get() {
        if (!authService.isLogin() || !authService.isAdmin() || !authService.isStatus()) {
            return "redirect:/logout";
        }
        return "admin/layout";
    }


    @PostMapping("/add-account")
    public String addVoucher(@Valid UserModel userModel, BindingResult error, Model model) {
        String fileName = uploadService.uploadFile(userModel.getAvatar(), "user");
        if (fileName == null) {
            error.addError(new FieldError("account", "avatar", "Please select a image"));
        }
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }

        User user = new User();
        user.setUsername(userModel.getUsername());
        String password = encode.hashCode(userModel.getPassword());
        user.setPassword(password);
        user.setEmail(userModel.getEmail());
        user.setFullname(userModel.getFullName());
        user.setPhone(userModel.getPhone());
        user.setStatus(userModel.isStatus());
        user.setAddress(userModel.getAddress());
        user.setRole(userModel.isRole());
        user.setAvatar(fileName);


        userRepository.save(user);

        return "redirect:/manage-account";
    }

    @PostMapping("/edit-account")
    public String editBrand(@RequestParam("id") long id, Model model) {
        User user = userRepository.findById(id).get();
        user.setPassword(null);
        model.addAttribute("user", user);


        return "admin/layout";
    }

    @PostMapping("/update-account")
    public String updateBrand(@Valid UserModel userModel, BindingResult error,
                              @RequestParam("id") long id, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }


        User user = userRepository.findById(id).get();

        user.setUsername(userModel.getUsername());

        String password = userModel.getPassword() == null ? user.getPassword() : encode.hashCode(userModel.getPassword());
        user.setPassword(password);
        user.setEmail(userModel.getEmail());
        user.setFullname(userModel.getFullName());
        user.setPhone(userModel.getPhone());
        user.setStatus(userModel.isStatus());
        user.setAddress(userModel.getAddress());
        user.setRole(userModel.isRole());


        String fileName = uploadService.uploadFile(userModel.getAvatar(), "user");
        if (fileName == null) {
            fileName = userRepository.findById(id).get().getAvatar();
        } else {
            uploadService.remove(userRepository.findById(id).get().getAvatar());
        }
        user.setAvatar(fileName);

        userRepository.save(user);
        return "redirect:/manage-account";
    }

    @PostMapping("/delete-account")
    public String deleteBrand(@RequestParam("id") long id) {
        User user = userRepository.findById(id).get();
        uploadService.remove(userRepository.findById(id).get().getAvatar());
        userRepository.delete(user);
        return "redirect:/manage-account";
    }

    @GetMapping("/clear-form-user")
    public String clearForm() {
        return "redirect:/manage-account";
    }

}
