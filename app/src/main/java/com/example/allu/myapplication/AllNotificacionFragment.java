package com.example.allu.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

/**
 * Created by Allu on 19/03/2016.
 */
public class AllNotificacionFragment extends Fragment {
    ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.all_notification_fragment, container, false);

        ListView myListView = (ListView) ll.findViewById(R.id.allList);
        ArrayList<String> myStringArray = new ArrayList<String>();
        myStringArray.add("20-03 Lluvia en Lleida ahora mismo");
        myStringArray.add("20-03 Alta probabilidad de lluvia en Lleida");
        myStringArray.add("19-03 0 grados en Lleida ahora mismo");
        myStringArray.add("17-03 Bajas temperaturas en Lleida ahora mismo");
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_row, myStringArray);
        myListView.setAdapter(adapter);

        return ll;
    }


}
