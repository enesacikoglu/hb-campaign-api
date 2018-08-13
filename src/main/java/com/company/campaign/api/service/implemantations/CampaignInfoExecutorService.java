package com.company.campaign.api.service.implemantations;


import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.repository.CampaignProductRepository;
import com.company.campaign.api.service.PriceManipulatorService;
import com.company.campaign.api.service.interfaces.ICommandExecutor;
import com.company.campaign.api.service.interfaces.ICommandOutPutPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampaignInfoExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    @Autowired
    private CampaignProductRepository campaignProductRepository;

    @Autowired
    private PriceManipulatorService priceManipulatorService;

    @Override
    public CampaignProduct executeCommand(String runCommand) throws Exception {
        String[] commands = runCommand.split(" ");
        CampaignProduct campaignProduct = campaignProductRepository.findByCampaign_Name(commands[1])
                .orElseThrow(() -> new Exception("Campaign Not Found!")); //TODO throw exception
        campaignProduct = priceManipulatorService.calculate(campaignProduct);
        print("Campaign info ;" + campaignProduct.toString());
        return campaignProduct;
    }
}
