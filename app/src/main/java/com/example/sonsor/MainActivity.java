package com.example.sonsor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.hardware.Sensor;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager sm = null;
    private TextView textView1 = null;
    private List<Sensor> list;

    private final SensorEventListener sel = new SensorEventListener(){
        public void onAccuracyChanged(Sensor sensor, int accuracy){}
        public void onSensorChanged(SensorEvent event){
            float[] values = event.values;
            textView1.setText("x: "+values[0]+"\ny: "+values[1]+"\nz: "+values[2]);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        textView1 = findViewById(R.id.textView1);

        list = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);

        if(list.size()>0){
            sm.registerListener(sel, list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();
        }

        Button buttonNavigateToCompass = findViewById(R.id.buttonNavigateToCompass);
        buttonNavigateToCompass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                navigateToCompassActivity();
            }
        });

        Button buttonNavigateToLightSensor = findViewById(R.id.buttonNavigateToLightSensor);
        buttonNavigateToLightSensor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                navigateToLightSensorActivity();
            }
        });
    }

    @Override
    protected  void onStop(){
        if(list.size()>0){
            sm.unregisterListener(sel);
        }
        super.onStop();
    }

    private void navigateToCompassActivity(){
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
    }

    private void navigateToLightSensorActivity(){
        Intent intent = new Intent(this, LightSensorActivity.class);
        startActivity(intent);
    }
}