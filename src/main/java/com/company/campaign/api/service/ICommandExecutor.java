package com.company.campaign.api.service;

public interface ICommandExecutor {
    <T> T executeCommand(String command);
}
