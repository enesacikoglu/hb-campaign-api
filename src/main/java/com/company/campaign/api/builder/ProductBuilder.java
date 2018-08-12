package com.company.campaign.api.builder;

import com.company.campaign.api.domain.Product;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class ProductBuilder {
    private Long productCode;
    private BigDecimal price;
    private Long stock;

    private ProductBuilder() {
    }

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder productCode(Long productCode) {
        this.productCode = productCode;
        return this;
    }

    public ProductBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductBuilder stock(Long stock) {
        this.stock = stock;
        return this;
    }

    public Product build() {
        Product product = new Product();
        product.setProductCode(productCode);
        product.setPrice(price);
        product.setStock(stock);
        return product;
    }
}
