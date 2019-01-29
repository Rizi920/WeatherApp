package com.example.weatherapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MainActivity : AppCompatActivity() {

    //Variables used in the class
    internal val DEGREE = "\u00b0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //onclick method of the btn "forecast" in the main activity
    fun btnOnClick(view: View) {
        val latitude = findViewById<View>(R.id.etLatitude) as EditText
        val longitude = findViewById<View>(R.id.etLongitude) as EditText

        //check if phone is connected to the network
        if (!isNetworkAvailable(applicationContext) == true) {
            Toast.makeText(applicationContext, "No Internet, Please connect to a Network", Toast.LENGTH_LONG).show()
        } else {

            //get user input values from the UI fields "longitude" & "latitude"
            val lat = latitude.text.toString()
            val lon = longitude.text.toString()

            //check either fields are empty and there is no values entered as "longitude" & "latitude"
            if (lat.equals("", ignoreCase = true) || lon.equals("", ignoreCase = true)) {
                Toast.makeText(applicationContext, "Please Enter latitude and longitude to get the forecast!", Toast.LENGTH_LONG).show()
            } else {

                //calling of method used for fetching data from API
                getData(lat, lon)
            }
        }
        //Hide keyboard from the screen after button click
        try {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
        }

    }

    internal fun getData(lat: String, lon: String) {

        //initialize retrofit builder to the API
        val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create<WeatherService>(WeatherService::class.java!!)

        //invoking the interface method, passing required attributes
        val call = service.getCurrentWeatherData(lat, lon, AppId)
        call.enqueue(object : Callback<WeatherData> {

            //method used to get response from the API
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                if (response.code() == 200) {

                    //mapping json response to our POJO object for deserialization
                    val weatherData = response.body()

                    //getting UI elements to set the values in it
                    val desc = findViewById<View>(R.id.tvDescription) as TextView
                    val tempmin = findViewById<View>(R.id.tvMinTemp) as TextView
                    val tempmax = findViewById<View>(R.id.tvMaxTemp) as TextView

                    //converting fahrenheit to celsius temperature
                    val temp = (weatherData!!.main!!.temp) - 272.15
                    var description = weatherData?.weather?.get(0)?.description

                    //capitalizing the first character of description
                    val chars = description?.toCharArray()
                    chars?.set(0, Character.toUpperCase(chars[0]))
                    description = chars?.let { String(it) }

                    //setting values to the UI
                    desc.text = description + " (" + temp + DEGREE + "C)"
                    tempmin.text = (weatherData?.main!!.temp_min - 272.15).toString() + DEGREE + "C"
                    tempmax.text = (weatherData?.main!!.temp_max - 272.15).toString() + DEGREE + "C"

                    //creating image url for the ICON image
                    val imgUrl = "http://openweathermap.org/img/w/" + weatherData.weather?.get(0)!!.icon + ".png"

                    //calling async task to download and set the icon image on UI
                    DownloadImageTask().execute(imgUrl)

                }
            }

            //method to deal with if we fail to get the response from API
            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(applicationContext, "Something went wrong, Please try again!", Toast.LENGTH_LONG).show()

            }
        })
    }


    //Task to download and set the icon image on UI
    private inner class DownloadImageTask : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            try {
                //Download the image bitmap
                return loadImageFromNetwork(urls[0])
            } catch (e: MalformedURLException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: IOException) {

                // TODO Auto-generated catch block
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: Bitmap) {

            //setting image bitmap On UI

            val mImageView = findViewById<View>(R.id.imvIcon) as ImageView
            mImageView.setImageBitmap(result)
        }

        @Throws(MalformedURLException::class, IOException::class)
        private fun loadImageFromNetwork(url: String): Bitmap {
            val bitmap = BitmapFactory.decodeStream(URL(
                    url).content as InputStream)
            return bitmap
        }
    }

    //method to check if phone is connected to the network
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }

    //first method to be executed in the start of app
    public override fun onStart() {
        super.onStart()
        //check if phone is connected to the network
        if (!isNetworkAvailable(applicationContext) == true) {
            Toast.makeText(applicationContext, "No Internet, Please connect to a Network", Toast.LENGTH_LONG).show()
        }

    }

    companion object {
        var BaseUrl = "http://api.openweathermap.org/"
        var AppId = "4f6f268a496ed32149b37570e57bef91"
    }

}
