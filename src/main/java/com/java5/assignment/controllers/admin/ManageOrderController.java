package com.java5.assignment.controllers.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java5.assignment.dto.ProductInfoDTO;
import com.java5.assignment.dto.UserInfoDto;
import com.java5.assignment.entities.ProductVersion;
import com.java5.assignment.jpa.OrderRepository;
import com.java5.assignment.jpa.ProductVersionRepository;
import com.java5.assignment.services.ProductVersionService;
import com.java5.assignment.services.UserService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import com.java5.assignment.model.ProductVersionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class ManageOrderController {
    @Autowired
    ProductVersionRepository productVersionRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductVersionService productVersionService;

    @Autowired
    private UserService userService;


    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_ORDER);
    }

    @ModelAttribute("productVersions")
    public List<ProductInfoDTO> productVersions() {
        List<ProductInfoDTO> versions = productVersionService.getAllProductActive();
        System.out.println("Number of product versions: " + versions.size());
        return versions;
    }

    @GetMapping("/api/productVersions")
    @ResponseBody
    public List<ProductInfoDTO> getProductVersions() {
        return productVersionService.getAllProductActive();
    }

    @GetMapping("/api/userInfo")
    @ResponseBody
    public UserInfoDto getUserInfo(@RequestParam("userKey") String userKey) {
        return userService.getUserInfo(userKey);
    }

    @GetMapping("/manage-order")
    public String get(Model model) throws IOException {

        return "admin/layout";
    }

}
