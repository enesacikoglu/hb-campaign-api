package com.company.campaign.api.domain;

import com.company.campaign.api.builder.CampaignProductBuilder;
import com.company.campaign.api.domain.enums.StatusType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CampaignProductTest {


    @Test
    public void it_should_return_status_ended_when_campaign_remaining_time_is_equal_or_smaller_than_zero() {
        //given
        CampaignProduct campaignProduct = CampaignProductBuilder
                .aCampaignProduct()
                .campaignRemainingTime(0.00)
                .build();

        //when
        StatusType status = campaignProduct.getStatus();

        //then
        assertThat(status).isEqualByComparingTo(StatusType.ENDED);
    }

    @Test
    public void it_should_return_campaign_remaining_time_is_zer0_when_campaign_remaining_time_is_equal_or_smaller_than_zero() {
        //given
        CampaignProduct campaignProduct = CampaignProductBuilder
                .aCampaignProduct()
                .campaignRemainingTime(0.00)
                .build();

        //when
        Double campaignRemainingTime = campaignProduct.getCampaignRemainingTime();

        //then
        assertThat(campaignRemainingTime).isEqualTo(0.0);
    }
}