package com.example.weatherapp;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.weatherapp.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    String tag = "LifeCycleEvents";
    final String DEGREE  = "\u00b0";

    private ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText latitude = (EditText) findViewById(R.id.etLatitude);
        final EditText longitude = (EditText) findViewById(R.id.etLongitude);
        Button button = (Button) findViewById(R.id.btnForecast);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // accept longitude and latitudes from User
                String lat = String.valueOf(latitude.getText());
                String lon = String.valueOf(longitude.getText());
                // Open weather API url
                String URL1 = "http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&APPID=4f6f268a496ed32149b37570e57bef91";
                new GetWeatherData().execute(URL1);

            }
        });


    }


    private class GetWeatherData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection =  (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1){

                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                Log.d("result: ","------------------------>"+result);
                return result;

            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }

            return null;
    }

        protected void onPostExecute(String result){
            try{

                JSONObject jsonObject =new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            String iconUrl=null;
            String desc=null;
            String description;
            for (int i = 0; i < jsonArray.length(); i++)
            {
                iconUrl = jsonArray.getJSONObject(i).getString("icon");
                 desc = jsonArray.getJSONObject(i).getString("description");
                Log.e("icon_url:------->",iconUrl);
            }
                char[] chars = desc.toCharArray();
                chars[0] = Character.toUpperCase(chars[0]);
                description= new String(chars);
                String imgUrl="http://openweathermap.org/img/w/"+iconUrl+".png";
                float tempMin=(float)(Float.valueOf(jsonObject.getJSONObject("main").getString("temp_min"))-272.15);
                float tempMax=(float)(Float.valueOf(jsonObject.getJSONObject("main").getString("temp_max"))-272.15);
                float temp=(float)(Float.valueOf(jsonObject.getJSONObject("main").getString("temp"))-272.15);

                new DownloadImageTask().execute(imgUrl);
                activityMainBinding= DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);
                WeatherInfo obj=new WeatherInfo(description+" ("+temp+DEGREE+"C)", "Min: "+String.valueOf(tempMin)+DEGREE+"C","Max: "+String.valueOf(tempMax)+DEGREE+"C");
                activityMainBinding.setWeatherInfo(obj);

            }catch (Exception x){
                x.printStackTrace();
            }
    }
    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... urls) {
            try {
                return loadImageFromNetwork(urls[0]);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {

                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap result) {

            ImageView mImageView = (ImageView) findViewById(R.id.imvIcon);
            mImageView.setImageBitmap(result);
        }

        private Bitmap loadImageFromNetwork(String url)
                throws MalformedURLException, IOException {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(
                    url).getContent());
            return bitmap;
        }
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
