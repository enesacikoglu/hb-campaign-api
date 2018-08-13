package com.company.campaign.api.repository;

import com.company.campaign.api.builder.CampaignBuilder;
import com.company.campaign.api.builder.CampaignProductBuilder;
import com.company.campaign.api.builder.ProductBuilder;
import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CampaignProductRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CampaignProductRepository campaignProductRepository;


    @Test
    public void it_should_get_campaign_product_by_campaign_name_and_product_code() {
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
                .build();

        testEntityManager.persistAndFlush(product);
        testEntityManager.persistAndFlush(campaign);
        testEntityManager.persist(campaignProduct);

        //when
        Optional<CampaignProduct> campaignProductOptional = campaignProductRepository
                .findByCampaign_NameAndProduct_ProductCode("NS", 1L);

        //then
        assertThat(campaignProductOptional).isPresent();
        CampaignProduct actual = campaignProductOptional.get();
        assertThat(actual.getCampaign()).isEqualToComparingFieldByField(campaign);
        assertThat(actual.getProduct()).isEqualToComparingFieldByField(product);
        assertThat(actual.getCampaignProductId()).isNotNull();
        assertThat(actual.getAverageItemPrice()).isEqualTo(1L);
        assertThat(actual.getTotalSalesCount()).isEqualTo(10L);
        assertThat(actual.getCampaignRemainingTime()).isEqualTo(2.00);
        assertThat(actual.getCampaignPrice()).isEqualTo(BigDecimal.ONE);

    }


    @Test
    public void it_should_get_campaign_product_by_campaign_name() {
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
                .build();

        testEntityManager.persistAndFlush(product);
        testEntityManager.persistAndFlush(campaign);
        testEntityManager.persist(campaignProduct);

        //when
        Optional<CampaignProduct> campaignProductOptional = campaignProductRepository
                .findByCampaign_Name("NS");

        //then
        assertThat(campaignProductOptional).isPresent();
        CampaignProduct actual = campaignProductOptional.get();
        assertThat(actual.getCampaign()).isEqualToComparingFieldByField(campaign);
        assertThat(actual.getProduct()).isEqualToComparingFieldByField(product);
        assertThat(actual.getCampaignProductId()).isNotNull();
        assertThat(actual.getAverageItemPrice()).isEqualTo(1L);
        assertThat(actual.getTotalSalesCount()).isEqualTo(10L);
        assertThat(actual.getCampaignRemainingTime()).isEqualTo(2.00);
        assertThat(actual.getCampaignPrice()).isEqualTo(BigDecimal.ONE);

    }
}