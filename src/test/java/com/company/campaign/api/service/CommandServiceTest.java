package com.company.campaign.api.service;


import com.company.campaign.api.service.implemantations.ProductCreateExecutorService;
import com.company.campaign.api.service.implemantations.ProductInfoExecutorService;
import com.company.campaign.api.service.interfaces.ICommandExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandServiceTest {

    @InjectMocks
    private CommandService commandService;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private ProductCreateExecutorService productCreateExecutorService;

    @Mock
    private ProductInfoExecutorService productInfoExecutorService;


    @Test
    public void it_should_run_product_create_command() {
        //given
        when(applicationContext.getBean("productCreateExecutorService", ICommandExecutor.class))
                .thenReturn(productCreateExecutorService)
                .thenReturn(productInfoExecutorService);

        List<String> commands = Arrays.asList("get_product_info 1", "create_product 1 100 1000");

        //when
        commandService.runCommands(commands);


        //then
        verify(applicationContext).getBean("productCreateExecutorService", ICommandExecutor.class);
        verify(applicationContext).getBean("productInfoExecutorService", ICommandExecutor.class);
    }
}