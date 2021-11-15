package com.tonypepe.fcuweb.lab4.e2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("DuplicatedCode")
public class Server extends Thread {
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private final ArrayList<Socket> servers = new ArrayList<>();

    @Override
    public void run() {
        while (true) {
            try {
                System.out.printf("Waiting for client port %d...\n", serverSocket.getLocalPort());
                Socket server;
                while ((server = serverSocket.accept()) != null) {
                    final Socket socket = server;
                    if (servers.size() >= 4) {
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        writer.write("Server is Full\n");
                        writer.flush();
                        socket.close();
                        continue;
                    }
                    System.out.println("Connect " + socket.getInetAddress() + ":" + socket.getPort());
                    servers.add(socket);
                    Thread thread = new Thread(() -> {
                        try {
                            Scanner scanner = new Scanner(socket.getInputStream());
                            while (scanner.hasNext()) {
                                String readLine = scanner.nextLine();
                                for (Socket target : servers) {
                                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(target.getOutputStream()));
                                    writer.write(readLine + "\n");
                                    writer.flush();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    thread.start();
                }
                serverSocket.close();
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
