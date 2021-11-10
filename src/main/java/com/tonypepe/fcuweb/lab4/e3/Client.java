package com.tonypepe.fcuweb.lab4.e3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("DuplicatedCode")
public class Client {
    private static final int port = 5678;
    private static final String host = "127.0.0.1";

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(host, port);
        final Scanner scanner = new Scanner(System.in);
        final Scanner reader = new Scanner(socket.getInputStream());
        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        List<Integer> sendTo = new ArrayList<>();

        Thread readThread = new Thread(() -> {
            while (reader.hasNext()) {
                String line = reader.nextLine();
                System.out.println(line);
            }
        });
        readThread.start();
        int mode = 0;
        System.out.println("(1) Broadcast\n(2) Private\n(3) Group\nChoose a function: ");
        mode = scanner.nextInt();
        scanner.nextLine();
        if (mode == 2) {
            System.out.println("Enter the user id: ");
            sendTo.add(scanner.nextInt());
            scanner.nextLine();
        }
        if (mode == 3) {
            String[] nums = scanner.nextLine().split(",");
            for (String num : nums) {
                sendTo.add(Integer.parseInt(num));
            }
        }
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            if (str.equals("Return")) {
                sendTo.clear();
                System.out.println("(1) Broadcast\n(2) Private\n(3) Group\nChoose a function: ");
                mode = scanner.nextInt();
                scanner.nextLine();
                if (mode == 2) {
                    System.out.println("Enter the user id: ");
                    sendTo.add(scanner.nextInt());
                    scanner.nextLine();
                }
                if (mode == 3) {
                    String[] nums = scanner.nextLine().split(",");
                    for (String num : nums) {
                        sendTo.add(Integer.parseInt(num));
                    }
                }
                continue;
            }
            StringBuilder sb = new StringBuilder();
            if (sendTo.size() == 0) {
                sb.append(0);
            } else {
                sb.append(sendTo.get(0));
                for (int i = 1; i < sendTo.size(); i++) {
                    sb.append(",").append(sendTo.get(i));
                }
            }
            sb.append(" ").append(str);
            writer.write(sb.toString());
            writer.newLine();
            writer.flush();
        }
        socket.close();
    }
}
