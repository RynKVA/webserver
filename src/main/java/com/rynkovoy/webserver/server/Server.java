package com.rynkovoy.webserver.server;

import com.rynkovoy.webserver.request_handler.RequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;
    private final String webAppPath;

    public Server(int port, String webAppPath) {
        this.port = port;
        this.webAppPath = webAppPath;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     OutputStream socketOutputStream = socket.getOutputStream()) {
                    RequestHandler.handle(socketReader, socketOutputStream, webAppPath);
                }
            }
        }
    }
}
