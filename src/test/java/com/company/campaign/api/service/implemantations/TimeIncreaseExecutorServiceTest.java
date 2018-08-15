package com.company.campaign.api.service.implemantations;


import com.company.campaign.api.service.PriceManipulatorService;
import com.company.campaign.api.util.TimeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TimeIncreaseExecutorServiceTest {

    @InjectMocks
    private TimeIncreaseExecutorService timeIncreaseExecutorService;

    @Mock
    private PriceManipulatorService priceManipulatorService;

    @Test
    public void it_should_increment_time() {
        //given
        TimeHelper.reset();
        String command = "time_increase 2";

        //when
        Long time = timeIncreaseExecutorService.executeCommand(command);

        //then
        assertThat(time).isEqualTo(2L);
        verify(priceManipulatorService).manipulate(2.00);
    }
}