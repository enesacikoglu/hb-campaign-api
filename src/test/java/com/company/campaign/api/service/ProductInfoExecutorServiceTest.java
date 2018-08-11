package com.company.campaign.api.service;

import com.company.campaign.api.builder.ProductBuilder;
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
public class ProductInfoExecutorServiceTest {

    @InjectMocks
    private ProductInfoExecutorService productInfoExecutorService;

    @Mock
    private ProductRepository productRepository;


    @Test
    public void it_should_execute_command_and_return_product_info() throws Exception {
        //given
        final String command = "get_product_info 1";

        Product product = ProductBuilder
                .aProduct()
                .productCode(1L)
                .price(BigInteger.ONE)
                .stock(2L)
                .build();

        given(productRepository.findById(1L))
                .willReturn(Optional.of(product));

        //when
        Product expectedProduct = productInfoExecutorService.executeCommand(command);

        //then
        assertThat(expectedProduct.getProductCode()).isEqualTo(1L);
        assertThat(expectedProduct.getPrice()).isEqualTo(1);
        assertThat(expectedProduct.getStock()).isEqualTo(2L);
    }
}