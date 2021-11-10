package com.tonypepe.fcuweb.lab4.e3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("DuplicatedCode")
public class Server {
    private static final int port = 5678;
    private static final List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket;
        while ((socket = serverSocket.accept()) != null) {
            final Socket client = socket;
            clients.add(client);
            new Thread(() -> {
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                    Scanner scanner = new Scanner(client.getInputStream());
                    writer.write("Hi, you are user " + clients.size() + ".");
                    writer.newLine();
                    writer.flush();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Online users:");
                    for (int i = 0; i < clients.size(); i++) {
                        sb.append(" ").append(i + 1);
                    }
                    for (Socket c : clients) {
                        if (!c.isClosed()) {
                            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));
                            w.write(sb.toString());
                            w.newLine();
                            w.flush();
                        }
                    }
                    while (scanner.hasNext()) {
                        String line = scanner.nextLine();
                        System.out.println("Read: " + line);
                        String[] split = line.split(" ", 2);
                        String[] sendTo = split[0].split(",");
                        String message = split[1];
                        message = (clients.indexOf(client) + 1) + " said " + message;
                        if (sendTo[0].equals("0")) {
                            for (Socket c : clients) {
                                if (!c.isClosed()) {
                                    BufferedWriter w = new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));
                                    w.write(message);
                                    w.newLine();
                                    w.flush();
                                }
                            }
                        } else {
                            for (String s : sendTo) {
                                int to = Integer.parseInt(s) - 1;
                                BufferedWriter w = new BufferedWriter(new OutputStreamWriter(clients.get(to).getOutputStream()));
                                w.write(message);
                                w.newLine();
                                w.flush();
                            }
                        }
                    }
                    System.out.println("Client Closed");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        serverSocket.close();
    }
}
