package com.hrupin.rosterapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Igor Khrupin www.hrupin.com on 5/4/16.
 */
public class ServerApiClient {

    private static ServerApiClient singleton;
    private static DataService service;

    private ServerApiClient() {
    }

    public static ServerApiClient start(){
        if (singleton == null) {
            synchronized (ServerApiClient.class) {
                if (singleton == null) {
                    singleton = new ServerApiClient();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://downloadapp.youwow.me")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    service = retrofit.create(DataService.class);
                }
            }
        }
        return singleton;
    }

    public DataService getService() {
        return service;
    }
}
