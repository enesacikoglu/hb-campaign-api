package com.company.campaign.api.service.interfaces;

public interface ICommandExecutor {
    <T> T executeCommand(String command) throws RuntimeException;
}
