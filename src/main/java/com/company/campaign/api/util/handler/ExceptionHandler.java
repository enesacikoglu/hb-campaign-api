package com.company.campaign.api.util.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

    private ExceptionHandler() {
    }

    public static <T, E extends Exception> void handleThrowable(ExceptionalSupplier<T, E> exceptionalSupplier) throws E {
        try {
            exceptionalSupplier.get();
        } catch (Exception exception) {
            LOG.error("Error occurred, detail: {}", exception.
                    getMessage());
            throw exception;
        }
    }
}
