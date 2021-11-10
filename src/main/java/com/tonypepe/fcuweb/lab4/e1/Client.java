package com.tonypepe.fcuweb.lab4.e1;

import java.io.*;
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
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            Scanner reader = new Scanner(client.getInputStream());
            while (scanner.hasNext()) {
                String str = scanner.nextLine();
                if (str.contains("@")) {
                    str = "*".repeat(str.length());
                }
                writer.write(str + "\n");
                writer.flush();
                System.out.println("Send to server " + str);
                // Read
                String read = reader.nextLine();
                System.out.println("Read: " + read);
            }
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
