package com.java5.assignment.controllers.share;

import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import com.java5.assignment.entities.User;
import com.java5.assignment.jpa.UserRepository;
import com.java5.assignment.model.share.login.RegisterModel;
import com.java5.assignment.services.EncodeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EncodeService encode;

    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.LOGIN);
    }

    @GetMapping("/sign-up")
    public String goToPage() {
        return "public/login";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid RegisterModel registerModel, BindingResult error, Model model) {
        if(!error.hasErrors()) {
            if(userRepository.findByUsername(registerModel.getUsername()) != null) {
                error.addError(new FieldError("registerModel", "username", "Username is already in use, please choose another one"));
                registerModel.setUsername("");
            }
            if(userRepository.findByEmail(registerModel.getEmail()) != null) {
                error.addError(new FieldError("registerModel", "email", "Email is already in use, please choose another one"));
                registerModel.setEmail("");
            }
            if(!registerModel.getPassword().equalsIgnoreCase(registerModel.getRepeatPassword())) {
                error.addError(new FieldError("registerModel", "password", "Passwords do not match"));
                registerModel.setPassword("");
                registerModel.setRepeatPassword("");
            }
        }
        if(error.hasErrors()) {
            model.addAttribute("registerError", error);
            for(FieldError err : error.getFieldErrors()){
                if(err.getField().contentEquals("username")){
                    registerModel.setUsername("");
                }
                if(err.getField().contentEquals("email")){
                    registerModel.setEmail("");
                }
                if(err.getField().contentEquals("password")){
                    registerModel.setPassword("");
                }
                if(err.getField().contentEquals("repeatPassword")){
                    registerModel.setRepeatPassword("");
                }
            }
            model.addAttribute("register", registerModel);
            model.addAttribute("page", "register");
            return "public/login";
        }
        else{
            User newUser = new User();
            newUser.setUsername(registerModel.getUsername());
            newUser.setEmail(registerModel.getEmail());
            newUser.setPassword(encode.hashCode(registerModel.getPassword()));
            newUser.setRole(false);
            newUser.setStatus(true);
            userRepository.save(newUser);
        }
        return "public/login";
    }

}
