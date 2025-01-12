package com.tehila.coupons.exceptions;

import com.tehila.coupons.enums.ErrorType;

public class ServerException extends Exception {

    private ErrorType errorType;

    public ServerException(ErrorType errorType, String message) {
        super(errorType.getClientErrorMessage() + ": " + message);
        this.errorType = errorType;
    }

    public ServerException(ErrorType errorType) {
        this(errorType, "");
    }

    public ServerException(ErrorType errorType, Exception e) {
        super(errorType.getClientErrorMessage(), e);
        this.errorType = errorType;
    }

    public ServerException(ErrorType errorType, Exception e, String message) {
        super(errorType.getClientErrorMessage() + ": " +  message, e);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
