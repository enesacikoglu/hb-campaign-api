package com.company.campaign.api.domain;

import com.company.campaign.api.domain.enums.StatusType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "campaign")
public class Campaign implements Serializable {

    private static final long serialVersionUID = -4619578838304972667L;

    @Id
    @GeneratedValue
    @Column(name = "campaign_id")
    private Long campaignId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_code", nullable = false)
    private Product product;

    @Column(name = "duration", nullable = false)
    private Double duration;

    @Column(name = "price_manipulation_limit", nullable = false)
    private Double priceManipulationLimit;


    @Column(name = "target_sales_count", nullable = false)
    private Long targetSalesCount;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusType status = StatusType.ACTIVE;

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getPriceManipulationLimit() {
        return priceManipulationLimit;
    }

    public void setPriceManipulationLimit(Double priceManipulationLimit) {
        this.priceManipulationLimit = priceManipulationLimit;
    }

    public Long getTargetSalesCount() {
        return targetSalesCount;
    }

    public void setTargetSalesCount(Long targetSalesCount) {
        this.targetSalesCount = targetSalesCount;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("campaignId", campaignId)
                .append("name", name)
                .append("product", product)
                .append("duration", duration)
                .append("priceManipulationLimit", priceManipulationLimit)
                .append("targetSalesCount", targetSalesCount)
                .append("status", status)
                .toString();
    }
}
