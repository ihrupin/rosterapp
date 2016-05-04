package com.hrupin.rosterapp.api;

import com.google.gson.annotations.SerializedName;
import com.hrupin.rosterapp.model.Group;

import java.util.ArrayList;

/**
 * Created by Igor Khrupin www.hrupin.com on 5/4/16.
 */
public class ContactGroupResponse {

    @SerializedName("groups")
    private ArrayList<Group> groups;

    public ArrayList<Group> getGroups() {
        return groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactGroupResponse that = (ContactGroupResponse) o;

        return groups != null ? groups.equals(that.groups) : that.groups == null;

    }

    @Override
    public int hashCode() {
        return groups != null ? groups.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ContactGroupResponse{" +
                "groups=" + groups +
                '}';
    }
}
