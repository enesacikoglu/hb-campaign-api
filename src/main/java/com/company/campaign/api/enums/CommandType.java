package com.company.campaign.api.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum CommandType {

    CREATE_PRODUCT("productCreate"),
    GET_PRODUCT_INFO("productInfo"),
    CREATE_ORDER("orderCreate"),
    CREATE_CAMPAIGN("campaignCreate"),
    GET_CAMPAIGN_INFO("campaignInfo"),
    INCREASE_TIME("timeIncrease");

    public static final String SERVICE_SUFFIX = "ExecutorService";

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public static String findCommandTypeBeanNameBy(String command) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> StringUtils.equals(commandType.name().toLowerCase(), command))
                .findFirst()
                .orElse(CommandType.GET_PRODUCT_INFO)
                .command + SERVICE_SUFFIX;
    }
}
