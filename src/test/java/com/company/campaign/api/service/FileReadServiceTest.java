package com.company.campaign.api.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileReadService.class)
public class FileReadServiceTest {

    @Autowired
    private FileReadService fileReadService;

    @Test
    public void it_should_return_string_list_of_given_multipart_text() throws IOException {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "file", "multipart/form-data", "get_product_info 1" .getBytes());

        //when
        List<String> strings = fileReadService.readFromMultiPartFile(mockMultipartFile);

        //then
        assertThat(strings).hasSize(1);
    }


    @Test
    public void it_should_return_string_list_of_given_file_path() throws IOException, URISyntaxException {
        //given
        String fileName = "commands.hb";

        //when
        List<String> commands = fileReadService.readFromResourceFile(fileName);

        //then
        assertThat(commands.size()).isGreaterThan(0);
    }
}