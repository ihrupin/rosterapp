package com.hrupin.rosterapp.model;

import com.google.gson.annotations.SerializedName;
import com.hrupin.rosterapp.R;

/**
 * Created by Igor Khrupin www.hrupin.com on 5/4/16.
 */
public enum Status {
    @SerializedName("busy")
    BUSY(R.drawable.ic_list_status_busy),

    @SerializedName("online")
    ONLINE(R.drawable.ic_list_status_online),

    @SerializedName("offline")
    OFFLINE(R.drawable.ic_list_status_offline),

    @SerializedName("away")
    AWAY(R.drawable.ic_list_status_away),

    @SerializedName("callforwarding")
    CALLFORWARDING(R.drawable.ic_list_status_pending);

    private final int mIconRes;

    Status(int iconRes) {
        mIconRes = iconRes;
    }

    public int getIconRes() {
        return mIconRes;
    }
}
