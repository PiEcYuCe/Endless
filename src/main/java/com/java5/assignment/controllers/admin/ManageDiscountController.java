package com.java5.assignment.controllers.admin;

import com.java5.assignment.dto.ProductVersionDto1;
import com.java5.assignment.dto.PromotionDto;
import com.java5.assignment.entities.ProductVersion;
import com.java5.assignment.entities.Promotion;
import com.java5.assignment.entities.PromotionDetail;
import com.java5.assignment.entities.PromotionProduct;
import com.java5.assignment.jpa.ProductVersionRepository;
import com.java5.assignment.jpa.PromotionDetailRepository;
import com.java5.assignment.jpa.PromotionProductRepository;
import com.java5.assignment.jpa.PromotionRepository;
import com.java5.assignment.services.ProductVersionService;
import com.java5.assignment.services.PromotionService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ManageDiscountController {
    @Autowired
    private ProductVersionRepository productVersionRepository;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_DISCOUNT);
    }

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    PromotionService promotionService;

    @Autowired
    ProductVersionService productVersionService;

    @Autowired
    PromotionDetailRepository promotionDetailRepository;

    @Autowired
    PromotionProductRepository promotionProductRepository;

    @GetMapping("/manage-discount")
    public String get() {
        return "admin/layout";
    }

    @GetMapping("/api/get-all-discount")
    @ResponseBody
    public List<PromotionDto> getAllDiscount() {
        List<PromotionDto> promotions = promotionService.getPromotionsDtoActive();
        return promotions == null ? new ArrayList<>() : promotions;
    }

    @GetMapping("/api/get-all-product-for-discount")
    @ResponseBody
    public List<ProductVersionDto1> getAllProductForDiscount() {
        return productVersionService.getAllForPromotion();
    }

    @PostMapping("/api/add-new-promotion-detail")
    public ResponseEntity<Boolean> addNewPromotionDetail(@RequestParam("promotionID") long promotionID, @RequestParam("DiscountLevel") int level) {
        Promotion promotion = promotionRepository.findById(promotionID).orElse(null);
        if (promotion == null || level < 0 || level > 99) {
            return ResponseEntity.badRequest().body(false);
        }

        PromotionDetail promotionDetail = new PromotionDetail();
        promotionDetail.setDiscountLevel(level);
        promotionDetail.setPromotionID(promotion);
        promotionDetailRepository.save(promotionDetail);

        return ResponseEntity.ok(true);
    }

    @PostMapping("/api/remove-1-promotion-product")
    public ResponseEntity<Boolean> removePromotion1Product(@RequestParam("promotionProductID") Long promotionProductID) {
        if(promotionProductID<0){
            return ResponseEntity.badRequest().body(false);
        }
        else {
            PromotionProduct promotionProduct = promotionProductRepository.findById(promotionProductID).get();
            promotionProductRepository.delete(promotionProduct);
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("/api/remove-promotion-product")
    public ResponseEntity<Boolean> removePromotionProduct(@RequestParam("promotionProductID") List <Long> promotionProductIDs) {
        if(promotionProductIDs.isEmpty()){
            return ResponseEntity.badRequest().body(false);
        }
        for (Long productProductID : promotionProductIDs) {
            PromotionProduct promotionProduct = promotionProductRepository.findById(productProductID).get();
            promotionProductRepository.delete(promotionProduct);
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("/api/create-promotion-product")
    public ResponseEntity<Boolean> createPromotionProduct(@RequestParam("promotionDetailID")long promotionDetailID, List<Long> productVersionIDs) {
        List<PromotionProduct> list = new ArrayList<>();
        PromotionDetail promotionDetail = promotionDetailRepository.findById(promotionDetailID).get();
        if (promotionDetail.getPromotionID()==null || productVersionIDs.isEmpty()){
            return ResponseEntity.badRequest().body(false);
        }
        for(Long productVersionID : productVersionIDs){
            ProductVersion productVersion = productVersionRepository.findById(productVersionID).get();
            PromotionProduct promotionProduct = new PromotionProduct();
            promotionProduct.setProductVersionID(productVersion);
            promotionProduct.setPromotionDetailID(promotionDetail);
            promotionProduct.setStatus("Active");
            promotionProductRepository.save(promotionProduct);
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("/api/create-1-promotion-product")
    public ResponseEntity<Boolean> create1PromotionProduct(@RequestParam("promotionDetailID")long promotionDetailID, @RequestParam Long productVersionID) {
        List<PromotionProduct> list = new ArrayList<>();
        PromotionDetail promotionDetail = promotionDetailRepository.findById(promotionDetailID).get();
        if (promotionDetail.getPromotionID()==null || productVersionID<0){
            return ResponseEntity.badRequest().body(false);
        }
        else{
            ProductVersion productVersion = productVersionRepository.findById(productVersionID).get();
            PromotionProduct promotionProduct = new PromotionProduct();
            promotionProduct.setProductVersionID(productVersion);
            promotionProduct.setPromotionDetailID(promotionDetail);
            promotionProduct.setStatus("Active");
            promotionProductRepository.save(promotionProduct);
        }
        return ResponseEntity.ok(true);
    }


}
