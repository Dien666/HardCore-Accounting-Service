package com.hardcore.accounting.exception;

import org.springframework.http.HttpStatus;

/**
 * HC Accounting Service ResourceNotFoundException
 */
public class ResourceNotFoundException extends ServiceException {

    public ResourceNotFoundException(String message) {
        super(message);
        this.setStatusCode(HttpStatus.NOT_FOUND.value());
        this.setErrorCode("USER_INFO_NOT_FOUND");
        this.setErrorType(ErrorType.Client);
    }
}
