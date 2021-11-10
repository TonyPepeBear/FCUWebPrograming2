package com.tonypepe.fcuweb;

public class Hello {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(i);
            }
        });
        thread.start();
        System.out.println("Hello");
//        thread.join();
    }
}
