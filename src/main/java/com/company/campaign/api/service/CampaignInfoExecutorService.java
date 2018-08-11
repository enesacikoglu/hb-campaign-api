package com.company.campaign.api.service;


import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampaignInfoExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    @Autowired
    private CampaignRepository campaignRepository;

    @Override
    public Campaign executeCommand(String runCommand) {
        String[] commands = runCommand.split(" ");
        Campaign campaign = campaignRepository.findByName(commands[1])
                .orElse(null); //TODO throw exception
        print("Campaign info ;" + campaign.toString());
        return campaign;
    }
}
