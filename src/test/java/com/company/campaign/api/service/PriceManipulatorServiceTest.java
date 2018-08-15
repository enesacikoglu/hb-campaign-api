package com.company.campaign.api.service;

import com.company.campaign.api.builder.CampaignBuilder;
import com.company.campaign.api.builder.CampaignProductBuilder;
import com.company.campaign.api.builder.ProductBuilder;
import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.domain.enums.StatusType;
import com.company.campaign.api.repository.CampaignProductRepository;
import com.company.campaign.api.util.TimeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class PriceManipulatorServiceTest {


    @InjectMocks
    private PriceManipulatorService priceManipulatorService;

    @Mock
    private CampaignProductRepository campaignProductRepository;

    @Mock
    private CampaignProductAggregateService campaignProductAggregateService;


    @Test
    public void it_should_manipulate_campaign_product_price() {
        //given
        TimeHelper.reset();
        Double increasedTime = 2.00;

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
                .campaignRemainingTime(12.00)
                .build();


        CampaignProduct aggregatedProduct = CampaignProductBuilder
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

        given(campaignProductRepository.findAll())
                .willReturn(Arrays.asList(campaignProduct));

        given(campaignProductAggregateService.aggregateCurrentStatistics(campaignProduct))
                .willReturn(aggregatedProduct);

        given(campaignProductRepository.save(any(CampaignProduct.class)))
                .willReturn(aggregatedProduct);

        //when
        BigDecimal price = priceManipulatorService.manipulate(increasedTime);

        //then
        assertThat(price).isEqualTo(BigDecimal.valueOf(9.69).setScale(2, 2));
    }

    @Test
    public void it_should_not_manipulate_campaign_and_return_real_price_when_time_is_bigger_than_campaign_time() {
        //given
        TimeHelper.reset();

        TimeHelper.incrementTime(13L);

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
                .campaignRemainingTime(12.00)
                .realPrice(BigDecimal.TEN)
                .build();


        given(campaignProductRepository.findAll())
                .willReturn(Arrays.asList(campaignProduct));

        //when
        BigDecimal price = priceManipulatorService.manipulate(2.00);


        //then
        verifyZeroInteractions(campaignProductAggregateService);
        verify(campaignProductRepository, times(0)).save(any(CampaignProduct.class));

        assertThat(price).isEqualTo(BigDecimal.TEN);
    }


    @Test
    public void it_should_not_manipulate_and_return_last_campaign_price_when_campaign_ended() {
        //given
        TimeHelper.reset();

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
                .campaignRemainingTime(12.00)
                .realPrice(BigDecimal.TEN)
                .status(StatusType.ENDED)
                .build();


        given(campaignProductRepository.findAll())
                .willReturn(Arrays.asList(campaignProduct));


        given(campaignProductRepository.save(any(CampaignProduct.class)))
                .willReturn(campaignProduct);

        //when
        BigDecimal price = priceManipulatorService.manipulate(2.00);


        //then
        verifyZeroInteractions(campaignProductAggregateService);

        assertThat(price).isEqualTo(BigDecimal.ONE);
    }


    @Test
    public void it_should_manipulate_campaign_only_using_time_multiplier_when_total_sales_is_zero() {
        //given
        TimeHelper.reset();

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
                .campaignRemainingTime(12.00)
                .realPrice(BigDecimal.TEN)
                .build();

        CampaignProduct aggregatedProduct = CampaignProductBuilder
                .aCampaignProduct()
                .campaign(campaign)
                .product(product)
                .campaignPrice(BigDecimal.ONE)
                .averageItemPrice(1L)
                .turnover(2L)
                .campaignRemainingTime(2.00)
                .realPrice(BigDecimal.TEN)
                .build();


        given(campaignProductRepository.findAll())
                .willReturn(Arrays.asList(campaignProduct));

        given(campaignProductAggregateService.aggregateCurrentStatistics(campaignProduct))
                .willReturn(aggregatedProduct);

        given(campaignProductRepository.save(any(CampaignProduct.class)))
                .willReturn(aggregatedProduct);


        //when
        BigDecimal price = priceManipulatorService.manipulate(2.00);


        //then
        assertThat(price).isEqualTo(BigDecimal.valueOf(9.60).setScale(2, 2));
    }

    @Test
    public void it_should_manipulate_campaign_using_time_multiplier_and_sales_rate_when_total_sales_is_bigger_than_target_sales() {
        //given
        TimeHelper.reset();

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
                .campaignRemainingTime(12.00)
                .realPrice(BigDecimal.TEN)
                .build();

        CampaignProduct aggregatedProduct = CampaignProductBuilder
                .aCampaignProduct()
                .campaign(campaign)
                .product(product)
                .campaignPrice(BigDecimal.ONE)
                .averageItemPrice(1L)
                .turnover(2L)
                .campaignRemainingTime(2.00)
                .totalSalesCount(240L)
                .realPrice(BigDecimal.TEN)
                .build();


        given(campaignProductRepository.findAll())
                .willReturn(Arrays.asList(campaignProduct));

        given(campaignProductAggregateService.aggregateCurrentStatistics(campaignProduct))
                .willReturn(aggregatedProduct);

        given(campaignProductRepository.save(any(CampaignProduct.class)))
                .willReturn(aggregatedProduct);


        //when
        BigDecimal price = priceManipulatorService.manipulate(2.00);


        //then
        assertThat(price).isEqualTo(BigDecimal.valueOf(9.93).setScale(2, 2));
    }

    @Test
    public void it_should_manipulate_campaign_using_time_multiplier_and_sales_rate_when_total_sales_is_smaller_than_target_sales() {
        //given
        TimeHelper.reset();

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
                .campaignRemainingTime(12.00)
                .realPrice(BigDecimal.TEN)
                .build();

        CampaignProduct aggregatedProduct = CampaignProductBuilder
                .aCampaignProduct()
                .campaign(campaign)
                .product(product)
                .campaignPrice(BigDecimal.ONE)
                .averageItemPrice(1L)
                .turnover(2L)
                .campaignRemainingTime(2.00)
                .totalSalesCount(10L)
                .realPrice(BigDecimal.TEN)
                .build();


        given(campaignProductRepository.findAll())
                .willReturn(Arrays.asList(campaignProduct));

        given(campaignProductAggregateService.aggregateCurrentStatistics(campaignProduct))
                .willReturn(aggregatedProduct);

        given(campaignProductRepository.save(any(CampaignProduct.class)))
                .willReturn(aggregatedProduct);


        //when
        BigDecimal price = priceManipulatorService.manipulate(2.00);


        //then
        assertThat(price).isEqualTo(BigDecimal.valueOf(9.69).setScale(2, 2));
    }

    @Test
    public void it_should_manipulate_campaign_using_time_multiplier_when_total_sales_is_equal_to_target_sales() {
        //given
        TimeHelper.reset();

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
                .campaignRemainingTime(12.00)
                .realPrice(BigDecimal.TEN)
                .build();

        CampaignProduct aggregatedProduct = CampaignProductBuilder
                .aCampaignProduct()
                .campaign(campaign)
                .product(product)
                .campaignPrice(BigDecimal.ONE)
                .averageItemPrice(1L)
                .turnover(2L)
                .campaignRemainingTime(2.00)
                .totalSalesCount(120L)
                .realPrice(BigDecimal.TEN)
                .build();


        given(campaignProductRepository.findAll())
                .willReturn(Arrays.asList(campaignProduct));

        given(campaignProductAggregateService.aggregateCurrentStatistics(campaignProduct))
                .willReturn(aggregatedProduct);

        given(campaignProductRepository.save(any(CampaignProduct.class)))
                .willReturn(aggregatedProduct);


        //when
        BigDecimal price = priceManipulatorService.manipulate(2.00);


        //then
        assertThat(price).isEqualTo(BigDecimal.valueOf(9.66).setScale(2, 2));
    }
}