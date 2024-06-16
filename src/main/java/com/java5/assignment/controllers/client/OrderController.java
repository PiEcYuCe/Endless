package com.java5.assignment.controllers.client;

import com.java5.assignment.dto.*;
import com.java5.assignment.entities.*;
import com.java5.assignment.jpa.*;
import com.java5.assignment.services.*;
import com.java5.assignment.utils.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private OrderService orderService;

    @Autowired
    private ProductVersionRepository productVersionRepository;

    @Autowired
    private RatingService ratingService;

    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.ORDER);
    }

    @GetMapping("/get-all-order-user")
    public List<OrderDto> getAllOrderUser() {
        UserDto userDto = (UserDto) session.getAttribute("user");
        return orderService.getAllOrdersDtoByUserID(userDto.getId());
    }

    @PostMapping("/cancel/order")
    public String cancelOrder(@RequestParam("orderId") long orderId, @RequestParam(name = "check", defaultValue = "false") boolean check, Model model) {
        if (check) {
            Order order = orderRepository.findById(orderId).orElse(null);
            if (order != null && order.getOrderStatus().equals("Processing")) {
                order.setOrderStatus("Cancelled");
                orderRepository.save(order);
                SuccessModal successModal = new SuccessModal("Order cancellation successful !");
                model.addAttribute("successModal", successModal);
            } else {
                ErrorModal errorModal = new ErrorModal("Orders that have been processed or previously canceled");
                model.addAttribute("errorModal", errorModal);
            }
        } else {
            String message = "Do you want to delete this order?";
            String text = "Confirm";
            String link = "/cancel/order?orderId=" + orderId;
            WarningModal warningModal = new WarningModal(message, text, link);
            model.addAttribute("warningModal", warningModal);
        }
        return "client/index";
    }


    @GetMapping("/order")
    public String goToPage(Model model) {
        return "client/index";
    }

    @ModelAttribute("Orders")
    public List<Order> getOrders() {
        UserDto userDto = (UserDto) session.getAttribute("user");
        return orderRepository.findByUserID(userDto.getId());
    }

    @PostMapping("/userCancel/order")
    public String cancelOrder(@RequestParam("orderId") long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setOrderStatus("Cancelled");
            orderRepository.save(order);
        }
        return "redirect:/order";
    }


    @PostMapping("/rating")
    public String addOrder(@ModelAttribute("Order") Order order) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        User user = userRepository.findById(userDto.getId()).get();
        return "redirect:/order";
    }

    @GetMapping("/api/get-order-details-list")
    @ResponseBody
    public List<CartInfo> getOrderDetailsByOrderID() {
        UserDto userDto = (UserDto) session.getAttribute("user");
        User user = userRepository.findById(userDto.getId()).get();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return cartService.getAllByUser(user, sort);
    }

    @GetMapping("/api/get-cart-list")
    @ResponseBody
    public List<CartInfo> getCartrList() {
        UserDto userDto = (UserDto) session.getAttribute("user");
        User user = userRepository.findById(userDto.getId()).get();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return cartService.getAllByUser(user, sort);
    }

    @GetMapping("/api/voucher-list")
    @ResponseBody
    public List<VoucherDto> getVoucherList() {
        UserDto userDto = (UserDto) session.getAttribute("user");
        List<VoucherDto> list = new ArrayList<>();
        for (Voucher voucher : voucherRepository.findAllByUserID(userDto.getId())) {
            list.add(voucherService.entityToDto(voucher));
        }
        return list;
    }

    @GetMapping("/api/user-infomation")
    @ResponseBody
    public UserInfoDto getUserInfomation() {
        UserDto userDto = (UserDto) session.getAttribute("user");
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

        UserDto userDto = (UserDto) session.getAttribute("user");
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

    @GetMapping("/api/get-prodVersion-by-orderID")
    @ResponseBody
    public List<ProductVersion> getListProdVersionByOrder(@RequestParam("orderId") long orderId){
        List<ProductVersion> list = orderRepository.selectProductVersionList(orderId);
        return list == null ? new ArrayList<>() : list;
    }



    @PostMapping("/api/save-rating")
    @ResponseBody
    public Boolean saveOrder(Model model, @RequestParam long orderId, @RequestParam int rating, @RequestParam String comment,
                             @RequestParam long productVersionID, @RequestParam List<MultipartFile> image) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        User user = userRepository.findById(userDto.getId()).get();
        ProductVersion productVersion = productVersionRepository.findById(productVersionID);
        return ratingService.saveAllRatingAndPicture(productVersion, user, comment, rating, image);
    }
}
