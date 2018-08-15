package com.company.campaign.api.builder;

import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.domain.enums.StatusType;

import java.math.BigDecimal;

public final class CampaignProductBuilder {
    private Long campaignProductId;
    private Campaign campaign;
    private Product product;
    private BigDecimal realPrice;
    private BigDecimal campaignPrice;
    private StatusType status = StatusType.ACTIVE;
    private Long totalSalesCount = 0L;
    private Long turnover;
    private Long averageItemPrice;
    private Double campaignRemainingTime;

    private CampaignProductBuilder() {
    }

    public static CampaignProductBuilder aCampaignProduct() {
        return new CampaignProductBuilder();
    }

    public CampaignProductBuilder campaignProductId(Long campaignProductId) {
        this.campaignProductId = campaignProductId;
        return this;
    }

    public CampaignProductBuilder campaign(Campaign campaign) {
        this.campaign = campaign;
        return this;
    }

    public CampaignProductBuilder product(Product product) {
        this.product = product;
        return this;
    }

    public CampaignProductBuilder realPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
        return this;
    }

    public CampaignProductBuilder campaignPrice(BigDecimal campaignPrice) {
        this.campaignPrice = campaignPrice;
        return this;
    }

    public CampaignProductBuilder status(StatusType status) {
        this.status = status;
        return this;
    }

    public CampaignProductBuilder totalSalesCount(Long totalSalesCount) {
        this.totalSalesCount = totalSalesCount;
        return this;
    }

    public CampaignProductBuilder turnover(Long turnover) {
        this.turnover = turnover;
        return this;
    }

    public CampaignProductBuilder averageItemPrice(Long averageItemPrice) {
        this.averageItemPrice = averageItemPrice;
        return this;
    }

    public CampaignProductBuilder campaignRemainingTime(Double campaignRemainingTime) {
        this.campaignRemainingTime = campaignRemainingTime;
        return this;
    }

    public CampaignProduct build() {
        CampaignProduct campaignProduct = new CampaignProduct();
        campaignProduct.setCampaignProductId(campaignProductId);
        campaignProduct.setCampaign(campaign);
        campaignProduct.setProduct(product);
        campaignProduct.setRealPrice(realPrice);
        campaignProduct.setCampaignPrice(campaignPrice);
        campaignProduct.setStatus(status);
        campaignProduct.setTotalSalesCount(totalSalesCount);
        campaignProduct.setTurnover(turnover);
        campaignProduct.setAverageItemPrice(averageItemPrice);
        campaignProduct.setCampaignRemainingTime(campaignRemainingTime);
        return campaignProduct;
    }
}
