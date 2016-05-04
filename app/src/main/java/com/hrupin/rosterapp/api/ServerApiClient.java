package com.hrupin.rosterapp.api;

import com.google.gson.Gson;

import retrofit2.Retrofit;

/**
 * Created by Igor Khrupin www.hrupin.com on 5/4/16.
 */
public class ServerApiClient {

    private static ServerApiClient singleton = new ServerApiClient();
    private DataService dataService;

    private ServerApiClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://downloadapp.youwow.me/")
                .build();

        dataService = retrofit.create(DataService.class);
    }

    public static ServerApiClient getInstance() {
        return singleton;
    }

    public DataService getDataService() {
        return dataService;
    }
}
