package com.java5.assignment.controllers.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java5.assignment.dto.UserDto;
import com.java5.assignment.model.purchase.PurchaseData;
import com.java5.assignment.model.purchase.PurchaseRequest;
import com.java5.assignment.services.PurchaseService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import com.java5.assignment.model.ProductVersionModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class ManagePurchaseController {
    @Autowired
    HttpSession session;

    @Autowired
    private PurchaseService purchaseService;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_PURCHASE);
    }

    @GetMapping("/manage-purchase")
    public String get(Model model) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/static/json/product-version.json");
        byte[] jsonData = inputStream.readAllBytes();
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductVersionModel> productVersionModels = objectMapper.readValue(jsonData, new TypeReference<List<ProductVersionModel>>() {
        });

        model.addAttribute("productVersions", productVersionModels);

        return "admin/layout";
    }

    @GetMapping("/user-data")
    @ResponseBody
    public UserDto getInfo(){
        return (UserDto)session.getAttribute("user");
    }

    @PostMapping("/api/addNewPurchaser")
    @ResponseBody
    public boolean createOrderWithDetails(@RequestBody PurchaseRequest dataRequest) {
        try {
            purchaseService.addPurchaseOrderAndDetails(dataRequest.getPurchaseData(), dataRequest.getPurchaseDetailsData());
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
