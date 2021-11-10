package com.tonypepe.fcuweb.lab4.e2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

@SuppressWarnings("DuplicatedCode")
public class Client {
    final static String host = "127.0.0.1";
    final static int port = 5678;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Socket client = new Socket(host, port);
            System.out.println("Connecting to server: " + client.getRemoteSocketAddress());
            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            final Scanner reader = new Scanner(client.getInputStream());
            Thread thread = new Thread(() -> {
                while (reader.hasNext()) {
                    String read = reader.nextLine();
                    System.out.println("Read: " + read);
                }
            });
            thread.start();
            while (scanner.hasNext()) {
                String str = scanner.nextLine();
                if (str.equals("Close")) {
                    break;
                }
                writer.write(str + "\n");
                writer.flush();
                System.out.println("Send to server " + str);
            }
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
