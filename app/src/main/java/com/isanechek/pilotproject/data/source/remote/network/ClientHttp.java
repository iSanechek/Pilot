package com.isanechek.pilotproject.data.source.remote.network;

import okhttp3.OkHttpClient;

/**
 * Created by isanechek on 11/15/16.
 */

public class ClientHttp {
    private static OkHttpClient client;

    public static void init() {
        client = new OkHttpClient.Builder().build();
    }

    public static OkHttpClient getOkHttpClient() {
        return client;
    }
}
