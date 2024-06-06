package com.rynkovoy.webserver.request_handler.request_parser;

import com.rynkovoy.webserver.custom_exception.ServerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.rynkovoy.webserver.custom_exception.StatusCode.BAD_REQUEST_ERROR;
import static com.rynkovoy.webserver.custom_exception.StatusCode.METHOD_NOT_ALLOWED_ERROR;

public class RequestParser {
    private static final Pattern HEADERS_PATTERN = Pattern.compile(": ");
    private static final Pattern FIRST_LINE_REQUEST_PATTERN = Pattern.compile(" ");

    public static Request parse(BufferedReader socketReader) throws IOException {
        Request request = new Request();
        List<String> requestsLines = requestLineDivider(socketReader);
        String requestFirstLine = requestsLines.removeFirst();
        injectUriAndMethod(requestFirstLine, request);
        injectHeaders(requestsLines, request);
        return request;
    }

    private static List<String> requestLineDivider(BufferedReader socketReader) throws IOException {
        String line;
        List<String> linesRequest = new ArrayList<>();
        while (!(line = socketReader.readLine()).isEmpty()) {
            linesRequest.add(line);
        }
        return linesRequest;
    }

    static void injectUriAndMethod(String requestFirstLine, Request request) throws ServerException {
        String[] split = FIRST_LINE_REQUEST_PATTERN.split(requestFirstLine);
        try {
            HttpMethod httpMethod = HttpMethod.valueOf(split[0]);
            if (httpMethod != HttpMethod.GET) {
                throw new ServerException(METHOD_NOT_ALLOWED_ERROR);
            }
            request.setMethod(httpMethod);
            request.setUri(split[1]);
        } catch (IllegalArgumentException e) {
            throw new ServerException(BAD_REQUEST_ERROR);
        }
    }

    static void injectHeaders(List<String> requestsLines, Request request) throws ServerException {
        for (String requestsLine : requestsLines) {
            try {
                String[] splitOnHeaders = HEADERS_PATTERN.split(requestsLine);
                request.getHeaders().put(splitOnHeaders[0], splitOnHeaders[1]);
            }catch (IndexOutOfBoundsException e){
                throw new ServerException(BAD_REQUEST_ERROR);
            }
        }
    }
}
