package com.hrupin.rosterapp.api;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by Igor Khrupin www.hrupin.com on 5/4/16.
 */
public interface DataService {

    @GET("iPhone/iOSTest/contacts.json")
    Call<ContactGroupResponse> getContactGroups();
}
