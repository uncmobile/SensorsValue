package edu.unc.nirjon.classsensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity
    implements SensorEventListener {

    private SensorManager sm;
    private Sensor s1, s2;
    private List<Sensor> ls;

    long lastPrinted = 0, lastPrinted2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //get a list of available sensors on the phone
        ls = sm.getSensorList(Sensor.TYPE_ALL);

        for(int i = 0; i < ls.size(); i++) {
            Log.v("-------->>", i + ": " + ls.get(i).getName() + "");
        }

        //get gravity and light sensor
        s1 = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        s2 = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

        //register sensors
        sm.registerListener(this, s1, 1000000);
        sm.registerListener(this, s2, 500000);
    }

    /**
     * Callback method which will be called by the system when the sensor
     * data changed.
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.timestamp - lastPrinted >= 1e9
                && event.sensor.getType() == Sensor.TYPE_GRAVITY) {

            String tmp = "" + Math.sqrt(
                    event.values[0] * event.values[0] +
                    event.values[1] * event.values[1] +
                    event.values[2] * event.values[2]);

                    Log.v("GRAVITY", tmp);
            lastPrinted = event.timestamp;
        }

        if(event.timestamp - lastPrinted2 >= 0.5e9
                && event.sensor.getType()== Sensor.TYPE_LIGHT) {
            Log.v("LiGHT", "" + event.values[0]);
            lastPrinted2 = event.timestamp;
        }

    }

    /**
     * Callback method which will be called by the system when the sensor
     * accuracy changed.
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
