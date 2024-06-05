package com.rynkovoy.webserver.request_handler.resource_reader;

import com.rynkovoy.webserver.custom_exception.ServerException;

import java.io.*;

import static com.rynkovoy.webserver.custom_exception.StatusCode.NOT_FOUND_ERROR;

public class ResourceReader {

    public static InputStream readResource(String uri, String webAppPath) throws ServerException {
        try {
            return new FileInputStream(new File(webAppPath, uri));
        } catch (FileNotFoundException e) {
            throw new ServerException(NOT_FOUND_ERROR);
        }
    }
}
