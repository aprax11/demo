package com.company.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TCPServer {
    private final int portnumb;
    private static final int Port = 7777;

    public static void main (String[] args) throws IOException, InterruptedException {
        TCPServer server = new TCPServer(Port);
        if(args.length == 1) {
            server.readfile(args[0]);
        } else {
            //server.act();
            server.recieveSensorData();
        }
   }
    private void recieveSensorData() throws IOException {
        Socket socket = this.socketaccept();
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        long timestamp = dis.readLong();
        float value = dis.readFloat();
        String sensorName = dis.readUTF();
        Date date = new Date();
        System.out.println("timestamp ist " + date);
        System.out.println("value ist " + value);
        System.out.println("sensor Name ist " + sensorName);

    }

    private void readfile(String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        Socket socket = this.socketaccept();
        InputStream is = socket.getInputStream();

        int i = 0;
        do{
            i = is.read();
            if(i != -1) {
                fos.write(i);
            }
        }while (i != -1);
        fos.close();
    }

    TCPServer(int port) {
       this.portnumb = port;
   }

   private Socket socketaccept() throws IOException {
       ServerSocket server = new ServerSocket(this.portnumb);
       System.out.println("Socket created");
       Socket socket = server.accept();
       System.out.println("er lauscht");

       return socket;
   }
   private void act() throws IOException, InterruptedException {
        Socket socket = this.socketaccept();

        socket.getInputStream().read();
       System.out.println("er liest");

       OutputStream os = socket.getOutputStream();
       os.write("=D".getBytes());
       System.out.println("er schreibt");

       Thread.sleep(5000);
       System.out.println("er pennt");

       os.close();



    }

}
