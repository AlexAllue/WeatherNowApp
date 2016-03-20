package com.example.allu.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Allu on 15/03/2016.
 */
public class MainRealDeviceFragment extends Fragment implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView temperature,humidity,pressure,altitude,hora;
    private Button subirDatos;
    private String sPref;
    private String temperaturetype="Fahrenheit ºF";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.main_realdevice_fragment, container, false);
        temperature = (TextView) ll.findViewById(R.id.temperatureInfo);
        humidity = (TextView) ll.findViewById(R.id.humidityInfo);
        pressure = (TextView) ll.findViewById(R.id.pressureInfo);
        altitude = (TextView) ll.findViewById(R.id.altitudeInfo);
        hora = (TextView) ll.findViewById(R.id.hora);
        subirDatos = (Button) ll.findViewById(R.id.subirDatos);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)==null){
            temperature.setText("Sorry, dont have temperature sensor");
        }if(sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)==null){
            humidity.setText("Sorry, dont have humidity sensor");
        }if(sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)==null){
            pressure.setText("Sorry, dont have humidity sensor");
            altitude.setText("Sorry, you need humidity sensor");
        }

        subirDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                hora.setText("Última subida: "+calendar.getTime());
                showToast("Subida de datos no implementada");
            }
        });
        return ll;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        getSensorEvent(event);
    }

    private void getSensorEvent(SensorEvent event) {
        float value = event.values[0];
        if(event.sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE){
            if(sPref.equals(temperaturetype)) temperature.setText((value*1800+32)+"ºF");
            else temperature.setText(value+"ºC");
        }
        else if(event.sensor.getType()==Sensor.TYPE_RELATIVE_HUMIDITY){
            humidity.setText(value+"%");
        }
        else if(event.sensor.getType()==Sensor.TYPE_PRESSURE){
            pressure.setText(value+"hPa");
            altitude.setText((SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, value))+"m");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(sPref.equals(temperaturetype)&&sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)!=null) temperature.setText("0.0ºF");
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Gets the user's network preference settings
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // Retrieves a string value for the preferences. The second parameter
        // is the default value to use if a preference value is not found.
        sPref = sharedPrefs.getString("Temperature", "Celsius ºC");
    }

    @Override
    public void onPause() {
        // unregister listener
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    protected void showToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }


}
