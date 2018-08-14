package com.company.campaign.api.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeHelperTest {

    @Test
    public void it_should_return_current_time() {
        //given
        TimeHelper.reset();
        TimeHelper.incrementTime(2L);

        //when
        Long currentTime = TimeHelper.getCurrentTime();

        //then
        assertThat(currentTime).isEqualTo(2L);
    }


    @Test
    public void it_should_increment_time() {
        //given
        TimeHelper.reset();
        //when
        Long incrementedTime = TimeHelper.incrementTime(2L);

        //then
        assertThat(incrementedTime).isEqualTo(2L);
    }


    @Test
    public void it_should_decrement_time() {
        //given
        TimeHelper.reset();
        TimeHelper.incrementTime(6L);

        //when
        Long incrementedTime = TimeHelper.decrementTime(2L);

        //then
        assertThat(incrementedTime).isEqualTo(4L);
    }

    @Test
    public void it_should_reset_time() {
        //given
        TimeHelper.reset();

        //when
        Long incrementedTime = TimeHelper.getCurrentTime();

        //then
        assertThat(incrementedTime).isEqualTo(0L);
    }
}