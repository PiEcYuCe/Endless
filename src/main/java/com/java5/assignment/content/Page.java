package com.java5.assignment.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Page {

    private String url;
    private String name;

    public static Map<PageType, Page> route = new HashMap<>();

    public static void setRoute(Map<PageType, Page> route) {
        Page.route = route;
    }

    static{
        route.put(PageType.HOME, new Page("client/home.html" , "home"));
        route.put(PageType.CONTACT,  new Page("client/contact.html", "contact"));
        route.put(PageType.PRODUCT,  new Page("client/product.html", "product"));
        route.put(PageType.DETAIL_PRODUCT,  new Page("client/detail-product.html", "detail-product"));
        route.put(PageType.PROMOTION, new Page("client/promotion.html", "promotion"));
        route.put(PageType.ORDER, new Page("client/order.html", "order"));
        route.put(PageType.ADMIN_HOME, new Page("admin/home.html", "home"));
        route.put(PageType.ADMIN_PRODUCT, new Page("admin/product.html", "product"));
        route.put(PageType.ADMIN_ATTRIBUTE, new Page("admin/attribute.html", "attribute"));
        route.put(PageType.ADMIN_RATING,  new Page("admin/rating.html", "rating"));
        route.put(PageType.ADMIN_ACCOUNT,  new Page("admin/account.html", "account"));
        route.put(PageType.ADMIN_PROMOTION, new Page("admin/promotion.html", "promotion"));
        route.put(PageType.ADMIN_VOUCHER,  new Page("admin/voucher.html", "voucher"));
        route.put(PageType.ADMIN_DISCOUNT, new Page("admin/discount.html", "discount"));
        route.put(PageType.ADMIN_ORDER, new Page("admin/order.html", "order"));
        route.put(PageType.ADMIN_PURCHASE, new Page("admin/purchase.html", "purchase"));
        route.put(PageType.ADMIN_CATEGORY,  new Page("admin/category.html", "category"));
        route.put(PageType.ADMIN_BRAND, new Page("admin/brand.html", "brand"));
        route.put(PageType.ADMIN_STATISTICAL, new Page("admin/statistical.html", "statistical"));
        route.put(PageType.PROFILE, new Page("public/profile.html", "profile"));
        route.put(PageType.LOGIN, new Page("public/login.html", "login"));
        route.put(PageType.REGISTER,  new Page("public/register.html", "register"));
        route.put(PageType.CHANGE_PASSWORD, new Page("public/change-password.html", "change-password"));

    }
}
