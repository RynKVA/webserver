package com.rynkovoy.webserver.request_handler.response_writer;

import com.rynkovoy.webserver.custom_exception.StatusCode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.rynkovoy.webserver.custom_exception.StatusCode.OK;

public class ResponseWriter {

    public static void writeResponseToClient(InputStream content, OutputStream socketOutputStream) throws IOException {
        try (content) {
            getResponse(OK, socketOutputStream);
            byte[] buffer = new byte[2048];
            int count;
            while ((count = content.read(buffer)) != -1) {
                socketOutputStream.write(buffer, 0, count);
            }
        }
    }

    public static void writeError(StatusCode statusCode, OutputStream socketOutputStream) throws IOException {
        getResponse(statusCode, socketOutputStream);
    }

    private static void getResponse(StatusCode statusCod, OutputStream socketOutputStream) throws IOException {
        socketOutputStream.write(("HTTP/1.1 " + statusCod.getCode() + " " + statusCod.getStatusText() + "\r\n\r\n").getBytes());
    }
}
