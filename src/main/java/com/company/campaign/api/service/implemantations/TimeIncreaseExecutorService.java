package com.company.campaign.api.service.implemantations;


import com.company.campaign.api.service.PriceManipulatorService;
import com.company.campaign.api.service.interfaces.ICommandExecutor;
import com.company.campaign.api.service.interfaces.ICommandOutPutPrinter;
import com.company.campaign.api.util.TimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeIncreaseExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    @Autowired
    private PriceManipulatorService priceManipulatorService;

    @Override
    public Long executeCommand(String command) {

        String[] commands = command.split(" ");
        Double increasedTime = Double.valueOf(commands[1]);
        priceManipulatorService.manipulate(increasedTime);
        Long time = TimeHelper.incrementTime(increasedTime.longValue());
        print("Time is " + time);
        return time;
    }
}
