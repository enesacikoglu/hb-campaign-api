package com.company.campaign.api.bootstrap;

import com.company.campaign.api.service.CommandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;



import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class AppInitializerTest {

    @Autowired
    private AppInitializer appInitializer;

    @MockBean
    private CommandService commandService;

    @Test
    public void it_should_initialize_commands_under_resource_folder_by_file_name() throws Exception {
        //Arrange

        //Act
        appInitializer.run();

        //Assert
        verify(commandService,times(2)).runCommands(anyList());
    }


}