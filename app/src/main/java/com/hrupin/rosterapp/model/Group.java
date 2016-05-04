package com.hrupin.rosterapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Igor Khrupin www.hrupin.com on 5/4/16.
 */
public class Group {
    @SerializedName("groupName")
    private String groupName;

    @SerializedName("people")
    private ArrayList<Person> people;

    public String getGroupName() {
        return groupName;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (groupName != null ? !groupName.equals(group.groupName) : group.groupName != null)
            return false;
        return people != null ? people.equals(group.people) : group.people == null;

    }

    @Override
    public int hashCode() {
        int result = groupName != null ? groupName.hashCode() : 0;
        result = 31 * result + (people != null ? people.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupName='" + groupName + '\'' +
                ", people=" + people +
                '}';
    }
}
