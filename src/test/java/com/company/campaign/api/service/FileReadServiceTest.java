package com.company.campaign.api.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FileReadServiceTest {

    @InjectMocks
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
}