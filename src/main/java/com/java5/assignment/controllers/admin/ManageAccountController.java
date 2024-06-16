package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.Promotion;
import com.java5.assignment.entities.User;
import com.java5.assignment.jpa.OrderRepository;
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
public class ManageAccountController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    EncodeService encode;
    @Autowired
    private OrderRepository orderRepository;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_ACCOUNT);
    }


    @ModelAttribute("accounts")
    public List<User> getUser() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return userRepository.findAll(sort);
    }

    @GetMapping("/manage-account")
    public String get() {
        if (!authService.isLogin() || !authService.isAdmin() || !authService.isStatus()) {
            return "redirect:/logout";
        }
        return "admin/layout";
    }


    @PostMapping("/manage-add-account")
    public String addAccount(@Valid UserModel userModel, BindingResult error, Model model, RedirectAttributes redirectAttributes) {

        if (userRepository.existsByUsername(userModel.getUsername())) {
            error.addError(new FieldError("userModel", "username", "Username already exists"));
        }
        if (userRepository.existsByEmail(userModel.getEmail())) {
            error.addError(new FieldError("userModel", "email", "Email already exists"));
        }
        if (userRepository.existsByPhone(userModel.getPhone())) {
            error.addError(new FieldError("userModel", "phone", "Phone number already exists"));
        }

        String fileName = uploadService.uploadFile(userModel.getAvatar(), "user");
        if (fileName == null) {
            error.addError(new FieldError("account", "avatar", "Please select a image"));
        }
        // Validate password manually
        if (userModel.getPassword() == null || userModel.getPassword().isEmpty()) {
            error.addError(new FieldError("userModel", "password", "Please enter password here!"));
        } else {
            if (userModel.getPassword().length() < 8) {
                error.addError(new FieldError("userModel", "password", "Password must be at least 8 characters long"));
            }
            if (!userModel.getPassword().matches("^(?=.*[a-z]).*$")) {
                error.addError(new FieldError("userModel", "password", "Password must contain at least one lowercase letter"));
            }
            if (!userModel.getPassword().matches("^(?=.*[A-Z]).*$")) {
                error.addError(new FieldError("userModel", "password", "Password must contain at least one uppercase letter"));
            }
            if (!userModel.getPassword().matches("^(?=.*\\d).*$")) {
                error.addError(new FieldError("userModel", "password", "Password must contain at least one digit"));
            }
            if (!userModel.getPassword().matches("^(?=.*[@$!%*?&]).*$")) {
                error.addError(new FieldError("userModel", "password", "Password must contain at least one special character"));
            }
        }
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            redirectAttributes.addFlashAttribute("message", "Error: unable to add account!");
            redirectAttributes.addFlashAttribute("messageType", "danger");
            return "admin/layout";
        }

        userModel.setRole(userModel.isRole());

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

        redirectAttributes.addFlashAttribute("message", "Account added successfully");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/manage-account";
    }

    @PostMapping("/manage-edit-account")
    public String editBrand(@RequestParam("id") long id, Model model) {
        User user = userRepository.findById(id).get();
        if (user == null) {
            return "redirect:/manage-account";
        }
        user.setPassword(null);
        model.addAttribute("user", user);


        return "admin/layout";
    }

    @PostMapping("/manage-update-account")
    public String updateAccount(@Valid UserModel userModel, BindingResult error,
                              @RequestParam("id") long id, Model model, RedirectAttributes redirectAttributes) {

        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            error.addError(new FieldError("userModel", "id", "User not found"));
        } else {
            if (!existingUser.getUsername().equals(userModel.getUsername()) && userRepository.existsByUsername(userModel.getUsername())) {
                error.addError(new FieldError("userModel", "username", "Username already exists"));
            }
            if (!existingUser.getEmail().equals(userModel.getEmail()) && userRepository.existsByEmail(userModel.getEmail())) {
                error.addError(new FieldError("userModel", "email", "Email already exists"));
            }
            if (!existingUser.getPhone().equals(userModel.getPhone()) && userRepository.existsByPhone(userModel.getPhone())) {
                error.addError(new FieldError("userModel", "phone", "Phone number already exists"));
            }
        }
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            redirectAttributes.addFlashAttribute("message", "Error: unable to update account!");
            redirectAttributes.addFlashAttribute("messageType", "danger");
            return "admin/layout";
        }

        userModel.setRole(userModel.isRole());

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
        redirectAttributes.addFlashAttribute("message", "Account updated successfully!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/manage-account";
    }

    @PostMapping("/manage-delete-account")
    public String deleteAccount(@RequestParam("id") long id, RedirectAttributes redirectAttributes) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "Account not found.");
            redirectAttributes.addFlashAttribute("messageType", "warning");
        } else if (Boolean.TRUE.equals(user.getStatus())) {
            redirectAttributes.addFlashAttribute("message", "Cannot delete active accounts.");
            redirectAttributes.addFlashAttribute("messageType", "danger");
        } else if (Boolean.TRUE.equals(user.getRole())) {
            redirectAttributes.addFlashAttribute("message", "Admin accounts cannot be deleted.");
            redirectAttributes.addFlashAttribute("messageType", "danger");
        } else if (orderRepository.existsByUserID(user)) {
            redirectAttributes.addFlashAttribute("message", "Cannot delete accounts with orders.");
            redirectAttributes.addFlashAttribute("messageType", "danger");
        } else {
            uploadService.remove(user.getAvatar());
            userRepository.delete(user);
            redirectAttributes.addFlashAttribute("message", "Account deleted successfully.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        }
        return "redirect:/manage-account";
    }


    @GetMapping("/manage-clear-form-user")
    public String clearForm() {
        return "redirect:/manage-account";
    }

}
