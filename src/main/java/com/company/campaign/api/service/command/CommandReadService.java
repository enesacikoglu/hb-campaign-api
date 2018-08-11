package com.company.campaign.api.service.command;

import com.company.campaign.api.domain.Product;
import com.company.campaign.api.service.ICommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CommandReadService {

    private static Logger LOGGER = LoggerFactory.getLogger(CommandReadService.class);

    public List<String> readCommandsByFileName(String fileName) throws URISyntaxException, IOException {
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
