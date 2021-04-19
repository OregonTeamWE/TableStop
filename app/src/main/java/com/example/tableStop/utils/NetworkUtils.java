package com.example.tableStop.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.tableStop.TableStopApp;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUtils {
    public static final String BaseUrl = "https://api.ebay.com";

    private static final OkHttpClient mHTTPClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(5, TimeUnit.MINUTES) // write timeout
            .readTimeout(5, TimeUnit.MINUTES) // read timeout
            .build();

    public static String doHttpGet(String url, String token) throws IOException {
        Log.d("Url: ", url);
        Request request = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + token).build();
        Response response = mHTTPClient.newCall(request).execute();
        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }

    public static String doHttpPost(String url) throws IOException {
        RequestBody formBody = new FormBody.Builder().addEncoded("grant_type", "client_credentials")
                .addEncoded("scope", "https://api.ebay.com/oauth/api_scope").build();
        Request request = new Request.Builder().url(url)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", Credentials.basic(TableStopApp.ClientID, TableStopApp.ClientSecret))
                .post(formBody).build();
        Response response = mHTTPClient.newCall(request).execute();

        try {
            return response.body().string();
        } finally {
            response.close();
        }

    }

    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;

        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }
}