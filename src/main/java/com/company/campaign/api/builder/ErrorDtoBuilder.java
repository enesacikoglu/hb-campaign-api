package com.company.campaign.api.builder;

import com.company.campaign.api.model.dto.error.ErrorDto;

public final class ErrorDtoBuilder {
    private String title;
    private int status;
    private String detail;
    private String requestUri;
    private String requestMethod;
    private String instant;

    private ErrorDtoBuilder() {
    }

    public static ErrorDtoBuilder anErrorDto() {
        return new ErrorDtoBuilder();
    }

    public ErrorDtoBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ErrorDtoBuilder status(int status) {
        this.status = status;
        return this;
    }

    public ErrorDtoBuilder detail(String detail) {
        this.detail = detail;
        return this;
    }

    public ErrorDtoBuilder requestUri(String requestUri) {
        this.requestUri = requestUri;
        return this;
    }

    public ErrorDtoBuilder requestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public ErrorDtoBuilder instant(String instant) {
        this.instant = instant;
        return this;
    }

    public ErrorDto build() {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setTitle(title);
        errorDto.setStatus(status);
        errorDto.setDetail(detail);
        errorDto.setRequestUri(requestUri);
        errorDto.setRequestMethod(requestMethod);
        errorDto.setInstant(instant);
        return errorDto;
    }
}
