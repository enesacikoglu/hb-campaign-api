package com.company.campaign.api.builder;

import com.company.campaign.api.domain.Order;
import com.company.campaign.api.domain.Product;

public final class OrderBuilder {
    private Long orderId;
    private Product product;
    private Long quantity;

    private OrderBuilder() {
    }

    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    public OrderBuilder orderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderBuilder product(Product product) {
        this.product = product;
        return this;
    }

    public OrderBuilder quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public Order build() {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setProduct(product);
        order.setQuantity(quantity);
        return order;
    }
}
