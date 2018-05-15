package com.example.krishna.numpad;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView tvAccelerometer;
    //the Sensor Manager
    private EditText etTimer;
    private EditText etFilename;
    private SensorManager sManager;
    private Sensor mAccelerometer;
    private Sensor mGyroscope;

    private Button btAccelerometerStart;
    private Button btAccelerometerStop;
    private Timer timer = new Timer();
    private int counter = 0,timeCounter = 0,minutes;
    private String fileName;
    private double x=0,y=0,z=0;
    private boolean flag = false;
    private Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bBack,bDone;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private static final int REQUEST_READ_PERMISSION = 786;
    Long tsLong = System.currentTimeMillis();



    //Github Code Start from here

    // Constants for the low-pass filters
    private double timeConstant = 0.18;
    private double alpha = 0.9;
    private double dt = 0;

    // Timestamps for the low-pass filters
    private double timestamp = System.nanoTime();
    private double timestampOld = System.nanoTime();

    // Gravity and linear accelerations components for the
    // Wikipedia low-pass filter
    private double[] gravity = new double[]
            { 0, 0, 0 };

    private double[] linearAcceleration = new double[]
            { 0, 0, 0 };

    // Raw accelerometer data
    private double[] input = new double[]
            { 0, 0, 0 };

    private int count = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = sManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        b0 = findViewById(R.id.b0);
        bBack = findViewById(R.id.bBack);
        bDone = findViewById(R.id.bDone);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeKeyTime("1,"+(System.currentTimeMillis() - tsLong));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeKeyTime("2,"+(System.currentTimeMillis() - tsLong));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeKeyTime("3,"+(System.currentTimeMillis() - tsLong));            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeKeyTime("4,"+(System.currentTimeMillis() - tsLong));            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeKeyTime("5,"+(System.currentTimeMillis() - tsLong));            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeKeyTime("6,"+(System.currentTimeMillis() - tsLong));            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeKeyTime("7,"+(System.currentTimeMillis() - tsLong));            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeKeyTime("8,"+(System.currentTimeMillis() - tsLong));            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeKeyTime("9,"+(System.currentTimeMillis() - tsLong));            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeKeyTime("0,"+(System.currentTimeMillis() - tsLong));            }
        });
        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeKeyTime("Back,"+(System.currentTimeMillis() - tsLong));            }
        });
        bDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeKeyTime("Done,"+(System.currentTimeMillis() - tsLong));            }
        });

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        /*register the sensor listener to listen to the gyroscope sensor, use the
        callbacks defined in this class, and gather the sensor information as quick
        as possible*/
//        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        sManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        sManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_FASTEST);


    }
    @Override
    protected void onStop()
    {
        //unregister the sensor listener
        sManager.unregisterListener(this);
        super.onStop();
    }


    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        //Do nothing.
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //if sensor is unreliable, return void
//        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
//        {
//            return;
//        }
        //String sensorName = event.sensor.getName();

//        Log.i("reading", String.valueOf(event.values[0]));
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {


            //GITHUB CODE START
            timestamp = System.nanoTime();

            // Find the sample period (between updates).
            // Convert from nanoseconds to seconds
            dt = 1 / (count / ((timestamp - timestampOld) / 1000000000.0f));

            count++;

            alpha = timeConstant / (timeConstant + dt);

            gravity[0] = alpha * gravity[0] + (1 - alpha) * x;
            gravity[1] = alpha * gravity[1] + (1 - alpha) * y;
            gravity[2] = alpha * gravity[2] + (1 - alpha) * z;

            x = x - gravity[0];
            y = y - gravity[1];
            z = z - gravity[2];
        }
        Long temp = System.currentTimeMillis();
        Long timeReading = temp - tsLong;
        String ts = timeReading.toString();

        String entry = Double.toString(x) + "," + Double.toString(y) + "," + Double.toString(z) + "," +  ts + "\n";
        Log.i("entry",entry);

        try {

            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath());
            Boolean dirsMade = dir.mkdir();
            //System.out.println(dirsMade);
            Log.v("Accel", dirsMade.toString());

            File file = new File(dir, "data_accelerometer.csv");
            File file1 = new File(dir,"data_gyroscope.csv");
            FileOutputStream f = new FileOutputStream(file,true);
            FileOutputStream f1 = new FileOutputStream(file1,true);
//            f = new FileOutputStream(file);


            try {
                if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                {f.write(entry.getBytes());
                    f.flush();
                    f.close();}
                else {
                    f1.write(entry.getBytes());
                    f1.flush();
                    f1.close();
                }
//                Toast.makeText(getBaseContext(), sdCard.getAbsolutePath(), Toast.LENGTH_LONG).show();
                Log.i("entry", entry);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void writeKeyTime(String key){
        key = key+"\n";
        try {

            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath());
            Boolean dirsMade = dir.mkdir();
            //System.out.println(dirsMade);
            Log.v("Accel", dirsMade.toString());

            File file = new File(dir, "dataKey.csv");
            FileOutputStream f = new FileOutputStream(file,true);
//            f = new FileOutputStream(file);


            try {
                f.write(key.getBytes());
                f.flush();
                f.close();
//                Toast.makeText(getBaseContext(), sdCard.getAbsolutePath(), Toast.LENGTH_LONG).show();
                Log.i("key", key);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_PERMISSION);
        } else {
//            openFilePicker();
        }
    }
}
