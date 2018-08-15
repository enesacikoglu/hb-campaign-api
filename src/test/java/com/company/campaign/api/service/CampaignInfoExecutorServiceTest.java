package com.company.campaign.api.service;

import com.company.campaign.api.builder.CampaignBuilder;
import com.company.campaign.api.builder.CampaignProductBuilder;
import com.company.campaign.api.builder.ProductBuilder;
import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.repository.CampaignProductRepository;
import com.company.campaign.api.service.implemantations.CampaignInfoExecutorService;
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
public class CampaignInfoExecutorServiceTest {

    @InjectMocks
    private CampaignInfoExecutorService campaignInfoExecutorService;

    @Mock
    private CampaignProductRepository campaignProductRepository;

    @Mock
    private CampaignProductAggregateService campaignProductAggregateService;


    @Test
    public void it_should_execute_command_and_return_campaign() throws Exception {
        //given
        final String command = "get_campaign_info NS";

        Product product = ProductBuilder.aProduct().productCode(1L).price(BigDecimal.ONE).build();

        Campaign campaign = CampaignBuilder
                .aCampaign()
                .campaignId(1L)
                .name("NS")
                .duration(12.00)
                .product(product)
                .priceManipulationLimit(24.00)
                .targetSalesCount(100L)
                .build();

        CampaignProduct campaignProduct = CampaignProductBuilder
                .aCampaignProduct()
                .campaign(campaign)
                .product(product)
                .campaignPrice(BigDecimal.ONE)
                .campaignRemainingTime(12.00)
                .build();

        CampaignProduct manipulatedCampaignProduct = CampaignProductBuilder
                .aCampaignProduct()
                .campaign(campaign)
                .product(product)
                .campaignPrice(BigDecimal.ONE)
                .campaignRemainingTime(12.00)
                .turnover(12L)
                .averageItemPrice(1L)
                .totalSalesCount(100L)
                .build();


        given(campaignProductRepository.findByCampaign_Name("NS"))
                .willReturn(Optional.of(campaignProduct));

        given(campaignProductAggregateService.aggregateCurrentStatistics(campaignProduct))
                .willReturn(manipulatedCampaignProduct);

        //when
        CampaignProduct expectedCampaign = campaignInfoExecutorService.executeCommand(command);

        //then
        assertThat(expectedCampaign.getProduct()).isEqualTo(product);
        assertThat(expectedCampaign.getCampaign()).isEqualTo(campaign);
        assertThat(expectedCampaign.getCampaignPrice()).isEqualTo(BigDecimal.ONE);
        assertThat(expectedCampaign.getCampaignRemainingTime()).isEqualTo(12.00);
        assertThat(expectedCampaign.getTotalSalesCount()).isEqualTo(100L);
    }
}