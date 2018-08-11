package com.company.campaign.api.bootstrap;

import com.company.campaign.api.service.CommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class AppInitializer implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppInitializer.class);

    private static final String COMMAND_FILE_NAME = "commands.hb";

    @Autowired
    private CommandService commandService;

    @Override
    public void run(String... args) throws Exception {
        commandService.runCommands(getCommands(COMMAND_FILE_NAME));
    }

    private List<String> getCommands(String fileName) throws Exception {
        Path path = Paths.get(getClass().getClassLoader()
                .getResource(fileName).toURI());
        try (Stream<String> commands = Files.lines(path)) {
            return commands
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("IO Error occurred while getting commands", e);
            throw e;
        }
    }
}
