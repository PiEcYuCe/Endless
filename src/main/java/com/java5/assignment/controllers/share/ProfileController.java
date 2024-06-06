package com.java5.assignment.controllers.share;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import com.java5.assignment.dto.UserDto;
import com.java5.assignment.entities.User;
import com.java5.assignment.jpa.UserRepository;
import com.java5.assignment.model.UserModel;
import com.java5.assignment.services.AuthService;
import com.java5.assignment.services.CookieService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {
    @Autowired
    HttpSession session;

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private CookieService cookieService;

    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.PROFILE);
    }

    @GetMapping("/profile")
    public String goToPage(Model model) {
        if(authService.isLogin()&&authService.isStatus()) {
            UserDto userDto = (UserDto)session.getAttribute("user");
            User user = userRepository.findById(userDto.getId()).get();
            model.addAttribute("user", user);
        }
        else{
            return "redirect:/logout";
        }
        return "client/index";
    }

    @PostMapping("/profile")
    public String profile(@Valid UserModel userModel, BindingResult error, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
        }
        model.addAttribute("user", userModel);
        return "client/index";
    }
}
