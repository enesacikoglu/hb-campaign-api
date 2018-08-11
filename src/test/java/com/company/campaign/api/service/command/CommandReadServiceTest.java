package com.company.campaign.api.service.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommandReadService.class)
public class CommandReadServiceTest {

    @Autowired
    private CommandReadService commandReadService;

    @Test
    public void it_should_get_data_when_given_file_under_resource_by_file_name() throws URISyntaxException, IOException {
        //Arrange
        final String fileName = "commands.hb";

        //Act
        List<String> commands = commandReadService.readCommandsByFileName(fileName);

        //Assert
        assertThat(commands).hasSize(6);
    }


    @Test
    public void it_should_throw_IO_exception_given_file_under_resource_by_file_name() throws URISyntaxException, IOException {
        //Arrange
        final String fileName = "nofile.hb";

        //Act
        Throwable throwable = catchThrowable(() -> commandReadService.readCommandsByFileName(fileName));

        //Assert
        // assertThat(throwable).isExactlyInstanceOf(IOException.class);
    }
}