package com.java5.assignment.controllers.admin;

import com.java5.assignment.content.Page;
import com.java5.assignment.content.PageType;
import com.java5.assignment.entities.Rating;
import com.java5.assignment.jpa.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class ManageRatingController {

    @Autowired
    RatingRepository ratingRepository;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_RATING);
    }

    @ModelAttribute("Ratings")
    public List<Rating> ratings() {
        return ratingRepository.findAll();
    }

    @GetMapping("/manage-rating")
    public String get() {
        return "admin/layout";
    }

}
