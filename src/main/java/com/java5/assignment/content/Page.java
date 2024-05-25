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
        route.put(PageType.ABOUT_US, new Page("client/about-us.html", "about-us"));
        route.put(PageType.ORDER, new Page("client/order.html", "order"));
        route.put(PageType.CART, new Page("client/cart.html", "cart"));
        route.put(PageType.ADMIN_DASHBOARD, new Page("admin/dashboard.html", "dashboard"));
        route.put(PageType.ADMIN_PRODUCT, new Page("admin/product.html", "product"));
        route.put(PageType.ADMIN_PRODUCT_VERSION, new Page("admin/product-version.html", "product"));
        route.put(PageType.ADMIN_PRODUCT_LIST, new Page("admin/product-list.html", "product"));
        route.put(PageType.ADMIN_ATTRIBUTE, new Page("admin/attribute.html", "product"));
        route.put(PageType.ADMIN_RATING,  new Page("admin/rating.html", "user"));
        route.put(PageType.ADMIN_ACCOUNT,  new Page("admin/account.html", "user"));
        route.put(PageType.ADMIN_PROMOTION, new Page("admin/promotion.html", "discount"));
        route.put(PageType.ADMIN_VOUCHER,  new Page("admin/voucher.html", "discount"));
        route.put(PageType.ADMIN_DISCOUNT, new Page("admin/discount.html", "discount"));
        route.put(PageType.ADMIN_ORDER, new Page("admin/order.html", "order"));
        route.put(PageType.ADMIN_PURCHASE, new Page("admin/purchase.html", "purchase"));
        route.put(PageType.ADMIN_CATEGORY,  new Page("admin/category.html", "category"));
        route.put(PageType.ADMIN_BRAND, new Page("admin/brand.html", "brand"));
        route.put(PageType.ADMIN_STATISTICAL_PRODUCT, new Page("admin/statistical-product.html", "statistical"));
        route.put(PageType.ADMIN_STATISTICAL_ORDER, new Page("admin/statistical_order.html", "statistical"));
        route.put(PageType.ADMIN_STATISTICAL_REVENUE, new Page("admin/statistical-revenue.html", "statistical"));
        route.put(PageType.ADMIN_STATISTICAL_INTERACT, new Page("/admin/statistical-interact.html", "statistical"));
        route.put(PageType.PROFILE, new Page("public/profile.html", "account"));
        route.put(PageType.LOGIN, new Page("public/login.html", "login"));
        route.put(PageType.REGISTER,  new Page("public/register.html", "register"));
        route.put(PageType.CHANGE_PASSWORD, new Page("public/change-password.html", "change-password"));

    }
}