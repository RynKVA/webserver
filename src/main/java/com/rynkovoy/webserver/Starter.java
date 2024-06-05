package com.rynkovoy.webserver;

import com.rynkovoy.webserver.server.Server;

import java.io.IOException;

public class Starter {
    public static void main(String[] args) throws IOException {
        Server server = new Server(3000, "src\\main\\resources\\webapp");
        server.start();
    }
}
