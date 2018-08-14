package com.company.campaign.api.controller;

import com.company.campaign.api.service.CommandService;
import com.company.campaign.api.service.FileReadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final CommandService commandService;

    private final FileReadService fileReadService;

    public FileController(CommandService commandService, FileReadService fileReadService) {
        this.commandService = commandService;
        this.fileReadService = fileReadService;
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        commandService.runCommands(fileReadService.readFromMultiPartFile(file));
    }
}
