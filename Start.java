package com.company.Start;

import com.company.client.TCPClient;
import com.company.server.TCPServer;

import java.io.IOException;

public class Start {
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length >= 2) {
            TCPClient.main(args);
            return;
        }
        TCPServer.main(args);
    }
}
