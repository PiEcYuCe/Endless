package com.java5.assignment.controllers.client;

import com.java5.assignment.dto.ProductInfoDTO;
import com.java5.assignment.dto.UserDto;
import com.java5.assignment.entities.*;
import com.java5.assignment.jpa.*;
import com.java5.assignment.services.ProductVersionService;
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

    @ModelAttribute("page")
    public Page setPageContent() {
        return Page.route.get(PageType.DETAIL_PRODUCT);
    }

    @GetMapping("/product/{id}")
    public String getProductDetails(@PathVariable Long id, Model model) {
        var product = productRepository.findById(id);

        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            var productVersions = productVersionRepository.findByProductID(id);
            model.addAttribute("productVersions", productVersions);
        }
        return "client/index";
    }

    @GetMapping("/product/detail")
    public String getProductDetail(@RequestParam("id") Long productId, Model model) {
        ProductInfoDTO productInfoDTO = productVersionService.getProductInfoById(productId);
        model.addAttribute("product", productInfoDTO);
        return "client/index";
    }

    @GetMapping("/products")
    public String showProducts(Model model) {
        List<ProductInfoDTO> productList = productVersionService.getAllProductInfo();
        model.addAttribute("productList", productList);
        return "products";
    }



//    @ModelAttribute("properties")
//    public String showProperties(Model model) {
//        List<AttributeValue> colors = attributeValueRepository.findValuesByAttributeName("Color");
//        List<AttributeValue> ramValues = attributeValueRepository.findValuesByAttributeName("RAM");
//        List<AttributeValue> storageValues = attributeValueRepository.findValuesByAttributeName("Storage");
//        model.addAttribute("colors", colors);
//        model.addAttribute("ramValues", ramValues);
//        model.addAttribute("storageValues", storageValues);
//        return "client/index";
//    }

    @GetMapping("/product/add-to-cart")
    public String addToCart(@RequestParam(name="id") long id,
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
