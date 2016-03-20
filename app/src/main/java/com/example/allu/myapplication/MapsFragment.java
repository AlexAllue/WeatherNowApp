package com.example.allu.myapplication;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    static final LatLng LLEIDA = new LatLng(41.619102, 0.619217);
    static final LatLng USER1 = new LatLng(41.607851, 0.623189);
    static final LatLng USER2 = new LatLng(41.627851, 0.623189);
    static final LatLng USER3 = new LatLng(41.610001, 0.593999);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.activity_maps_fragment, container, false);

        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())!= ConnectionResult.SUCCESS){
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), getActivity(),1 );
            dialog.show();
        }

        setUpMapIfNeeded();


        return ll;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        Marker Yo = mMap.addMarker(new MarkerOptions().position(LLEIDA).title("Lleida"));
        Marker user1 = mMap.addMarker(new MarkerOptions().position(USER1).title("Prueba1").snippet("Temperatura: 24ºC Humedad: 15% Presión: 1018.3hPa Altitud: 160m").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green)));
        Marker user2 = mMap.addMarker(new MarkerOptions().position(USER2).title("Prueba2").snippet("Temperatura: 22ºC\n" +
                "Humedad: 10% Presión: 1018.3 hPa Altitud: 160m").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green)));
        Marker user3 = mMap.addMarker(new MarkerOptions().position(USER3).title("Prueba3").snippet("Temperatura: 22,5ºC\n" +
                "Humedad: 13% Presión: 1018.3 hPa Altitud: 160m").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LLEIDA, 10));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
    }
}
