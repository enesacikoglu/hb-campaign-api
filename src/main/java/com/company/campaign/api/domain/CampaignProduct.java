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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "campaign_product")
public class CampaignProduct {

    @Id
    @GeneratedValue
    @Column(name = "campaign_product_id")
    private Long campaignProductId;

    @OneToOne
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @OneToOne
    @JoinColumn(name = "product_code", nullable = false)
    private Product product;

    @Column(name = "real_price", nullable = false)
    private BigDecimal realPrice;

    @Column(name = "campaign_price", nullable = false)
    private BigDecimal campaignPrice;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusType status = StatusType.ACTIVE;

    @Column(name = "total_sales_count", nullable = false)
    private Long totalSalesCount = 0L;

    @Column(name = "turnover")
    private Long turnover;

    @Column(name = "average_item_price")
    private Long averageItemPrice;

    @Column(name = "campaign_remaining_time")
    private Double campaignRemainingTime;


    public Long getCampaignProductId() {
        return campaignProductId;
    }

    public void setCampaignProductId(Long campaignProductId) {
        this.campaignProductId = campaignProductId;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getCampaignPrice() {
        return campaignPrice;
    }

    public void setCampaignPrice(BigDecimal campaignPrice) {
        this.campaignPrice = campaignPrice;
    }

    public StatusType getStatus() {
        if (this.campaignRemainingTime.compareTo(0.0) <= 0) {
            this.setStatus(StatusType.ENDED);
            return status;
        }
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Long getTotalSalesCount() {
        return totalSalesCount;
    }

    public void setTotalSalesCount(Long totalSalesCount) {
        this.totalSalesCount = totalSalesCount;
    }

    public Long getTurnover() {
        return turnover;
    }

    public void setTurnover(Long turnover) {
        this.turnover = turnover;
    }

    public Long getAverageItemPrice() {
        return averageItemPrice;
    }

    public void setAverageItemPrice(Long averageItemPrice) {
        this.averageItemPrice = averageItemPrice;
    }

    public Double getCampaignRemainingTime() {
        if (campaignRemainingTime.compareTo(0.0) <= 0) {
            this.setCampaignRemainingTime(0.0);
        }
        return campaignRemainingTime;
    }

    public void setCampaignRemainingTime(Double campaignRemainingTime) {
        this.campaignRemainingTime = campaignRemainingTime;
    }

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", campaign.getName())
                .append("status", getStatus())
                .append("targetSalesCount", campaign.getTargetSalesCount())
                .append("totalSalesCount", totalSalesCount)
                .append("turnover", turnover)
                .append("averageItemPrice", averageItemPrice)
                .append("productPrice", campaignPrice)
                .append("campaignRemainingTime", getCampaignRemainingTime())
                .toString();
    }
}
