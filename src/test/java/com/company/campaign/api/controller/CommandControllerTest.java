package com.company.campaign.api.controller;

import com.company.campaign.api.service.CommandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CommandController.class)
public class CommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommandService commandService;

    @Test
    public void it_should_run_given_command() throws Exception {
        //given
        String command = "get_product_info 1";

        //when
        ResultActions result = mockMvc.perform(get("/commands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(command))
                .andDo(print());

        //then
        verify(commandService).runCommands(anyList());
        result.andExpect(status().isOk());

    }
}