package com.company.campaign.api.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = -8568166021632768067L;

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "product_code", nullable = false)
    private Product product;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "is_campaign_order", nullable = false)
    private Boolean isCampaignOrder = Boolean.FALSE;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Boolean getCampaignOrder() {
        return isCampaignOrder;
    }

    public void setCampaignOrder(Boolean campaignOrder) {
        isCampaignOrder = campaignOrder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("orderId", orderId)
                .append("product", product)
                .append("quantity", quantity)
                .toString();
    }
}
