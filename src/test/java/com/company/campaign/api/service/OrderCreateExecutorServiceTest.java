package com.company.campaign.api.service;

import com.company.campaign.api.builder.OrderBuilder;
import com.company.campaign.api.builder.ProductBuilder;
import com.company.campaign.api.domain.Order;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.repository.OrderRepository;
import com.company.campaign.api.repository.ProductRepository;
import com.company.campaign.api.service.implemantations.OrderCreateExecutorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderCreateExecutorServiceTest {

    @InjectMocks
    private OrderCreateExecutorService orderCreateExecutorService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void it_should_execute_command_and_return_created_order() throws Exception {
        //given
        final String command = "create_order 1 200";

        Product product = ProductBuilder
                .aProduct()
                .productCode(1L)
                .price(BigInteger.ONE)
                .productCode(2L)
                .stock(300L)
                .build();

        Order order = OrderBuilder
                .anOrder()
                .orderId(61L)
                .product(product)
                .quantity(200L)
                .build();


        given(productRepository.findById(1L))
                .willReturn(Optional.of(product));

        given(orderRepository.save(any(Order.class)))
                .willReturn(order);

        //when
        Order expectedOrder = orderCreateExecutorService.executeCommand(command);

        //then
        verify(productRepository, times(1)).save(any(Product.class));

        assertThat(expectedOrder.getProduct().getPrice()).isEqualTo(1);
        assertThat(expectedOrder.getProduct().getStock()).isEqualTo(100L);
        assertThat(expectedOrder.getProduct().getProductCode()).isEqualTo(2L);
        assertThat(expectedOrder.getOrderId()).isNotNull();
        assertThat(expectedOrder.getQuantity()).isEqualTo(200L);
    }

}