package com.example.narasimha.screenchanger;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    /*
     Every application which uses Sensors has to create 2 objects SensorManager and Sensor,
     SensorManager : It is a main (super) class for all Sensors, it contains functions and variables
     which can be used by Application to register, manage and unregister sensor hardware whenever necessary

     Sensor : Sensor class is an abstract class of Sensor hardware, Sensor class remains same irrespective
     of type of Sensor we are using.

     NOTE : Sensor object has to be wrapped up with SensorManager to make it available to application.
     */
    SensorManager sensorManager;
    Sensor acceleration;
    TextView net, acclr;
    //We create an int[] array containing 2 images, 0 -> Tiger and 1-> Monkey
    int[] images = {
            R.drawable.tiger,
            R.drawable.monkey
    };
    //Initialize net_acclrn to 0, (initial)
    float net_acclrn = 0.00f;

    //index is used to iterate over images[] array
    int index = 0;
    //View object for ImageView
    ImageView imgView;

    //Current accleration will be initialized with 9.81 m/s2 , we call it C and LAST_ACCLRN is also 9.81
    //initially, We use LAST_ACCLRN to contain last known accleration. (Previous value) We call it as L
    // L can be used to calculate Delta, where DELTA = C - L
    float current_accleration = SensorManager.GRAVITY_EARTH;
    float LAST_ACCLRN = SensorManager.GRAVITY_EARTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init all views
        imgView = findViewById(R.id.image);
        net = findViewById(R.id.net_acclrn);
        acclr = findViewById(R.id.axis_values);

        //Initially, set ImageView to tiger's image. getResources().getDrawable() function can be used to
        // point to drawable directoy inside res folder. we make use of images[] array to obtain ids of Images
        // in this case index = 0, so tiger image will be rendered.
        imgView.setImageDrawable(getResources().getDrawable(images[index]));
        /*
          getSystemService() is base android service, which can be used to establish a connection
          (IPC) to any System service. SENSOR_SERVICE is a Linux Service, which manages sensors.
         */
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        //We select type of sensor required for out task, we are making use of accleremeter,
        //getDefaultSensor() function returns a Sensor object of TYPE_ACCELEROMETER
        acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        /*
        We register a SensorEventListener, it has 2 methods:
          onSensorChange() : This method is invoked whenever there is a change in Sensor value.
          onAccurayChanged() : invoked only when accuray is changed, we are not using this function.
          SENSOR_DELAY_FASTEST : Make complete use of Sensor hardware, by setting minimum access delay
         */
        sensorManager.registerListener(this, acceleration, SensorManager.SENSOR_DELAY_FASTEST);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Sensor values will be listed here:
        // sensorEvent.values returns a array of length 3, values[0] -> acclrn along X axis
        // values[1] -> acclrn along Y axis
        // values[2] -> acclrn along Z axis
        float[] values = sensorEvent.values;
        acclr.setText("X : "+values[0]+" Y: "+values[1]+" Z: "+values[2]);

        //User defined function to compute net acceleration from 3D vector
        float magnitude = computeNetAcceleration(values);
        net.setText("Net: "+magnitude);
        if(magnitude > 20){
            // if N > 20, we can say, there is a drastic motion, now change the image
            //Drastic motion:
            //The below expression makes sure, index will rotate between 0 and 1
            //So we get alternate images like Tiger -> Monkey -> Tiger -> Monkey etc
            index = (index+1)%(images.length);
            //Set new image
            imgView.setImageDrawable(getResources().getDrawable(images[index]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public float computeNetAcceleration(float[] values){
        //x - axis : values[0], y-axis : values[1], z-axis : values[2]
        //We compute (x^2+y^2+z^2) this is the magnitude of a 3D vector
        float magnitude = (values[0]*values[0])+(values[1]*values[1])+(values[2]*values[2]);

        //Copy C to L , so L is last acceleration value
        LAST_ACCLRN = current_accleration;

        //Compute C = sqrt(magnitude)
        current_accleration = (float)Math.sqrt(magnitude);

        //Calculate delta : D = C - L
        float delta = current_accleration - LAST_ACCLRN;

        //Net accleration is always 90% of previous accelearation added with delta
        //Recurrent eqn for N = N*0.9 + D
        net_acclrn = net_acclrn*0.9f + delta;
        //return N
        return  net_acclrn;
    }
}
