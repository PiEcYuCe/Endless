package com.java5.assignment.controllers.share;

import com.java5.assignment.dto.ChangePasswordDTO;
import com.java5.assignment.dto.UserDto;
import com.java5.assignment.entities.User;
import com.java5.assignment.jpa.UserRepository;
import com.java5.assignment.services.EncodeService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class ChangePassController {
    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.CHANGE_PASSWORD);
    }

    @Autowired
    HttpSession session;

    @Autowired
    EncodeService encode;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/change-password")
    public String goToPage() {
        return "client/index";
    }

    @PostMapping("/update-password")
    public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String confirmPassword, Model model) {
        UserDto userDto = (UserDto)session.getAttribute("user");
        User user = userRepository.findById(userDto.getId()).get();

        if(encode.checkCode(oldPassword, user.getPassword())) {
            if(newPassword.equals(confirmPassword)) {
                user.setPassword(encode.hashCode(newPassword));
                userRepository.save(user);
                model.addAttribute("staus", "Update password successfully!!");
            }
            else{
                model.addAttribute("staus", "Confirm password is incorrect!");
            }
        }
        else{
            model.addAttribute("staus", "Old password is incorrect!");
        }
        return "client/index";

    }


}
