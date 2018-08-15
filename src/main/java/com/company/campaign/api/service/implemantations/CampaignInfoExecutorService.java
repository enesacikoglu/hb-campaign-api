package com.company.campaign.api.service.implemantations;


import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.exception.CampaignApiDomainNotFoundException;
import com.company.campaign.api.repository.CampaignProductRepository;
import com.company.campaign.api.service.CampaignProductAggregateService;
import com.company.campaign.api.service.interfaces.ICommandExecutor;
import com.company.campaign.api.service.interfaces.ICommandOutPutPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.company.campaign.api.constant.MessageKeyConstants.MESSAGE_KEY_CAMPAIGN_NOT_FOUND_EXCEPTION;

@Service
public class CampaignInfoExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    @Autowired
    private CampaignProductRepository campaignProductRepository;

    @Autowired
    private CampaignProductAggregateService campaignProductAggregateService;

    @Override
    public CampaignProduct executeCommand(String runCommand) {
        String[] commands = runCommand.split(" ");
        CampaignProduct campaignProduct = campaignProductRepository.findByCampaign_Name(commands[1])
                .orElseThrow(() -> new CampaignApiDomainNotFoundException(MESSAGE_KEY_CAMPAIGN_NOT_FOUND_EXCEPTION)); //TODO throw exception
        campaignProduct = campaignProductAggregateService.aggregateCurrentStatistics(campaignProduct);
        print("Campaign info " + campaignProduct);
        return campaignProduct;
    }
}
