package com.hrupin.rosterapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Igor Khrupin www.hrupin.com on 5/4/16.
 */
public class Group {
    @SerializedName("groupName")
    private String name;

    @SerializedName("people")
    private ArrayList<Person> people = new ArrayList<>();

    public String getNameCapitalized() {
        return name.substring(0,1).toUpperCase() + name.substring(1);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPerson(Person p) {
        people.add(p);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (name != null ? !name.equals(group.name) : group.name != null)
            return false;
        return people != null ? people.equals(group.people) : group.people == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (people != null ? people.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupName='" + name + '\'' +
                ", people=" + people +
                '}';
    }
}
