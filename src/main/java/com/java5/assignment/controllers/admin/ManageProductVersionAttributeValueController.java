package com.java5.assignment.controllers.admin;

import com.java5.assignment.dto.AttributeValueDto;
import com.java5.assignment.entities.Attribute;
import com.java5.assignment.entities.AttributeValue;
import com.java5.assignment.entities.Product;
import com.java5.assignment.entities.ProductVersion;
import com.java5.assignment.jpa.AttributeRepository;
import com.java5.assignment.jpa.AttributeValueRepository;
import com.java5.assignment.jpa.ProductRepository;
import com.java5.assignment.jpa.ProductVersionRepository;
import com.java5.assignment.model.admin.productVersion.ProductVersionCreateModel;
import com.java5.assignment.services.AttributeValueService;
import com.java5.assignment.services.UploadService;
import com.java5.assignment.utils.Page;
import com.java5.assignment.utils.PageType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ManageProductVersionAttributeValueController {

    @ModelAttribute("page")
    public Page page() {
        return Page.route.get(PageType.ADMIN_PRODUCT_VERSION_ATTRIBUTE_VALUE);
    }

    @GetMapping("/product-version-attributevalue")
    public String get() {
        return "admin/layout";
    }


}
