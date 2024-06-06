package com.rynkovoy.webserver.request_handler.request_parser;

import com.rynkovoy.webserver.custom_exception.ServerException;
import com.rynkovoy.webserver.request_handler.resource_reader.ResourceReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.rynkovoy.webserver.request_handler.request_parser.HttpMethod.GET;
import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    Request request;

    @BeforeEach
    void before() {
        request = new Request();
    }

    @Test
    @DisplayName("When use injectUriAndMethod on first line client request then exemplar Request filling uri and method fields")
    void whenUseInjectUriAndMethod_ThenExemplarRequestFillingUriAndMethod() {
        RequestParser.injectUriAndMethod("GET /index.html HTTP/1.1", request);
        assertEquals(GET, request.getMethod());
        assertEquals("/index.html", request.getUri());
    }

    @Test
    @DisplayName("When use injectUriAndMethod with not allowed method then expect ServerException with statusCode 405 Method Not Allowed")
    void whenUseInjectUriAndMethodWithNotAllowedMethod_ThenExpectServerExceptionWithStatusCode405() {
        ServerException serverException = assertThrows(ServerException.class,
                () -> RequestParser.injectUriAndMethod("POST /index.html HTTP/1.1", request));
        assertEquals(405, serverException.getStatusCode().getCode());
        assertEquals("Method Not Allowed", serverException.getStatusCode().getStatusText());
    }

    @Test
    @DisplayName("When use injectUriAndMethod with not exist method then expect ServerException with statusCode 400 Bad Request")
    void whenUseInjectUriAndMethodWithNotExistMethod_ThenExpectServerExceptionWithStatusCode400() {
        ServerException serverException = assertThrows(ServerException.class,
                () -> RequestParser.injectUriAndMethod("CLEAR /index.html HTTP/1.1", request));
        assertEquals(400, serverException.getStatusCode().getCode());
        assertEquals("Bad Request", serverException.getStatusCode().getStatusText());
    }

    @Test
    @DisplayName("When use injectHeaders on divider headers lines then exemplar Request filling headers map")
    void fillingRequestHeaders() {
        List<String> headers = new ArrayList<>(3);
        headers.add("Host: ru.wikipedia.org");
        headers.add("User-Agent: Mozilla/5.0 (X11; U; Linux i686; ru; rv:1.9b5) Gecko/2008050509 Firefox/3.0b5");
        headers.add("Accept: text/html");

        RequestParser.injectHeaders(headers, request);

        assertEquals("ru.wikipedia.org", request.getHeaders().get("Host"));
        assertEquals("Mozilla/5.0 (X11; U; Linux i686; ru; rv:1.9b5) Gecko/2008050509 Firefox/3.0b5", request.getHeaders().get("User-Agent"));
        assertEquals("text/html", request.getHeaders().get("Accept"));
    }

    @Test
    @DisplayName("When use injectHeaders on divider headers lines which has wrong request protocol then expect ServerException with statusCode 400 Bad Request")
    void wrongFillingRequestHeaders() {
        List<String> headers = new ArrayList<>(3);
        headers.add("Host ru.wikipedia.org");

        ServerException serverException = assertThrows(ServerException.class,
                () -> RequestParser.injectHeaders(headers, request));
        assertEquals(400, serverException.getStatusCode().getCode());
        assertEquals("Bad Request", serverException.getStatusCode().getStatusText());
    }

    @Test
    @DisplayName("When FIle which requested is not found then expect ServerException with statusCode 404 Not Found")
    void whenUriFileNotFound() {
        ServerException serverException = assertThrows(ServerException.class,
                () -> ResourceReader.readResource("photo.jpg", "src\\main\\resources\\webapp"));
        assertEquals(404, serverException.getStatusCode().getCode());
        assertEquals("Not Found", serverException.getStatusCode().getStatusText());
    }
}

