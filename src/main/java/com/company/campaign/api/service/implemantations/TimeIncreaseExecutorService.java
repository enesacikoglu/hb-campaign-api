package com.company.campaign.api.service.implemantations;


import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.domain.enums.StatusType;
import com.company.campaign.api.repository.CampaignProductRepository;
import com.company.campaign.api.service.interfaces.ICommandExecutor;
import com.company.campaign.api.service.interfaces.ICommandOutPutPrinter;
import com.company.campaign.api.util.TimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TimeIncreaseExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    @Autowired
    private CampaignProductRepository campaignProductRepository;

    @Override
    public Long executeCommand(String command) throws Exception {

        String[] commands = command.split(" ");
        Double increasedTime = Double.valueOf(commands[1]);

        List<CampaignProduct> campaignProducts = (List<CampaignProduct>) campaignProductRepository.findAll();
        CampaignProduct campaignProduct = campaignProducts.get(0);

        Double campaignRemainingTime = campaignProduct.getCampaignRemainingTime();
        campaignRemainingTime = campaignRemainingTime - increasedTime;
        campaignProduct.setCampaignRemainingTime(campaignRemainingTime);

        if (campaignProduct.getStatus() == StatusType.ACTIVE) {
            campaignProduct.setCampaignPrice(calculateNewPrice(campaignProduct, campaignRemainingTime));
        }

        campaignProductRepository.save(campaignProduct);
        Long time = TimeHelper.incrementTime(increasedTime.longValue());
        print("Time is " + time);
        return time;
    }

    private BigDecimal calculateNewPrice(CampaignProduct campaignProduct, Double campaignRemainingTime) {
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
