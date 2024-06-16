package com.java5.assignment.controllers.share;

import com.java5.assignment.entities.User;
import com.java5.assignment.jpa.UserRepository;
import com.java5.assignment.services.EmailService;
import com.java5.assignment.services.EncodeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Controller
public class ForgotPasswordController {

    @Autowired
    HttpSession session;

    @Autowired
    EncodeService encode;

    @Autowired
    UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EncodeService encodeService;


    @GetMapping("/forget-password")
    public String forgetPassword(Model model) {
        return "public/send-mail";
    }

//    @PostMapping("/send-mail")
//    public String forgotPassword(@RequestParam String email, Model model) {
//        // Tạo mã OTP ngẫu nhiên và gửi đi
//        String otp = generateOTP(); // Phần này bạn cần triển khai generateOTP() như đã đề cập ở trên
//
//        // Lưu thông tin vào session để sử dụng sau này
//        session.setAttribute("resetEmail", email);
//        session.setAttribute("resetOTP", otp);
//
//        // Gửi email chứa OTP
//        emailService.sendOTPEmail(email, otp);
//
//        model.addAttribute("status", "OTP sent successfully!");
//
//        return "public/forgot-password";
//    }
//
//    @PostMapping("/forgot-password")
//    public String changePassword(@RequestParam String otp, @RequestParam String newPassword, Model model) {
//        String resetEmail = (String) session.getAttribute("resetEmail");
//        String resetOTP = (String) session.getAttribute("resetOTP");
//
//        if (resetEmail == null || resetOTP == null || !resetOTP.equals(otp)) {
//            model.addAttribute("status", "Invalid OTP. Please enter the correct OTP.");
//            return "public/forgot-password";
//        }
//
//        User user = userRepository.findByEmail(resetEmail);
//        if (user == null) {
//            model.addAttribute("status", "User not found.");
//            return "public/forgot-password";
//        }
//
//        // Update mật khẩu
//        user.setPassword(encodeService.hashCode(newPassword)); // Sử dụng encodeService để mã hóa mật khẩu mới
//        userRepository.save(user);
//
//        model.addAttribute("status", "Password updated successfully!");
//
//        // Xóa các session attribute đã sử dụng
//        session.removeAttribute("resetEmail");
//        session.removeAttribute("resetOTP");
//
//        return "client/index";
//    }
//
//    // Phương thức sinh mã OTP ngẫu nhiên
//    private String createOTP() {
//        Random random = new Random();
//        String otp = String.format("%06d", random.nextInt(999999));
//        return otp;
//    }
}
