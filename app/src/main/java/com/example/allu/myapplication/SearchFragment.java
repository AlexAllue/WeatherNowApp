package com.example.allu.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Allu on 15/03/2016.
 */
public class SearchFragment extends Fragment {

    private EditText editTown;
    private Button btnSearch;
    private SQLiteDatabase db;
    private Cursor c;
    private String[] args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.search_fragment, container, false);

        editTown = (EditText)ll.findViewById(R.id.town);
        btnSearch = (Button)ll.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTown.getText().toString().matches("")){
                    showToast(getString(R.string.null_town));
                }else {
                    BaseSQLiteHelper padbh =
                            new BaseSQLiteHelper(getActivity().getApplication(), "DBBase", null, 1);
                    db = padbh.getReadableDatabase();
                    args = new String[]{editTown.getText().toString()};
                    c = db.rawQuery("SELECT town,degrees,humidity,longitude,altitude,optional FROM Weather WHERE town=?", args);
                    if(c.moveToFirst()) {

                    }
                    while (c.moveToNext()) {

                    }
                }
            }
        });

        return ll;
    }

    protected void showToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(db!=null){
            db.close();
        }
    }

}
