package com.hrupin.rosterapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hrupin.rosterapp.loader.ContactsLoader;
import com.hrupin.rosterapp.loader.Result;
import com.hrupin.rosterapp.model.Group;

import java.util.List;

/**
 * Created by Igor Khrupin www.hrupin.com on 5/5/16.
 */
public class ContactsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Result<List<Group>>>{

    private static final String TAG = "ContactsFragment";
    private View mRootView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        progressBar = (ProgressBar)mRootView.findViewById(R.id.progressBar);

        return mRootView;
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar.setVisibility(View.VISIBLE);
        getLoaderManager().initLoader(0, null, this).forceLoad();
    }


    @Override
    public Loader<Result<List<Group>>> onCreateLoader(int id, Bundle args) {
        return new ContactsLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<Result<List<Group>>> loader, Result<List<Group>> data) {
        progressBar.setVisibility(View.GONE);
        switch (data.getStatus()){
            case OK:
                Log.i(TAG, data.getData().toString());
                break;
            case IO_EXCEPTION:
                break;
            case SERVER_ERROR:
                break;
            case NO_DATA:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Result<List<Group>>> loader) {

    }
}
