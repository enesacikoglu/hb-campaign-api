package com.company.campaign.api.service;

import com.company.campaign.api.builder.CampaignBuilder;
import com.company.campaign.api.builder.CampaignProductBuilder;
import com.company.campaign.api.builder.ProductBuilder;
import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.domain.enums.StatusType;
import com.company.campaign.api.repository.CampaignProductRepository;
import com.company.campaign.api.repository.CampaignRepository;
import com.company.campaign.api.repository.ProductRepository;
import com.company.campaign.api.service.implemantations.CampaignCreateExecutorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
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

    @Mock
    private CampaignProductRepository campaignProductRepository;

    @Test // TODO testini düzelticeksin...
    public void it_should_execute_command_and_return_created_campaign() throws Exception {
        //given
        final String command = "create_campaign NS 1 12 24 100";

        Product product = ProductBuilder
                .aProduct()
                .productCode(1L)
                .price(BigDecimal.ONE)
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

        CampaignProduct campaignProduct = CampaignProductBuilder
                .aCampaignProduct()
                .campaign(campaign)
                .product(product)
                .realPrice(product.getPrice())
                .campaignPrice(product.getPrice())
                .campaignRemainingTime(campaign.getDuration())
                .build();

        given(productRepository.findById(1L))
                .willReturn(Optional.of(product));

        given(campaignRepository.save(any(Campaign.class)))
                .willReturn(campaign);

        given(campaignProductRepository.save(any(CampaignProduct.class)))
                .willReturn(campaignProduct);


        //when
        Campaign expectedCampaign = campaignCreateExecutorService.executeCommand(command);

        //then
        assertThat(expectedCampaign.getCampaignId()).isEqualTo(1L);
        assertThat(expectedCampaign.getProduct()).isEqualTo(product);
        assertThat(expectedCampaign.getName()).isEqualTo("NS");
        assertThat(expectedCampaign.getDuration()).isEqualTo(12.00);
        assertThat(expectedCampaign.getPriceManipulationLimit()).isEqualTo(24.00);
        assertThat(expectedCampaign.getTargetSalesCount()).isEqualTo(100);
    }

}