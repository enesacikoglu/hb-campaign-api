package com.company.campaign.api.controller;

import com.company.campaign.api.service.CommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final CommandService commandService;

    public FileController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping("/uploadFile")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String content = new String(file.getBytes(), "UTF-8");
        List<String> commands = Arrays.asList(content.split("\n"));
        commandService.runCommands(commands);
    }
}
