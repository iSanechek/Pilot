package com.isanechek.pilotproject.data.source.remote.network;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by isanechek on 11/15/16.
 */

public class OkHelper {
    public static String getBody(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response;
        try {
            response = ClientHttp
                    .getOkHttpClient()
                    .newCall(request)
                    .execute();
            return  response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream getStream(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response;

        try {
            response = ClientHttp
                    .getOkHttpClient()
                    .newCall(request)
                    .execute();
            return response.body().byteStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
