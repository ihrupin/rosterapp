package com.hrupin.rosterapp.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.hrupin.rosterapp.api.ContactGroupResponse;
import com.hrupin.rosterapp.api.ServerApiClient;
import com.hrupin.rosterapp.model.Group;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Igor Khrupin www.hrupin.com on 5/5/16.
 */
public class ContactsLoader extends AsyncTaskLoader<Result<List<Group>>> {
    private static final String TAG = "ContactsLoader";
    private Result<List<Group>> mData;

    public ContactsLoader(Context context) {
        super(context);
    }

    @Override
    public Result<List<Group>> loadInBackground() {
        Call<ContactGroupResponse> request = ServerApiClient.start().getService().getContactGroups();
        try{
            Response<ContactGroupResponse> response = request.execute();
            if(response != null) {
                if (response.isSuccessful()) {
                    Log.i(TAG, response.body().getGroups().toString());
                    if (response.body() != null) {
                        ContactGroupResponse body = response.body();
                        if(body != null){
                            List<Group> groups = body.getGroups();
                            if(groups.isEmpty()){
                                return new Result<>(DataStatus.NO_DATA, null);
                            }else{
                                return new Result<>(DataStatus.OK, groups);
                            }
                        }
                    }
                }
            }
            return new Result<>(DataStatus.SERVER_ERROR, null);
        }catch (IOException e){
            return new Result<>(DataStatus.IO_EXCEPTION, null);
        }
    }

    @Override
    public void deliverResult(Result<List<Group>> data) {
        if (isReset()) {
            return;
        }
        mData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mData != null && mData.getStatus() == DataStatus.OK) {
            deliverResult(mData);
        }else {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
    }
}
