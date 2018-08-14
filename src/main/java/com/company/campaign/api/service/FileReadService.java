package com.company.campaign.api.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class FileReadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileReadService.class);

    private static final String UTF_8 = "UTF-8";
    public static final String NEW_LINE = "\n";

    public List<String> readFromMultiPartFile(MultipartFile file) throws IOException {
        String content = new String(file.getBytes(), UTF_8);
        return Arrays.asList(content.split(NEW_LINE));
    }

    public List<String> readFromResourceFile(String fileName) throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader()
                .getResource(fileName).toURI());
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            LOGGER.error("IO Error occurred while getting commands", e);
            throw e;
        }
    }
}
