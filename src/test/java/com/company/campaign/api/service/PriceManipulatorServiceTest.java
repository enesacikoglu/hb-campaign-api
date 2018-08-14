package com.company.campaign.api.service;

import com.company.campaign.api.builder.CampaignBuilder;
import com.company.campaign.api.builder.CampaignProductBuilder;
import com.company.campaign.api.builder.OrderBuilder;
import com.company.campaign.api.builder.ProductBuilder;
import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.domain.Order;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.repository.CampaignProductRepository;
import com.company.campaign.api.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class PriceManipulatorServiceTest {

    @InjectMocks
    private PriceManipulatorService priceManipulatorService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CampaignProductRepository campaignProductRepository;

    @Test
    public void it_should_manipulate_price() throws Exception {
        //given

        Product product = ProductBuilder
                .aProduct()
                .productCode(1L)
                .price(BigDecimal.TEN)
                .stock(1000L)
                .build();

        Order order = OrderBuilder
                .anOrder()
                .orderId(61L)
                .product(product)
                .quantity(10L)
                .campaignOrder(true)
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


        CampaignProduct savedCampaignProduct = CampaignProductBuilder
                .aCampaignProduct()
                .campaign(campaign)
                .product(product)
                .campaignPrice(BigDecimal.TEN)
                .averageItemPrice(1L)
                .totalSalesCount(10L)
                .turnover(2L)
                .campaignRemainingTime(2.00)
                .realPrice(BigDecimal.TEN)
                .build();

        given(orderRepository.findByProduct_ProductCodeAndIsCampaignOrderTrue(1L))
                .willReturn(Optional.of(Arrays.asList(order)));

        given(campaignProductRepository.save(any(CampaignProduct.class)))
                .willReturn(savedCampaignProduct);

        //when
        CampaignProduct expectedCampaignProduct = priceManipulatorService.calculate(campaignProduct);

        //then
        assertThat(expectedCampaignProduct).isNotNull();
        assertThat(expectedCampaignProduct.getCampaignPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(expectedCampaignProduct).isEqualToComparingFieldByField(savedCampaignProduct);
    }


    @Test
    public void it_should_not_manipulate_price_and_return_default_price() throws Exception {
        //given

        Product product = ProductBuilder
                .aProduct()
                .productCode(1L)
                .price(BigDecimal.TEN)
                .stock(1000L)
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


        given(orderRepository.findByProduct_ProductCodeAndIsCampaignOrderTrue(1L))
                .willReturn(Optional.empty());

        //when
        CampaignProduct expectedCampaignProduct = priceManipulatorService.calculate(campaignProduct);

        //then
        verifyZeroInteractions(campaignProductRepository);
        assertThat(expectedCampaignProduct).isNotNull();
        assertThat(expectedCampaignProduct.getCampaignPrice()).isEqualTo(BigDecimal.ONE);
        assertThat(expectedCampaignProduct).isEqualTo(campaignProduct);
    }
}