package com.tonypepe.fcuweb.lab5;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class Q2 {
    private static final String TOKEN = "";
    private static final String FILE_PATH = "";

    public static void main(String[] args) throws IOException, UnirestException {
        byte[] bytes = Files.readAllBytes(Path.of(FILE_PATH));
        String base64 = Base64.getEncoder().encodeToString(bytes);

        HttpResponse<String> response = Unirest.post("https://face-similarity.p.rapidapi.com/FaceSimilar")
                .header("x-rapidapi-host", "face-similarity.p.rapidapi.com")
                .header("x-rapidapi-key", TOKEN)
                .field("photo", new File(FILE_PATH))
                .asString();

        String body = response.getBody();
        Gson gson = new Gson();
        PhotoData photo = gson.fromJson(body, PhotoData.class);
        System.out.println(photo.data.name);
    }

    private class PhotoData {
        public int statusCode;
        public String statusMessage;
        public boolean hasError;
        public Data data;

        private class Data {
            public String name;
            public String group;
            public String imageBase64;
            public String imageMimeType;
            public int similarPercentage;
            public double similarDistance;
        }
    }
}
