package com.example.allu.myapplication;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Allu on 14/03/2016.
 */
public class MainActivity extends ActionBarActivity {
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private TextView txtUser, txtEmail;
    private DrawerLayout mDrawerLayout;
    private Fragment fragment;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private String sPref;
    private String sensor="Sensor device";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //fragment = new MainFragment();
                switch(i) {
                    default:
                    case 0:
                        if(sPref.equals(sensor)) {
                            fragment = new MainRealDeviceFragment();
                        }else{
                            fragment = new MainSensorSimulatorFagment();
                        }
                        getSupportActionBar().setTitle(getApplication().getString(R.string.app_name));
                        break;
                    case 1:
                        fragment = new MapsFragment();
                        getSupportActionBar().setTitle(getApplication().getString(R.string.bar_map));
                        break;
                    case 2:
                        fragment = new SearchFragment();
                        getSupportActionBar().setTitle(getApplication().getString(R.string.bar_search));
                        break;
                    case 3:
                        fragment = new NotificationFragment();
                        getSupportActionBar().setTitle(getApplication().getString(R.string.bar_notification));
                        break;
                    case 4:
                        fragment = new ContactFragment();
                        getSupportActionBar().setTitle(getApplication().getString(R.string.bar_contact));
                        break;
                    case 5:
                        fragment = new MainRealDeviceFragment();
                        Intent settingsActivity = new Intent(getApplication(), SettingsActivity.class);
                        startActivity(settingsActivity);
                        break;
                    case 6:
                        fragment = new MainRealDeviceFragment();
                        Intent intent = new Intent(getApplication(),GoodByeActivity.class);
                        intent.putExtra("user", txtUser.getText().toString());
                        intent.putExtra("email", txtEmail.getText().toString());
                        startActivity(intent);
                        finish();
                        break;
                    case 7:
                        fragment = new MainRealDeviceFragment();
                        finish();
                        break;
                }
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                    mDrawerLayout.closeDrawers();

            }
        });



        txtUser = (TextView) findViewById(R.id.user);
        txtEmail = (TextView) findViewById(R.id.email);

        Intent intent = getIntent();
        txtUser.setText(intent.getStringExtra("user"));
        txtEmail.setText(intent.getStringExtra("email"));

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        sPref = sharedPrefs.getString("Sensor", "Sensor simulator");
        if(sPref.equals(sensor)) {
            fragment = new MainRealDeviceFragment();
        }else{
            fragment = new MainSensorSimulatorFagment();
        }

        fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();

    }

    public void onSaveInstanceState(Bundle outState){
        getSupportFragmentManager().putFragment(outState,"myfragment",fragment);
        outState.putString("user", txtUser.getText().toString());
        outState.putString("email", txtEmail.getText().toString());
    }
    public void onRestoreInstanceState(Bundle savedInstanceState){
        txtUser.setText(savedInstanceState.getString("user"));
        txtEmail.setText(savedInstanceState.getString("email"));
        fragment = getSupportFragmentManager().getFragment(savedInstanceState,"myfragment");
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void addDrawerItems() {
        Integer[] imgid={
                R.drawable.icono_subir,
                R.drawable.icono_mapa,
                R.drawable.icono_buscar,
                R.drawable.icono_notificaciones,
                R.drawable.icono_contacto,
                R.drawable.icono_preferencias,
                R.drawable.icono_cerrar_sesion,
                R.drawable.icono_salir,
        };
        String[] drawerMenuArray = { "Subir datos","Ver mapa","Buscar", "Notificaciones", "Contacto","Preferencias", "Cerrar Sesión", "Salir" };
        CustomListAdapter adapter=new CustomListAdapter(this, drawerMenuArray, imgid);
        //mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerMenuArray);
        mDrawerList.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        sPref = sharedPrefs.getString("Sensor", "Sensor simulator");
    }


}
