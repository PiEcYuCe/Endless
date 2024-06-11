package com.java5.assignment.controllers.client;

import com.java5.assignment.dto.CartInfo;
import com.java5.assignment.dto.UserDto;
import com.java5.assignment.dto.UserInfoDto;
import com.java5.assignment.dto.VoucherDto;
import com.java5.assignment.entities.*;
import com.java5.assignment.jpa.*;
import com.java5.assignment.services.CartService;
import com.java5.assignment.services.UserService;
import com.java5.assignment.services.VoucherService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    HttpSession session;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private VoucherService voucherService;
    @Autowired
    private ProductVersionRepository productVersionRepository;

    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.ORDER);
    }

    @ModelAttribute("Orders")
    public List<Order> getOrders() {
        UserDto userDto = (UserDto) session.getAttribute("user");
        return orderRepository.findByUserID(userDto.getId());
    }

    @GetMapping("/order")
    public String goToPage(Model model) {
        UserDto userDto = (UserDto)session.getAttribute("user");
        User user = userRepository.findById(userDto.getId()).get();
        model.addAttribute("user", user);
        return "client/index";
    }

    @PostMapping("/rating")
    public String addOrder(@ModelAttribute("Order") Order order) {
        UserDto userDto = (UserDto)session.getAttribute("user");
        User user = userRepository.findById(userDto.getId()).get();

        return "";
    }

    @GetMapping("/api/get-order-details-list")
    @ResponseBody
    public List<CartInfo> getOrderDetailsByOrderID() {
        UserDto userDto = (UserDto)session.getAttribute("user");
        User user = userRepository.findById(userDto.getId()).get();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return cartService.getAllByUser(user, sort);
    }

    @GetMapping("/api/get-cart-list")
    @ResponseBody
    public List<CartInfo> getCartrList() {
       UserDto userDto = (UserDto)session.getAttribute("user");
       User user = userRepository.findById(userDto.getId()).get();
       Sort sort = Sort.by(Sort.Direction.DESC, "id");
       return cartService.getAllByUser(user, sort);
    }

    @GetMapping("/api/voucher-list")
    @ResponseBody
    public List<VoucherDto> getVoucherList() {
        UserDto userDto = (UserDto)session.getAttribute("user");
        List<VoucherDto> list = new ArrayList<>();
        for(Voucher voucher : voucherRepository.findAllByUserID(userDto.getId())) {
            list.add(voucherService.entityToDto(voucher));
        }
        return list;
    }

    @GetMapping("/api/user-infomation")
    @ResponseBody
    public UserInfoDto getUserInfomation() {
        UserDto userDto = (UserDto)session.getAttribute("user");
        return userService.getUserInfo(userDto.getUsername());
    }

    @PutMapping("/api/cart/update")
    public ResponseEntity<Boolean> updateCart(@RequestParam long id, @RequestParam int quantity) {
        try {
            Cart cart = cartRepository.findById(id).orElse(null);
            if (cart == null) {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
            cart.setQuantity(quantity);
            cartRepository.save(cart);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/cart/delete")
    public ResponseEntity<Boolean> deleteCart(@RequestParam long id) {
        try {
            if (!cartRepository.existsById(id)) {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
            cartRepository.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @PostMapping("/api/cart/add-to-cart")
        public ResponseEntity<?> addToCart(@RequestParam long id, @RequestParam(defaultValue = "1") int quantity) {
            ProductVersion productVersion = productVersionRepository.findById(id);
            if (productVersion == null) {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }

            UserDto userDto = (UserDto)session.getAttribute("user");
            Cart cart = cartRepository.findByUserIDAndProdVID(userDto.getId(), id);

            if (cart == null) {
                cart = new Cart();
                cart.setUserID(userRepository.findById(userDto.getId()).get());
                cart.setProductVersionID(productVersion);
                cart.setQuantity(quantity);
            } else {
                cart.setQuantity(cart.getQuantity() + quantity);
            }

            try {
                cartRepository.save(cart);
                return new ResponseEntity<>(true, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error adding product to cart", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}
