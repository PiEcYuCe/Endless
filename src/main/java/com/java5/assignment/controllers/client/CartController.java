package com.java5.assignment.controllers.client;

import com.java5.assignment.dto.UserDto;
import com.java5.assignment.entities.Cart;
import com.java5.assignment.entities.ProductVersion;
import com.java5.assignment.entities.User;
import com.java5.assignment.jpa.CartRepository;
import com.java5.assignment.jpa.ProductVersionRepository;
import com.java5.assignment.jpa.UserRepository;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CartController {
    @Autowired
    HttpSession session;

    @Autowired
    CartRepository CartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductVersionRepository productVersionRepository;

    @Autowired
    HttpServletRequest req;

    @Autowired
    private CartRepository cartRepository;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.CART);
    }

    @GetMapping("/cart")
    public String get() {
        return "client/index";
    }

    @GetMapping("/add-to-cart")
    public String addToCart(@RequestParam(name="id")long id, @RequestParam(name = "quantity", defaultValue = "1") int quantity, Model model) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        User user = userRepository.findById(userDto.getId()).get();
        ProductVersion productVersion = productVersionRepository.findById(id);
        Cart cart = new Cart();
        cart.setUserID(user);
        cart.setQuantity(quantity);
        cart.setProductVersionID(productVersion);
        CartRepository.save(cart);
        String referer = req.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/home");
    }

}
