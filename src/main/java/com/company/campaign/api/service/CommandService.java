package com.company.campaign.api.service;

import com.company.campaign.api.enums.CommandType;
import com.company.campaign.api.service.interfaces.ICommandExecutor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.company.campaign.api.util.handler.ExceptionHandler.handleThrowable;

@Service
public class CommandService {

    private final ApplicationContext applicationContext;

    public CommandService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void runCommands(List<String> commands) {
        commands.forEach(command ->
                handleThrowable(() -> applicationContext
                        .getBean(CommandType.findCommandTypeBeanNameBy(command.split(" ")[0]), ICommandExecutor.class)
                        .executeCommand(command)));
    }
}
