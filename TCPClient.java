package com.company.client;

import java.io.*;
import java.net.Socket;

public class TCPClient {
    private String name;
    private int portnr;

    public static final String HOST = "localhost";
    public static final int PORT = 7777;

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length < 2) {
            System.out.println("missing parameters");
        }

        String hostname = args[0];
        String portstring = args[1];
        String filename = null;
        if(args.length > 2) {
            filename = args[2];
        }


        int portnr = Integer.parseInt(portstring);
        if (args.length > 2) {
            TCPClient client = new TCPClient(hostname, portnr);

            client.copyfile(filename);
        } else {
            TCPClient client = new TCPClient(hostname, portnr);

            //client.connect();
            long timestamp = System.currentTimeMillis();
            float value = (float) 42.0;
            String sensorName = "Sensor A";
            client.sendSensorData(timestamp, value, sensorName);
        }

    }
    TCPClient(String name, int port) {
        this.name = name;
        this.portnr = port;
    }
    private void copyfile(String filename) throws IOException {
        Socket socket = new Socket(this.name, this.portnr);

        FileInputStream is = new FileInputStream(filename);

        OutputStream os = socket.getOutputStream();
        int i = 0;
        do {
            i = is.read();
            if(i != -1) {
                os.write(i);
            }
        }while (i != -1);
        os.close();
    }
    private void sendSensorData(long timestamp, float value, String sensorname) throws IOException {
        Socket socket = new Socket(this.name, this.portnr);
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeLong(timestamp);
        dos.writeFloat(value);
        dos.writeUTF(sensorname);

        os.close();

    }

    private void connect() throws IOException, InterruptedException {
        Socket socket = new Socket(this.name, this.portnr);
        System.out.println("verbindet");

        OutputStream os = socket.getOutputStream();
        os.write("eine Sendung".getBytes());

        InputStream is = socket.getInputStream();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int in = 0;

        do {
            in = is.read();

            if(in != -1) {
                baos.write(in);
            }

        } while (in != -1);

        String nachricht = new String(baos.toByteArray());
        System.out.println("server sagt: " + nachricht);



        Thread.sleep(5000);
        System.out.println("schlaf");

        os.close();


    }

}
