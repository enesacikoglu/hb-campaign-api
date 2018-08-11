package com.company.campaign.api.service.implemantations;


import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.repository.CampaignRepository;
import com.company.campaign.api.service.interfaces.ICommandExecutor;
import com.company.campaign.api.service.interfaces.ICommandOutPutPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampaignInfoExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    @Autowired
    private CampaignRepository campaignRepository;

    @Override
    public Campaign executeCommand(String runCommand) throws Exception {
        String[] commands = runCommand.split(" ");
        Campaign campaign = campaignRepository.findByName(commands[1])
                .orElseThrow(() -> new Exception("Campaign Not Found!")); //TODO throw exception
        print("Campaign info ;" + campaign.toString());
        return campaign;
    }
}
