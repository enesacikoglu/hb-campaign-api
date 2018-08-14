package com.company.campaign.api.util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ObjectHelperTest {


    @Test
    public void it_should_return_true_when_object_is_not_present() {
        //given
        Object object = null;


        //when
        boolean  aNull= ObjectHelper.isNotPresent(object);

        //then
        assertTrue(aNull);
    }

    @Test
    public void it_should_return_true_when_object_is_present() {
        //given
        Object object = new Object();

        //when
        boolean notNull = ObjectHelper.isPresent(object);

        //then
        assertTrue(notNull);
    }

}
