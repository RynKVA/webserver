package com.rynkovoy.webserver.request_handler;


import com.rynkovoy.webserver.custom_exception.ServerException;
import com.rynkovoy.webserver.request_handler.request_parser.Request;
import com.rynkovoy.webserver.request_handler.request_parser.RequestParser;
import com.rynkovoy.webserver.request_handler.resource_reader.ResourceReader;
import com.rynkovoy.webserver.request_handler.response_writer.ResponseWriter;

import java.io.*;

import static com.rynkovoy.webserver.custom_exception.StatusCode.*;


public class RequestHandler {
    public static void handle(BufferedReader socketReader, OutputStream socketOutputStream, String webAppPath) throws IOException {
        try {
            Request request = RequestParser.parse(socketReader);
            InputStream content = ResourceReader.readResource(request.getUri(), webAppPath);
            ResponseWriter.writeResponseToClient(content, socketOutputStream);
        } catch (ServerException e) {
            ResponseWriter.writeError(e.getStatusCode(), socketOutputStream);
        } catch (Exception e) {
            ResponseWriter.writeError(ILLEGAL_SERVER_ERROR, socketOutputStream);
        }
    }
}
