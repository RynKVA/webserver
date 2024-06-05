package com.rynkovoy.webserver.custom_exception;

public enum StatusCode {
    OK(200, "OK"),
    BAD_REQUEST_ERROR(400, "Bad Request"),
    METHOD_NOT_ALLOWED_ERROR(405, "Method Not Allowed"),
    NOT_FOUND_ERROR(404, "Not Found"),
    ILLEGAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String statusText;

    StatusCode(int code, String statusText) {
        this.code = code;
        this.statusText = statusText;
    }

    public int getCode() {
        return code;
    }

    public String getStatusText() {
        return statusText;
    }
}
