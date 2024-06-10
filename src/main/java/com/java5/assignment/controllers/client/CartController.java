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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {
    @Autowired
    HttpSession session;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductVersionRepository productVersionRepository;

    @Autowired
    HttpServletRequest req;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.CART);
    }

    @ModelAttribute("Carts")
    public List<Cart> carts() {
        UserDto userDto = (UserDto) session.getAttribute("user");
        User user = userRepository.findById(userDto.getId()).get();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return cartRepository.findByUserID(user, sort);
    }

    @GetMapping("/cart")
    public String get(Model model) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        User user = userRepository.findById(userDto.getId()).get();
        model.addAttribute("user", user);
        return "client/index";
    }


    @GetMapping("/update-cart")
    public String updateCart(@RequestParam int id, @RequestParam int quantityChange) {
        ArrayList<Cart> carts = (ArrayList<Cart>) session.getAttribute("carts");
        if (carts != null) {
            for (int indexCart = 0; indexCart < carts.size(); indexCart++) {
                if (carts.get(indexCart).getId() == id) {
                    Cart cart = carts.get(indexCart);
                    int newQuantity = cart.getQuantity() + quantityChange;
                    if (newQuantity < 1) {
                        newQuantity = 1;
                    }
                    cart.setQuantity(newQuantity);
                    carts.set(indexCart, cart);
                    session.setAttribute("carts", carts);
                    return "redirect:client/cart";
                }
            }
        }
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
        cartRepository.save(cart);
        String referer = req.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/home");
    }

}
