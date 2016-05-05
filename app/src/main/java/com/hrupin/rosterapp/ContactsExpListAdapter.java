package com.hrupin.rosterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.hrupin.rosterapp.model.Group;
import com.hrupin.rosterapp.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor Khrupin www.hrupin.com on 5/5/16.
 */
public class ContactsExpListAdapter extends BaseExpandableListAdapter implements Filterable {

    private List<Group> mGroups;
    private Context mContext;
    private Filter filter;
    private List<Group> mFilteredGroups;

    public ContactsExpListAdapter(Context context, List<Group> groups) {
        mContext = context;
        mGroups = groups;
        mFilteredGroups = new ArrayList<>();
    }

    @Override
    public Object getChild(int groupPosition, int personPosititon) {
        List<Person> people = getGroups().get(groupPosition).getPeople();
        if (people.isEmpty()) {
            return null;
        }
        return people.get(personPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int personPosititon) {
        return personPosititon;
    }

    @Override
    public View getChildView(int groupPosition, final int personPosititon,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Object child = getChild(groupPosition, personPosititon);
        if (child == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_list_empty_person, null);
        } else {
            final Person person = (Person) child;
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_list_person, null);

            TextView tv = (TextView) convertView.findViewById(R.id.tv_list_person_name);

            tv.setText(person.getFirstName() + " " + person.getLastName());
            tv.setCompoundDrawablesRelativeWithIntrinsicBounds(person.getStatus().getIconRes(), 0, 0, 0);

            tv = (TextView) convertView.findViewById(R.id.tv_list_person_status_message);
            tv.setText(person.getStatusMessage());
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int realCount = getGroups().get(groupPosition).getPeople().size();
        if (realCount == 0) {
            return 1;
        }
        return realCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return getGroups().get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return getGroups().size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Group group = (Group) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_list_group_name, null);
        }

        TextView tvGroupName = (TextView) convertView
                .findViewById(R.id.tv_list_group_name);
        tvGroupName.setText(group.getNameCapitalized());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void updateData(List<Group> groups) {
        mGroups = groups;
        mFilteredGroups = new ArrayList<>();
        notifyDataSetChanged();
    }

    public List<Group> getGroups() {
        if (!mFilteredGroups.isEmpty()) {
            return mFilteredGroups;
        }
        return mGroups;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults results = new FilterResults();
                    if (charSequence == null || charSequence.length() == 0) {
                        results.values = mGroups;
                        results.count = mGroups.size();
                    } else {
                        String str = charSequence.toString().toLowerCase();
                        ArrayList<Group> filterResultsData = new ArrayList<>();

                        for (Group data : mGroups) {
                            Group g = new Group();
                            g.setName(data.getName());
                            for (Person p : data.getPeople()) {
                                if (p.getFirstName().toLowerCase().contains(str) || p.getLastName().toLowerCase().contains(str)) {
                                    g.addPerson(p);
                                }
                            }
                            filterResultsData.add(g);
                        }

                        results.values = filterResultsData;
                        results.count = filterResultsData.size();
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    mFilteredGroups = (ArrayList<Group>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }
        return filter;
    }
}
