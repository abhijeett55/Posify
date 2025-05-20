package com.example.posify;

import okhttp3.Request;

public class SupabaseClient {
    public static final String BASE_URL = "https://sfwkwfhdcxbtwqbulmzv.supabase.co/rest/v1/";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNmd2t3ZmhkY3hidHdxYnVsbXp2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDc1NDc3MjcsImV4cCI6MjA2MzEyMzcyN30.DaorlyXkgMhHx1qihtpCAXoWs5dntxWRDL7vCoQ8U0s";


    public static Request.Builder addHeaders(Request.Builder builder) {
        return builder
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json");
    }



}
