package com.company.campaign.api.service;

import com.company.campaign.api.builder.CampaignBuilder;
import com.company.campaign.api.builder.ProductBuilder;
import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.domain.enums.StatusType;
import com.company.campaign.api.repository.CampaignRepository;
import com.company.campaign.api.repository.ProductRepository;
import com.company.campaign.api.service.implemantations.CampaignCreateExecutorService;
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

@RunWith(MockitoJUnitRunner.class)
public class CampaignCreateExecutorServiceTest {

    @InjectMocks
    private CampaignCreateExecutorService campaignCreateExecutorService;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void it_should_execute_command_and_return_created_campaign() throws Exception {
        //given
        final String command = "create_campaign NS 1 12 24 100";

        Product product = ProductBuilder
                .aProduct()
                .productCode(1L)
                .price(BigInteger.ONE)
                .productCode(2L)
                .stock(3L)
                .build();


        Campaign campaign = CampaignBuilder
                .aCampaign()
                .campaignId(1L)
                .name("NS")
                .duration(12.00)
                .product(product)
                .priceManipulationLimit(24.00)
                .targetSalesCount(100L)
                .build();

        given(productRepository.findById(1L))
                .willReturn(Optional.of(product));

        given(campaignRepository.save(any(Campaign.class)))
                .willReturn(campaign);

        //when
        Campaign expectedCampaign = campaignCreateExecutorService.executeCommand(command);

        //then
        assertThat(expectedCampaign.getCampaignId()).isEqualTo(1L);
        assertThat(expectedCampaign.getProduct()).isEqualTo(product);
        assertThat(expectedCampaign.getName()).isEqualTo("NS");
        assertThat(expectedCampaign.getDuration()).isEqualTo(12.00);
        assertThat(expectedCampaign.getPriceManipulationLimit()).isEqualTo(24.00);
        assertThat(expectedCampaign.getTargetSalesCount()).isEqualTo(100);
        assertThat(expectedCampaign.getStatus()).isEqualByComparingTo(StatusType.ACTIVE);
    }

}