package com.hrupin.rosterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hrupin.rosterapp.api.ContactGroupResponse;
import com.hrupin.rosterapp.api.DataService;
import com.hrupin.rosterapp.api.ServerApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<ContactGroupResponse> request = ServerApiClient.start().getService().getContactGroups();
        request.enqueue(new Callback<ContactGroupResponse>() {
            @Override
            public void onResponse(Call<ContactGroupResponse> call, Response<ContactGroupResponse> response) {
                if(response.code() == 200){
                    Log.i(TAG, response.body().getGroups().toString());
                }else{
                    //TODO
                }
            }

            @Override
            public void onFailure(Call<ContactGroupResponse> call, Throwable t) {

            }
        });

    }
}
