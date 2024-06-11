package com.java5.assignment.model.admin.productVersion;

import com.java5.assignment.entities.AttributeValue;
import com.java5.assignment.entities.Product;
import com.java5.assignment.entities.ProductVersion;

import java.util.List;

public class ProductVersionDetail {

    private Product product;
    private ProductVersion productVersion;
    private List<AttributeValue> attributeValues;

    public ProductVersionDetail(Product product, ProductVersion productVersion, List<AttributeValue> attributeValues) {
        this.product = product;
        this.productVersion = productVersion;
        this.attributeValues = attributeValues;
    }

    // Getters and setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductVersion getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(ProductVersion productVersion) {
        this.productVersion = productVersion;
    }

    public List<AttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(List<AttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
