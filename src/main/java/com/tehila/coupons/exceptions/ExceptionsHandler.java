package com.tehila.coupons.exceptions;


import com.tehila.coupons.dto.ErrorBean;
import com.tehila.coupons.enums.ErrorType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;


@RestControllerAdvice
public class ExceptionsHandler {

    //	Response - Object in Spring
    @ExceptionHandler
    // Variable name is throwable in order to remember that it handles Exception and Error
    public ErrorBean toResponse(Throwable throwable, HttpServletResponse response) {

        //	ErrorBean errorBean;
        if(throwable instanceof ServerException) {
            ServerException serverException = (ServerException) throwable;

            ErrorType errorType = serverException.getErrorType();
            int errorNumber = errorType.getInternalError();
            String errorMessage = errorType.getClientErrorMessage();
            int httpStatus = errorType.getHttpStatus();

            ErrorBean errorBean = new ErrorBean(errorNumber, errorMessage);
            response.setStatus(httpStatus);

            if(serverException.getErrorType().isShowStackTrace()) {
                serverException.printStackTrace();
            }

            return errorBean;
        }

        String errorMessage = throwable.getMessage();
        ErrorBean errorBean = new ErrorBean(601, "General error");
        throwable.printStackTrace();

        return errorBean;
    }

}
