package com.tonypepe.fcuweb.lab5;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Scanner;

public class Q1 {
    private static final String TOKEN = "";

    public static void main(String[] args) throws UnirestException {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            HttpResponse<String> response = Unirest.post("https://waifu-ai.p.rapidapi.com/path")
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("x-rapidapi-host", "waifu-ai.p.rapidapi.com")
                    .header("x-rapidapi-key", TOKEN)
                    .body("message=" + line + "&translate_from=en")
                    .asString();
            System.out.println(response.getBody());
        }
    }
}
