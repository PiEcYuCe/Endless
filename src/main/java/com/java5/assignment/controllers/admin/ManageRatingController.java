package com.java5.assignment.controllers.admin;

import com.java5.assignment.jpa.RatingPictureRepository;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import com.java5.assignment.entities.Rating;
import com.java5.assignment.jpa.RatingRepository;
import com.java5.assignment.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class ManageRatingController {

    @Autowired
    AuthService authService;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    RatingPictureRepository RatingPictureRepository;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_RATING);
    }

    @ModelAttribute("Ratings")
    public List<Rating> ratings() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratingRepository.findAll();
    }

    @GetMapping("/manage-rating")
    public String get() {
        if(!authService.isLogin() || !authService.isAdmin() || !authService.isStatus()) {
            return "redirect:/logout";
        }
        return "admin/layout";
    }
}
