package com.company.campaign.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

    private ExceptionHandler() {
    }

    public static <T, E extends Exception> void handleThrowable(ExceptionalSupplier<T, E> exceptionalSupplier) {
        try {
            exceptionalSupplier.get();
        } catch (Exception exception) {
            System.out.println("Error occurred, detail: " + exception);
            LOG.error("Error occurred, detail: {}", exception.
                    getMessage());
        }
    }
}
