package com.company.campaign.api.service;

import com.company.campaign.api.builder.ProductBuilder;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.repository.ProductRepository;
import com.company.campaign.api.service.implemantations.ProductCreateExecutorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
public class ProductCreateExecutorServiceTest {

    @InjectMocks
    private ProductCreateExecutorService productCreateExecutorService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void it_should_execute_command_and_return_created_product() {
        //given
        final String command = "create_product 1 10 1000";

        Product product = ProductBuilder
                .aProduct()
                .productCode(1L)
                .price(BigInteger.TEN)
                .stock(1000L)
                .build();

        given(productRepository.save(any(Product.class)))
                .willReturn(product);


        //when
        Product expectedProduct = productCreateExecutorService.executeCommand(command);

        //then
        assertThat(expectedProduct.getProductCode()).isEqualTo(1L);
        assertThat(expectedProduct.getPrice()).isEqualTo(10);
        assertThat(expectedProduct.getStock()).isEqualTo(1000L);
    }
}