package com.java5.assignment.content;

import java.util.HashMap;
import java.util.Map;

public class Page {

    private String url;

    public Page(String url) {
        super();
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Map<PageType, Page> route = new HashMap<>();

    public static void setRoute(Map<PageType, Page> route) {
        Page.route = route;
    }

    static{
        route.put(PageType.HOME, new Page("client/home.html"));
        route.put(PageType.CONTACT,  new Page("client/contact.html"));
        route.put(PageType.PRODUCT,  new Page("client/product.html"));
        route.put(PageType.DETAIL_PRODUCT,  new Page("client/detail-product.html"));
        route.put(PageType.PROMOTION, new Page("client/promotion.html"));
        route.put(PageType.ORDER, new Page("client/order.html"));
        route.put(PageType.ADMIN_HOME, new Page("admin/home.html"));
        route.put(PageType.ADMIN_PRODUCT, new Page("admin/product.html"));
        route.put(PageType.ADMIN_ATTRIBUTE, new Page("admin/attribute.html"));
        route.put(PageType.ADMIN_RATING,  new Page("admin/rating.html"));
        route.put(PageType.ADMIN_ACCOUNT,  new Page("admin/account.html"));
        route.put(PageType.ADMIN_PROMOTION, new Page("admin/promotion.html"));
        route.put(PageType.ADMIN_VOUCHER,  new Page("admin/voucher.html"));
        route.put(PageType.ADMIN_DISCOUNT, new Page("admin/discount.html"));
        route.put(PageType.ADMIN_ORDER, new Page("admin/order.html"));
        route.put(PageType.ADMIN_PURCHASE, new Page("admin/purchase.html"));
        route.put(PageType.ADMIN_CATEGORY,  new Page("admin/category.html"));
        route.put(PageType.ADMIN_BRAND, new Page("admin/brand.html"));
        route.put(PageType.ADMIN_STATISTICAL, new Page("admin/statistical.html"));
        route.put(PageType.PROFILE, new Page("public/profile.html"));
        route.put(PageType.LOGIN, new Page("public/login.html"));
        route.put(PageType.REGISTER,  new Page("public/register.html"));
        route.put(PageType.CHANGE_PASSWORD, new Page("public/change-password.html"));

    }
}
