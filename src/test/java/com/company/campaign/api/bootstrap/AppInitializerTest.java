package com.company.campaign.api.bootstrap;

import com.company.campaign.api.service.CommandService;
import com.company.campaign.api.service.FileReadService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class AppInitializerTest {

    @Autowired
    private AppInitializer appInitializer;

    @MockBean
    private CommandService commandService;

    @MockBean
    private FileReadService fileReadService;

    @Test
    public void it_should_initialize_commands_under_resource_folder_by_file_name() throws Exception {
        //Arrange
        List<String> commands = Arrays.asList("create_product 1 100 1000", "get_product_info 1", "increase_time 2");
        given(fileReadService.readFromResourceFile("commands.hb"))
                .willReturn(commands);

        //Act
        appInitializer.run();

        //Assert
        verify(commandService).runCommands(commands);
    }
}