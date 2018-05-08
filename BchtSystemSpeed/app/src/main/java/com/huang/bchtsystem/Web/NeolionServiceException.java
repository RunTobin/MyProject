package com.huang.bchtsystem.Web;

import com.huang.bchtsystem.Model.ErrorResponse;

/**
 * Created by admin on 2017/3/13.
 */

public class NeolionServiceException extends Exception {

    private ErrorResponse errorResponse;

    public NeolionServiceException() {
    }

    public NeolionServiceException(String detailMessage) {
        super(detailMessage);
    }

    public NeolionServiceException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NeolionServiceException(Throwable throwable) {
        super(throwable);
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}

