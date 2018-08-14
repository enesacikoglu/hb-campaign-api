package com.company.campaign.api.controller;

import com.company.campaign.api.service.CommandService;
import com.company.campaign.api.service.FileReadService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(FileController.class)
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommandService commandService;

    @MockBean
    private FileReadService fileReadService;


    @Test
    public void it_should_run_commands_from_file() throws Exception {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "file", "multipart/form-data", "get_product_info 1" .getBytes());

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile);
        given(fileReadService.readFromMultiPartFile(mockMultipartFile))
                .willReturn(Arrays.asList("get_product_info 1"));

        //when
        ResultActions result = mockMvc.perform(builder)
                .andDo(print());

        //then
        verify(commandService).runCommands(anyList());
        assertThat(result.andExpect(status().isCreated()));
    }
}