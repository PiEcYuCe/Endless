package com.java5.assignment.controllers.admin;

import com.java5.assignment.jpa.RatingPictureRepository;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import com.java5.assignment.entities.Rating;
import com.java5.assignment.jpa.RatingRepository;
import com.java5.assignment.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

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
    public List<Rating> ratings(@RequestParam(defaultValue = "ratingValue") String sortBy,
                                @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        return ratingRepository.findAll(sort);
    }

    @GetMapping("/manage-rating")
    public String get(@RequestParam(defaultValue = "ratingValue") String sortBy,
                      @RequestParam(defaultValue = "asc") String sortOrder,
                      Model model) {
        if (!authService.isLogin() || !authService.isAdmin() || !authService.isStatus()) {
            return "redirect:/logout";
        }
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);
        return "admin/layout";
    }
}
