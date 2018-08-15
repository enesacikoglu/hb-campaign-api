package com.company.campaign.api.service;

import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.domain.enums.StatusType;
import com.company.campaign.api.repository.CampaignProductRepository;
import com.company.campaign.api.util.TimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PriceManipulatorService {

    @Autowired
    private CampaignProductRepository campaignProductRepository;

    @Autowired
    private CampaignProductAggregateService campaignProductAggregateService;

    public BigDecimal manipulate(Double increasedTime) {

        List<CampaignProduct> campaignProducts = (List<CampaignProduct>) campaignProductRepository.findAll();
        CampaignProduct campaignProduct = campaignProducts.get(0);

        Double campaignRemainingTime = campaignProduct.getCampaignRemainingTime();
        campaignRemainingTime = campaignRemainingTime - increasedTime;
        campaignProduct.setCampaignRemainingTime(campaignRemainingTime);

        if (TimeHelper.getCurrentTime().doubleValue() > campaignProduct.getCampaign().getDuration()) {
            return campaignProduct.getRealPrice();
        }

        if (campaignProduct.getStatus() == StatusType.ACTIVE) {
            campaignProduct = campaignProductAggregateService.aggregateCurrentStatistics(campaignProduct);
            Double priceMultiplier = getPriceMultiplier(campaignProduct.getCampaign(), campaignRemainingTime);
            calculatePrice(campaignProduct, priceMultiplier, campaignRemainingTime);
        }

        campaignProduct = campaignProductRepository.save(campaignProduct);
        return campaignProduct.getCampaignPrice();
    }

    private CampaignProduct calculatePrice(CampaignProduct campaignProduct, Double priceMultiplier, Double campaignRemainingTime) {
        Long totalSalesCount = campaignProduct.getTotalSalesCount();
        Long targetSalesCount = campaignProduct.getCampaign().getTargetSalesCount();

        if (totalSalesCount.compareTo(0L) == 0) {
            BigDecimal priceWithoutSales = calculatePriceWithoutSales(campaignProduct, campaignRemainingTime);
            campaignProduct.setCampaignPrice(priceWithoutSales);
            return campaignProduct;
        }

        if (totalSalesCount.compareTo(targetSalesCount) <= 0) {
            double salesRate = (double) totalSalesCount / targetSalesCount;
            double rate = (1 - salesRate) == 0.0 ? 1.00 : (1 - salesRate);
            rate = priceMultiplier * rate;
            BigDecimal diff = campaignProduct.getRealPrice().multiply(BigDecimal.valueOf(rate))
                    .setScale(2, 2);
            BigDecimal subtract = campaignProduct.getRealPrice().subtract(diff)
                    .setScale(2, 2);
            campaignProduct.setCampaignPrice(subtract);
        } else {
            double rate = ((double) totalSalesCount / (campaignProduct.getProduct().getStock() + totalSalesCount));
            rate = priceMultiplier * rate;
            BigDecimal diff = campaignProduct.getRealPrice().multiply(BigDecimal.valueOf(rate))
                    .setScale(2, 2);
            BigDecimal add = campaignProduct.getRealPrice().subtract(diff)
                    .setScale(2, 2);
            campaignProduct.setCampaignPrice(add);
        }
        return campaignProduct;
    }

    private BigDecimal calculatePriceWithoutSales(CampaignProduct campaignProduct, Double campaignRemainingTime) {
        Double priceMultiplier = getPriceMultiplier(campaignProduct.getCampaign(), campaignRemainingTime);
        BigDecimal priceDiff = getPriceDiff(priceMultiplier, campaignProduct.getRealPrice());
        return campaignProduct.getRealPrice().subtract(priceDiff)
                .setScale(2, 2);
    }

    private BigDecimal getPriceDiff(Double priceMultiplier, BigDecimal campaignPrice) {
        return campaignPrice
                .multiply(BigDecimal.valueOf(priceMultiplier).setScale(2, 2))
                .setScale(2, 2);
    }

    private Double getPriceMultiplier(Campaign campaign, Double campaignRemainingTime) {
        return (campaign.getPriceManipulationLimit() / 100) * (campaign.getDuration() - campaignRemainingTime) / (campaign.getDuration());
    }
}
