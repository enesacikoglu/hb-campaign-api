package com.company.campaign.api.service;

import com.company.campaign.api.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class ProductCreateExecutorServiceTest {

    @InjectMocks
    private ProductCreateExecutorService productCreateExecutorService;

    @Test
    public void it_should_execute_command_and_return_created_product() {
        //given
        final String command = "create_product 1 100 1000";

        //when
        Product product = productCreateExecutorService.executeCommand(command);

        //then
        assertThat(product.getProductCode()).isEqualTo(1L);
        assertThat(product.getPrice()).isEqualTo(100L);
        assertThat(product.getStock()).isEqualTo(1000L);
    }
}