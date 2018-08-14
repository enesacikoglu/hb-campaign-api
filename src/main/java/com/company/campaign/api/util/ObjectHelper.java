package com.company.campaign.api.util;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

public class ObjectHelper {

    private ObjectHelper() {
    }

    public static boolean isPresent(Object object) {
        return isNotNull(object);
    }

    public static boolean isNotPresent(Object object) {
        return isNull(object);
    }

    private static boolean isNull(Object object) {
        return object == null;
    }

    private static boolean isNotNull(Object object) {
        return !isNull(object);
    }

}
