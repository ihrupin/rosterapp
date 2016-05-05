package com.hrupin.rosterapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.hrupin.rosterapp.loader.ContactsLoader;
import com.hrupin.rosterapp.loader.Result;
import com.hrupin.rosterapp.model.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor Khrupin www.hrupin.com on 5/5/16.
 */
public class ContactsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Result<List<Group>>>, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ContactsFragment";
    private View mRootView;
    private ExpandableListView mElvContactsList;
    private SwipeRefreshLayout mSwipeContainer;
    private ContactsExpListAdapter expListAdapter;
    private TextView mTvErrorMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        mElvContactsList = (ExpandableListView)mRootView.findViewById(R.id.elv_contacts);
        mElvContactsList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Doing nothing. Disabling collapse groups
                return true;
            }
        });
        mSwipeContainer = (SwipeRefreshLayout)mRootView.findViewById(R.id.swipe_container);
        mTvErrorMessage = (TextView)mRootView.findViewById(R.id.tv_error_message);
        mSwipeContainer.setOnRefreshListener(this);
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        return mRootView;
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeContainer.post(new Runnable() {
            @Override public void run() {
                mSwipeContainer.setRefreshing(true);
            }
        });
        getLoaderManager().initLoader(0, null, this).forceLoad();
    }


    @Override
    public Loader<Result<List<Group>>> onCreateLoader(int id, Bundle args) {
        return new ContactsLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<Result<List<Group>>> loader, Result<List<Group>> data) {
        mSwipeContainer.setRefreshing(false);
        switch (data.getStatus()){
            case OK:
                Log.i(TAG, data.getData().toString());
                updateListItens(data.getData());
                updateErrorMessage(null);
                break;
            case IO_EXCEPTION:
                updateListItens(null);
                updateErrorMessage(getString(R.string.error_message_list_io_exception));
                break;
            case SERVER_ERROR:
                updateListItens(null);
                updateErrorMessage(getString(R.string.error_message_list_server_error));
                break;
            case NO_DATA:
                updateListItens(null);
                updateErrorMessage(getString(R.string.error_message_list_no_data));
                break;
        }
    }

    private void updateListItens(List<Group> data) {
        if(expListAdapter == null) {
            expListAdapter = new ContactsExpListAdapter(getContext(), data);
            mElvContactsList.setAdapter(expListAdapter);
            expandAll();
        }else{
            if(data == null){
                expListAdapter.updateData(new ArrayList<Group>());
            }else {
                expListAdapter.updateData(data);
                expandAll();
            }
        }
    }

    private void expandAll() {
        if(mElvContactsList != null & expListAdapter != null) {
            for (int i = 0; i < expListAdapter.getGroupCount(); i++) {
                mElvContactsList.expandGroup(i);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Result<List<Group>>> loader) {

    }

    @Override
    public void onRefresh() {
        updateListItens(null);
        updateErrorMessage(null);
        getLoaderManager().initLoader(0, null, this).forceLoad();
    }

    private void updateErrorMessage(String message) {
        if(message == null){
            mTvErrorMessage.setVisibility(View.GONE);
        }else{
            mTvErrorMessage.setText(message);
            mTvErrorMessage.setVisibility(View.VISIBLE);
        }
    }
}
