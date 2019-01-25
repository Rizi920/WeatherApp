package com.example.weatherapp;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.weatherapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    String tag = "LifeCycleEvents";

    private ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        WeatherInfo obj=new WeatherInfo("Cloudy","12.4","13.2");
        activityMainBinding.setWeatherInfo(obj);

        final EditText latitude = (EditText) findViewById(R.id.etLatitude);
        final EditText longitude = (EditText) findViewById(R.id.etLongitude);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(tag, "In the onStart() event");
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        Log.d(tag, "In the onRestart() event");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(tag, "In the onResume() event");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(tag, "In the onPause() event");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(tag, "In the onStop() event");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(tag, "In the onDestroy() event");
    }
}
