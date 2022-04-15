package com.hardcore.accounting.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorResponse {
    private BizErrorCode code;
    private ServiceException.ErrorType errorType;
    private String message;
    private int statusCode; // Httpstatus also OK
}
