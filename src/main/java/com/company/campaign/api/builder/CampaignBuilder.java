package com.company.campaign.api.builder;

import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.domain.enums.StatusType;

public final class CampaignBuilder {
    private Long campaignId;
    private String name;
    private Product product;
    private Double duration;
    private Double priceManipulationLimit;
    private Long targetSalesCount;
    private StatusType status = StatusType.ACTIVE;

    private CampaignBuilder() {
    }

    public static CampaignBuilder aCampaign() {
        return new CampaignBuilder();
    }

    public CampaignBuilder campaignId(Long campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    public CampaignBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CampaignBuilder product(Product product) {
        this.product = product;
        return this;
    }

    public CampaignBuilder duration(Double duration) {
        this.duration = duration;
        return this;
    }

    public CampaignBuilder priceManipulationLimit(Double priceManipulationLimit) {
        this.priceManipulationLimit = priceManipulationLimit;
        return this;
    }

    public CampaignBuilder targetSalesCount(Long targetSalesCount) {
        this.targetSalesCount = targetSalesCount;
        return this;
    }

    public CampaignBuilder status(StatusType status) {
        this.status = status;
        return this;
    }

    public Campaign build() {
        Campaign campaign = new Campaign();
        campaign.setCampaignId(campaignId);
        campaign.setName(name);
        campaign.setProduct(product);
        campaign.setDuration(duration);
        campaign.setPriceManipulationLimit(priceManipulationLimit);
        campaign.setTargetSalesCount(targetSalesCount);
        campaign.setStatus(status);
        return campaign;
    }
}
