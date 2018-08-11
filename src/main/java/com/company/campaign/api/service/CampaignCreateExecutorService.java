package com.company.campaign.api.service;


import com.company.campaign.api.builder.CampaignBuilder;
import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.repository.CampaignRepository;
import com.company.campaign.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampaignCreateExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Override
    public Campaign executeCommand(String runCommand) {
        String[] commands = runCommand.split(" ");
        Product product = productRepository.findById(Long.valueOf(commands[2]))
                .orElse(null);//TODO exception fırlat...
        Campaign campaign = CampaignBuilder
                .aCampaign()
                .name(commands[1])
                .duration(Double.valueOf(commands[3]))
                .product(product)
                .priceManipulationLimit(Double.valueOf(commands[4]))
                .targetSalesCount(Long.valueOf(commands[5]))
                .build();

        campaign = campaignRepository.save(campaign);
        print("Campaign created ;" + campaign.toString());
        return campaign;
    }
}
