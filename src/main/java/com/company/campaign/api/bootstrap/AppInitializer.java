package com.company.campaign.api.bootstrap;

import com.company.campaign.api.service.CommandService;
import com.company.campaign.api.service.FileReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


@Component
public class AppInitializer implements CommandLineRunner {

    private static final String COMMAND_FILE_NAME = "commands.hb";

    @Autowired
    private CommandService commandService;

    @Autowired
    private FileReadService fileReadService;

    @Override
    public void run(String... args) throws IOException, URISyntaxException {
        List<String> commands = fileReadService.readFromResourceFile(COMMAND_FILE_NAME);
        commandService.runCommands(commands);
    }
}
