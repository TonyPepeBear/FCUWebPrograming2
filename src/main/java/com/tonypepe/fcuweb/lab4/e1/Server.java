package com.tonypepe.fcuweb.lab4.e1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

@SuppressWarnings("DuplicatedCode")
public class Server extends Thread {
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10 * 1000);
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.printf("Waiting for client port %d...\n", serverSocket.getLocalPort());
                Socket server = serverSocket.accept();
                System.out.println("Just connecting to " + server.getRemoteSocketAddress());
                Scanner reader = new Scanner(server.getInputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));

                while (reader.hasNext()) {
                    String line = reader.nextLine();
                    System.out.println(line);
                    writer.write(line + "\n");
                    writer.flush();
                }

                server.close();
            } catch (SocketTimeoutException e) {
                System.out.println("Socket timed out!");
                break;
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(5678);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
