package com.company.campaign.api.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class FileReadService {

    private static final String UTF_8 = "UTF-8";
    public static final String NEW_LINE = "\n";

    private final CommandService commandService;

    public FileReadService(CommandService commandService) {
        this.commandService = commandService;
    }

    public List<String> readFromMultiPartFile(MultipartFile file) throws IOException {
        String content = new String(file.getBytes(), UTF_8);
        return Arrays.asList(content.split(NEW_LINE));
    }
}
