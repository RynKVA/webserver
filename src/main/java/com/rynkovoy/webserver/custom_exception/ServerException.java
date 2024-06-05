package com.rynkovoy.webserver.custom_exception;

public class ServerException extends RuntimeException{
    StatusCode statusCode;

    public ServerException(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
