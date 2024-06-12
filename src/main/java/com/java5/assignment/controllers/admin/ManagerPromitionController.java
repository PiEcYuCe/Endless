package com.java5.assignment.controllers.admin;

import com.java5.assignment.entities.Brand;
import com.java5.assignment.entities.Promotion;
import com.java5.assignment.entities.Voucher;
import com.java5.assignment.jpa.PromotionRepository;
import com.java5.assignment.model.BrandModel;
import com.java5.assignment.model.PromotionModel;
import com.java5.assignment.model.VoucherModel;
import com.java5.assignment.services.UploadService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class ManagerPromitionController {
    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    private UploadService uploadService;

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_PROMOTION);
    }

    @ModelAttribute("promotions")
    public List<Promotion> getPromotions() {
        return promotionRepository.findAll();
    }

    @GetMapping("/manage-promotion")
    public String get() {
        return "admin/layout";
    }


    @PostMapping("/manage-add-promotion")
    public String addVoucher(@Valid PromotionModel promotionModel, BindingResult error, Model model) {
        String fileName = uploadService.uploadFile(promotionModel.getPoster(), "product");
        if (fileName == null) {
            error.addError(new FieldError("promotion", "poster", "Please select a image"));
        }
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }


        Promotion promotion = new Promotion();
        promotion.setName(promotionModel.getName());
        promotion.setDescription(promotionModel.getDescription());
        promotion.setStartDate(promotionModel.getStartDate());
        promotion.setEndDate(promotionModel.getEndDate());
        promotion.setStatus(promotionModel.isStatus());
        promotion.setPoster(fileName);


        promotionRepository.save(promotion);

        return "redirect:/manage-promotion";
    }

    @PostMapping("/manage-edit-promotion")
    public String editBrand(@RequestParam("id") long id, Model model) {
        Promotion promotion = promotionRepository.findById(id).get();
        model.addAttribute("promotion", promotion);
        return "admin/layout";
    }

    @PostMapping("/manage-update-promotion")
    public String updateBrand(@Valid PromotionModel promotionModel, BindingResult error,
                              @RequestParam("id") long id, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", error);
            return "admin/layout";
        }

        Promotion promotion = promotionRepository.findById(id).get();
        promotion.setName(promotionModel.getName());
        promotion.setDescription(promotionModel.getDescription());
        promotion.setStartDate(promotionModel.getStartDate());
        promotion.setEndDate(promotionModel.getEndDate());
        promotion.setStatus(promotionModel.isStatus());

        String fileName = uploadService.uploadFile(promotionModel.getPoster(), "promotion");
        if (fileName == null) {
            fileName = promotionRepository.findById(id).get().getPoster();
        } else {
            uploadService.remove(promotionRepository.findById(id).get().getPoster());
        }
        promotion.setPoster(fileName);
        promotionRepository.save(promotion);
        return "redirect:/manage-promotion";
    }

    @PostMapping("/manage-delete-promotion")
    public String deleteBrand(@RequestParam("id") long id) {
        Promotion promotion = promotionRepository.findById(id).get();
        uploadService.remove(promotionRepository.findById(id).get().getPoster());
        promotionRepository.delete(promotion);
        return "redirect:/manage-promotion";
    }

    @GetMapping("/manage-clear-formPromotion")
    public String clearForm() {
        return "redirect:/manage-promotion";
    }


}



