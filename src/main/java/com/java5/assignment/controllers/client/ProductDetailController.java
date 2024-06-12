package com.java5.assignment.controllers.client;

import com.java5.assignment.dto.ProductInfoDTO;
import com.java5.assignment.dto.UserDto;
import com.java5.assignment.entities.*;
import com.java5.assignment.jpa.*;
import com.java5.assignment.services.ProductVersionService;
import com.java5.assignment.services.VersionAttributeService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductDetailController {

    @Autowired
    ProductVersionRepository productVersionRepository;
    @Autowired
    private ProductVersionService productVersionService;
    @Autowired
    HttpSession session;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;


    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    AttributeRepository attributeRepository;

    @Autowired
    VersionAttributeService versionAttributeService;

    @Autowired
    private VersionAttributeRepository versionAttributeRepository;


    @ModelAttribute("vouchers")
    public List<Voucher> getVoucher() {
        return voucherRepository.findAll();
    }

    @ModelAttribute("attributes")
    public List<Attribute> getAttribute(@RequestParam("id")long id) {
        return attributeRepository.findAll();
    }

    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.DETAIL_PRODUCT);
    }

    @GetMapping("/product/{id}")
    public String getProductDetails(@PathVariable Long id, Model model) {
        var product = productRepository.findById(id);

        List<Voucher> vouchers = voucherRepository.findAll();
        model.addAttribute("vouchers", vouchers);

        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            var productVersions = productVersionRepository.findByProductID(id);
            model.addAttribute("productVersions", productVersions);
        }
        return "client/index";
    }

    @GetMapping("/product/detail")
    public String getProductDetail(@RequestParam("id") Long id, Model model) {
        ProductInfoDTO productInfoDTO = productVersionService.getProductInfoById(id);
        model.addAttribute("product", productInfoDTO);
        model.addAttribute("vouchers", getVoucher()); // Add vouchers to the model
        model.addAttribute("attributes", versionAttributeService.getAttributesByProductVersionID(id));
        return "client/index";
    }


    @GetMapping("/product/add-to-cart")
    public String addToCart(@RequestParam(name = "id") long id,
                            @RequestParam(name = "quantity", defaultValue = "1") int quantity) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        User user = userRepository.findById(userDto.getId()).get();
        ProductVersion productVersion = productVersionRepository.findById(id);
        Cart cart = new Cart();
        cart.setUserID(user);
        cart.setQuantity(quantity);
        cart.setProductVersionID(productVersion);
        cartRepository.save(cart);
        return "client/index";
    }

}
