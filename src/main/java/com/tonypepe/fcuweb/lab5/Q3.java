package com.tonypepe.fcuweb.lab5;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Q3 {
    private static final String TOKEN = "";

    public static void main(String[] args) throws UnirestException {
        String query = "";
        HttpResponse<String> response = Unirest.get("https://imdb-internet-movie-database-unofficial.p.rapidapi.com/film/" + query)
                .header("x-rapidapi-host", "imdb-internet-movie-database-unofficial.p.rapidapi.com")
                .header("x-rapidapi-key", TOKEN)
                .asString();
        Gson gson = new Gson();
        Imdb imdb = gson.fromJson(response.getBody(), Imdb.class);
        System.out.println(query);
        System.out.println("Title: " + imdb.title);
        System.out.println("Rating: " + imdb.rating);
    }

    private class Imdb {
        public String id;
        public String title;
        public String year;
        public String length;
        public String rating;
        public String rating_votes;
        public String poster;
        public String plot;
    }
}
