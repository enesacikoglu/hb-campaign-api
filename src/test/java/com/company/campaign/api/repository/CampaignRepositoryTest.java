package com.company.campaign.api.repository;

import com.company.campaign.api.builder.CampaignBuilder;
import com.company.campaign.api.builder.ProductBuilder;
import com.company.campaign.api.domain.Campaign;
import com.company.campaign.api.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CampaignRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CampaignRepository campaignRepository;


    @Test
    public void it_should_get_campaign_by_name() {
        //given
        Product product = ProductBuilder
                .aProduct()
                .productCode(1L)
                .price(BigInteger.ONE)
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

        testEntityManager.persistAndFlush(product);
        testEntityManager.persist(campaign);

        //when
        Optional<Campaign> optionalCampaign = campaignRepository.findByName("NS");

        //then
        assertThat(optionalCampaign).isPresent();
        Campaign expectedCampaign = optionalCampaign.get();
        assertThat(expectedCampaign.getName()).isEqualTo("NS");
    }
}