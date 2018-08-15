package com.company.campaign.api.exception;

import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class CampaignApiBusinessException extends RuntimeException {

    private static final long serialVersionUID = 1145523870268478407L;
    private final String messageKey;

    public CampaignApiBusinessException(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("messageKey", messageKey)
                .toString();
    }
}