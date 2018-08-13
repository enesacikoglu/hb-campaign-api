package com.company.campaign.api.service.implemantations;


import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.domain.enums.StatusType;
import com.company.campaign.api.repository.CampaignProductRepository;
import com.company.campaign.api.repository.OrderRepository;
import com.company.campaign.api.repository.ProductRepository;
import com.company.campaign.api.service.interfaces.ICommandExecutor;
import com.company.campaign.api.service.interfaces.ICommandOutPutPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TimeIncreaseExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CampaignProductRepository campaignProductRepository;

    @Override
    public Double executeCommand(String command) throws Exception {

        String[] commands = command.split(" ");
        Double increasedTime = Double.valueOf(commands[1]);

        List<CampaignProduct> campaignProducts = (List<CampaignProduct>) campaignProductRepository.findAll();
        CampaignProduct campaignProduct = campaignProducts.get(0);

        Campaign campaign = campaignProduct.getCampaign();
        Double duration = campaign.getDuration();

        Double campaignRemainingTime = campaignProduct.getCampaignRemainingTime();
        campaignRemainingTime = campaignRemainingTime - increasedTime;
        campaignProduct.setCampaignRemainingTime(campaignRemainingTime);

        if (campaignProduct.getStatus() == StatusType.ACTIVE) {
            double priceMultiplier = getPriceMultiplier(campaign, campaignRemainingTime);
            BigDecimal campaignPrice = campaignProduct.getCampaignPrice();
            BigDecimal priceDiff = campaignPrice.multiply(BigDecimal.valueOf(priceMultiplier).setScale(2, 2))
                    .setScale(2, 2);
            BigDecimal newPrice = campaignPrice.subtract(priceDiff)
                    .setScale(2, 2);
            campaignProduct.setCampaignPrice(newPrice);
            campaignProductRepository.save(campaignProduct);
        }

        Double time = duration - campaignRemainingTime;
        print("Time is " + time);
        return time;
    }

    private double getPriceMultiplier(Campaign campaign, Double campaignRemainingTime) {
        return (campaign.getPriceManipulationLimit() / 100) * (campaign.getDuration() - campaignRemainingTime) / (campaign.getDuration());
    }
}
