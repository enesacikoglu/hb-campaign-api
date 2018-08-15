package com.company.campaign.api.controller.exception;


import com.company.campaign.api.builder.ErrorDtoBuilder;
import com.company.campaign.api.exception.CampaignApiBusinessException;
import com.company.campaign.api.exception.CampaignApiDomainNotFoundException;
import com.company.campaign.api.model.dto.error.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Locale;

import static com.company.campaign.api.constant.MessageKeyConstants.MESSAGE_KEY_DEFAULT_EXCEPTION;


@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
    private static final Locale TR = new Locale("tr");
    private final MessageSource messageSource;


    @Autowired
    public GlobalControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception exception,HttpServletRequest request) {
        LOGGER.error("Exception: {}", exception.toString());
        String message = messageSource.getMessage(MESSAGE_KEY_DEFAULT_EXCEPTION, null, TR);
        ErrorDto errorDto = ErrorDtoBuilder.anErrorDto()
                .requestUri(request.getRequestURI())
                .requestMethod(request.getMethod())
                .instant(Instant.now().toString())
                .title(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .detail(message)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CampaignApiBusinessException.class)
    public ResponseEntity<ErrorDto> handleApiBusinessException(CampaignApiBusinessException exception, HttpServletRequest request) {
        LOGGER.error("CampaignApiBusinessException: {}", exception.toString());
        String message = messageSource.getMessage(exception.getMessageKey(), null, TR);
        ErrorDto errorDto = ErrorDtoBuilder.anErrorDto()
                .requestUri(request.getRequestURI())
                .requestMethod(request.getMethod())
                .instant(Instant.now().toString())
                .title(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .detail(message)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CampaignApiDomainNotFoundException.class)
    public ResponseEntity<ErrorDto> handleApiDomainNotFoundException(CampaignApiDomainNotFoundException exception, HttpServletRequest request) {
        LOGGER.error("CampaignApiDomainNotFoundException: {}", exception.toString());
        String message = messageSource.getMessage(exception.getMessageKey(), null, TR);
        ErrorDto errorDto = ErrorDtoBuilder.anErrorDto()
                .requestUri(request.getRequestURI())
                .requestMethod(request.getMethod())
                .instant(Instant.now().toString())
                .title(HttpStatus.NOT_FOUND.getReasonPhrase())
                .detail(message)
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }
}
