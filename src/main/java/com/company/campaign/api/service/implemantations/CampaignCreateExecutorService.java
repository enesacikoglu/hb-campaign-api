package com.company.campaign.api.service.implemantations;


import com.company.campaign.api.builder.CampaignBuilder;
import com.company.campaign.api.builder.CampaignProductBuilder;
import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.repository.CampaignProductRepository;
import com.company.campaign.api.repository.CampaignRepository;
import com.company.campaign.api.repository.ProductRepository;
import com.company.campaign.api.service.interfaces.ICommandExecutor;
import com.company.campaign.api.service.interfaces.ICommandOutPutPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampaignCreateExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CampaignProductRepository campaignProductRepository;

    @Override
    public Campaign executeCommand(String runCommand) throws Exception {
        String[] commands = runCommand.split(" ");
        Product product = productRepository.findById(Long.valueOf(commands[2]))
                .orElseThrow(() -> new Exception("Product Not Found!"));//TODO exception fırlat...

        Campaign campaign = CampaignBuilder
                .aCampaign()
                .name(commands[1])
                .duration(Double.valueOf(commands[3]))
                .product(product)
                .priceManipulationLimit(Double.valueOf(commands[4]))
                .targetSalesCount(Long.valueOf(commands[5]))
                .build();

        campaign = campaignRepository.save(campaign);


        CampaignProduct campaignProduct = CampaignProductBuilder
                .aCampaignProduct()
                .campaign(campaign)
                .product(product)
                .realPrice(product.getPrice())
                .campaignPrice(product.getPrice())
                .campaignRemainingTime(campaign.getDuration())
                .build();

        campaignProductRepository.save(campaignProduct);

        print("Campaign created ;" + campaign.toString());
        return campaign;
    }
}
