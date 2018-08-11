package com.company.campaign.api.enums;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandTypeTest {

    @Test
    public void it_should_find_create_product_bean_name_by_command() {
        //When
        String countryBeanName = CommandType.findCommandTypeBeanNameBy("create_product");

        //Then
        assertThat(countryBeanName).isEqualTo("productCreateExecutorService");
    }

    @Test
    public void it_should_return_default_bean_name_when_command_is_not_exist() {
        //When
        String countryBeanName = CommandType.findCommandTypeBeanNameBy("invalid_command");

        //Then
        assertThat(countryBeanName).isEqualTo("productInfoExecutorService");
    }
}