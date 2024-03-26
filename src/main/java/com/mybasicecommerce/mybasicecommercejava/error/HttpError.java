package com.mybasicecommerce.mybasicecommercejava.error;

public class HttpError extends RuntimeException {
    private int statusCode;

    public HttpError(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
