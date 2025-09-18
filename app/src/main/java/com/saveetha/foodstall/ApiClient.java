package com.saveetha.foodstall;

import java.util.concurrent.TimeUnit; // ADDED THIS IMPORT
import okhttp3.OkHttpClient; // ADDED THIS IMPORT
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is a Singleton that creates and provides a single, shared instance of Retrofit.
 * This is the central point for configuring how the app communicates with the server.
 */
public class ApiClient {

    public static final String BASE_URL = "https://s6c4sdvs-80.inc1.devtunnels.ms/foodstall/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            // --- START OF UPDATED SECTION ---
            // Create an OkHttpClient with custom timeouts to prevent network errors
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) // Time to establish a connection
                    .readTimeout(30, TimeUnit.SECONDS)    // Time to wait for data
                    .writeTimeout(30, TimeUnit.SECONDS)   // Time to wait for sending data
                    .build();
            // --- END OF UPDATED SECTION ---

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    // --- START OF UPDATED SECTION ---
                    // Attach the custom client to Retrofit
                    .client(okHttpClient)
                    // --- END OF UPDATED SECTION ---
                    .build();
        }
        return retrofit;
    }
}