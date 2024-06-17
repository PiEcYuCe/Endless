package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.Brand;
import com.java5.assignment.entities.Promotion;
import com.java5.assignment.entities.User;
import com.java5.assignment.jpa.OrderRepository;
import com.java5.assignment.jpa.UserRepository;
import com.java5.assignment.model.PromotionModel;
import com.java5.assignment.services.EncodeService;
import com.java5.assignment.services.UploadService;
import com.java5.assignment.utils.ErrorModal;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import com.java5.assignment.model.UserModel;
import com.java5.assignment.services.AuthService;
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
    public String addAccount(@Valid UserModel userModel, BindingResult errors, RedirectAttributes redirectAttributes) {
        if (userRepository.existsByUsername(userModel.getUsername())) {
            errors.addError(new FieldError("userModel", "username", "Username already exists"));
        }
        if (userRepository.existsByEmail(userModel.getEmail())) {
            errors.addError(new FieldError("userModel", "email", "Email already exists"));
        }
        if (userRepository.existsByPhone(userModel.getPhone())) {
            errors.addError(new FieldError("userModel", "phone", "Phone number already exists"));
        }

        String fileName = uploadService.uploadFile(userModel.getAvatar(), "user");
        if (fileName == null) {
            errors.addError(new FieldError("userModel", "avatar", "Please select an image"));
        }

        if (userModel.getPassword() == null || userModel.getPassword().isEmpty()) {
            errors.addError(new FieldError("userModel", "password", "Please enter a password"));
        } else {
            if (userModel.getPassword().length() < 8) {
                errors.addError(new FieldError("userModel", "password", "Password must be at least 8 characters long"));
            }
            if (!userModel.getPassword().matches("^(?=.*[a-z]).*$")) {
                errors.addError(new FieldError("userModel", "password", "Password must contain at least one lowercase letter"));
            }
            if (!userModel.getPassword().matches("^(?=.*[A-Z]).*$")) {
                errors.addError(new FieldError("userModel", "password", "Password must contain at least one uppercase letter"));
            }
            if (!userModel.getPassword().matches("^(?=.*\\d).*$")) {
                errors.addError(new FieldError("userModel", "password", "Password must contain at least one digit"));
            }
            if (!userModel.getPassword().matches("^(?=.*[@$!%*?&]).*$")) {
                errors.addError(new FieldError("userModel", "password", "Password must contain at least one special character"));
            }
        }

        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Unable to add account. Please check the form and try again."));
            return "redirect:/manage-account";
        }

        User user = new User();
        user.setUsername(userModel.getUsername());
        String hashedPassword = encode.hashCode(userModel.getPassword());
        user.setPassword(hashedPassword);
        user.setEmail(userModel.getEmail());
        user.setFullname(userModel.getFullName());
        user.setPhone(userModel.getPhone());
        user.setStatus(userModel.isStatus());
        user.setAddress(userModel.getAddress());
        user.setRole(userModel.isRole());
        user.setAvatar(fileName);

        userRepository.save(user);

        redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Account added successfully!"));
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
    public String updateAccount(@Valid UserModel userModel, BindingResult errors,
                                @RequestParam("id") long id, RedirectAttributes redirectAttributes) {

        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            errors.addError(new FieldError("userModel", "id", "User not found"));
        } else {
            if (!existingUser.getUsername().equals(userModel.getUsername()) && userRepository.existsByUsername(userModel.getUsername())) {
                errors.addError(new FieldError("userModel", "username", "Username already exists"));
            }
            if (!existingUser.getEmail().equals(userModel.getEmail()) && userRepository.existsByEmail(userModel.getEmail())) {
                errors.addError(new FieldError("userModel", "email", "Email already exists"));
            }
            if (!existingUser.getPhone().equals(userModel.getPhone()) && userRepository.existsByPhone(userModel.getPhone())) {
                errors.addError(new FieldError("userModel", "phone", "Phone number already exists"));
            }
        }
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: Unable to update account. Please check the form and try again."));
            return "redirect:/manage-account";
        }

        userModel.setRole(userModel.isRole());

        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
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
            if (fileName != null) {
                uploadService.remove(user.getAvatar());
                user.setAvatar(fileName);
            }

            userRepository.save(user);
            redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Account updated successfully!"));
        } else {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Error: User not found."));
        }

        return "redirect:/manage-account";
    }


    @PostMapping("/manage-delete-account")
    public String deleteAccount(@RequestParam("id") long id, RedirectAttributes redirectAttributes) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Account not found."));
        } else if (Boolean.TRUE.equals(user.getStatus())) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Cannot delete active accounts."));
        } else if (Boolean.TRUE.equals(user.getRole())) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Admin accounts cannot be deleted."));
        } else if (orderRepository.existsByUserID(user)) {
            redirectAttributes.addFlashAttribute("errorModal", new ErrorModal("Cannot delete accounts with orders."));
        } else {
            uploadService.remove(user.getAvatar());
            userRepository.delete(user);
            redirectAttributes.addFlashAttribute("successModal", new SuccessModal("Account deleted successfully."));
        }

        return "redirect:/manage-account";
    }



    @GetMapping("/manage-clear-form-user")
    public String clearForm() {
        return "redirect:/manage-account";
    }

}
