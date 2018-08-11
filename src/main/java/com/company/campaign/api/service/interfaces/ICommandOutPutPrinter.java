package com.company.campaign.api.service.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public interface ICommandOutPutPrinter {

    Logger LOGGER = LoggerFactory.getLogger(ICommandOutPutPrinter.class);

    default void print(String outPut) {
        LOGGER.debug(outPut);
        System.out.println(outPut);
    }

}
