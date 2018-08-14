package com.company.campaign.api.util;

public class TimeHelper {

    private static Long currentTime = 0L;

    private TimeHelper() {
    }

    public static Long incrementTime(Long timeToIncrement) {
        currentTime += timeToIncrement;
        return currentTime;
    }

    public static Long decrementTime(Long timeToIncrement) {
        currentTime -= timeToIncrement;
        return currentTime;
    }

    public static Long getCurrentTime() {
        return currentTime;
    }

    public static void reset() {
        currentTime = 0L;
    }
}
