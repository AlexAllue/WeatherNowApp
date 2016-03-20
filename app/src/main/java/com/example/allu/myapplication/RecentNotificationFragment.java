package com.example.allu.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Allu on 19/03/2016.
 */
public class RecentNotificationFragment extends Fragment {

    ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.recent_notification_fragment, container, false);

        ListView myListView = (ListView) ll.findViewById(R.id.recentList);
        ArrayList<String> myStringArray = new ArrayList<String>();
        myStringArray.add("Lluvia en Lleida ahora mismo");
        myStringArray.add("Alta probabilidad de lluvia en Lleida");
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_row, myStringArray);
        myListView.setAdapter(adapter);

        return ll;
    }
}
