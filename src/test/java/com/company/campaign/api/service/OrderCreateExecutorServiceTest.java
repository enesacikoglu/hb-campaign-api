package com.company.campaign.api.service;

import com.company.campaign.api.builder.ProductBuilder;
import com.company.campaign.api.domain.Order;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class OrderCreateExecutorServiceTest {

    @InjectMocks
    private OrderCreateExecutorService orderCreateExecutorService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void it_should_execute_command_and_return_created_order() {
        //given
        final String command = "create_order 1 1000";

        Product product = ProductBuilder
                .aProduct()
                .productCode(1L)
                .price(BigInteger.ONE)
                .productCode(2L)
                .stock(3L)
                .build();

        given(productRepository.findById(1L))
                .willReturn(Optional.of(product));

        //when
        Order order = orderCreateExecutorService.executeCommand(command);

        //then
        assertThat(order.getProduct()).isEqualTo(product);
        assertThat(order.getOrderId()).isNotNull();
        assertThat(order.getQuantity()).isEqualTo(1000L);
    }

}