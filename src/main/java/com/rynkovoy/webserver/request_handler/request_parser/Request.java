package com.rynkovoy.webserver.request_handler.request_parser;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String uri;
    private final Map<String, String> headers;
    private HttpMethod method;

    Request() {
        headers = new HashMap<>();
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getUri() {
        return uri;
    }
}
