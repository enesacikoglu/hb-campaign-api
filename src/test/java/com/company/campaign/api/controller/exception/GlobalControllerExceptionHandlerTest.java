package com.company.campaign.api.controller.exception;

import com.company.campaign.api.exception.CampaignApiBusinessException;
import com.company.campaign.api.exception.CampaignApiDomainNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GlobalControllerExceptionHandlerTest {

    @InjectMocks
    private GlobalControllerExceptionHandler globalControllerExceptionHandler;

    @Mock
    private MessageSource messageSource;

    @Test
    public void it_should_handle_campaign_api_domain_not_found_exception() {
        //given
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        CampaignApiDomainNotFoundException campaignApiDomainNotFoundException = new CampaignApiDomainNotFoundException("not.found.exception");

        //when
        ResponseEntity<?> responseEntity = globalControllerExceptionHandler
                .handleApiDomainNotFoundException(campaignApiDomainNotFoundException, mockHttpServletRequest);

        //then
        verify(messageSource).getMessage("not.found.exception", null, new Locale("TR"));
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void it_should_handle_business_exception() {
        //given
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        CampaignApiBusinessException campaignApiBusinessException = new CampaignApiBusinessException("business.exception");

        //when
        ResponseEntity<?> responseEntity = globalControllerExceptionHandler
                .handleApiBusinessException(campaignApiBusinessException, mockHttpServletRequest);

        //then
        verify(messageSource).getMessage("business.exception", null, new Locale("TR"));
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Test
    public void it_should_handle_generic_exception() {
        //given
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        Exception exception = new Exception("default.exception.message");

        //when
        ResponseEntity<?> responseEntity = globalControllerExceptionHandler
                .handleException(exception, mockHttpServletRequest);

        //then
        verify(messageSource).getMessage("default.exception.message", null, new Locale("TR"));
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}