package com.company.campaign.api.service;

import com.company.campaign.api.enums.CommandType;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandService {

    private final ApplicationContext applicationContext;

    public CommandService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void runCommands(List<String> commands) {
        commands.forEach(command -> applicationContext
                .getBean(CommandType.findCommandTypeBeanNameBy(command.split(" ")[0]), ICommandExecutor.class)
                .executeCommand(command));
    }
}
