package com.company.campaign.api.service;

import com.company.campaign.api.builder.CampaignBuilder;
import com.company.campaign.api.builder.CampaignProductBuilder;
import com.company.campaign.api.builder.ProductBuilder;
import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.repository.CampaignProductRepository;
import com.company.campaign.api.repository.ProductRepository;
import com.company.campaign.api.service.implemantations.ProductInfoExecutorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProductInfoExecutorServiceTest {

    @InjectMocks
    private ProductInfoExecutorService productInfoExecutorService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CampaignProductRepository campaignProductRepository;


    @Test
    public void it_should_execute_command_and_return_product_info() throws Exception {
        //given
        final String command = "get_product_info 1";

        Product product = ProductBuilder
                .aProduct()
                .productCode(1L)
                .price(BigDecimal.ONE)
                .stock(2L)
                .build();

        Campaign campaign = CampaignBuilder
                .aCampaign()
                .name("NS")
                .duration(12.00)
                .product(product)
                .priceManipulationLimit(20.00)
                .targetSalesCount(120L)
                .build();

        CampaignProduct campaignProduct = CampaignProductBuilder
                .aCampaignProduct()
                .campaign(campaign)
                .product(product)
                .campaignPrice(BigDecimal.ONE)
                .averageItemPrice(1L)
                .totalSalesCount(10L)
                .turnover(2L)
                .campaignRemainingTime(2.00)
                .realPrice(BigDecimal.TEN)
                .build();


        given(productRepository.findById(1L))
                .willReturn(Optional.of(product));

        given(campaignProductRepository.findByProduct_ProductCode(1L))
                .willReturn(Optional.of(campaignProduct));

        //when
        Product expectedProduct = productInfoExecutorService.executeCommand(command);

        //then
        assertThat(expectedProduct.getProductCode()).isEqualTo(1L);
        assertThat(expectedProduct.getPrice()).isEqualTo(BigDecimal.ONE);
        assertThat(expectedProduct.getStock()).isEqualTo(2L);
    }
}