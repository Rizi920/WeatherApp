package com.example.weatherapp

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

import com.example.weatherapp.databinding.ActivityMainBinding

import org.json.JSONArray
import org.json.JSONObject

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.MalformedURLException
import java.net.URL
import java.net.URLConnection
import java.net.UnknownHostException

class MainActivity : AppCompatActivity() {
    internal var tag = "LifeCycleEvents"
    internal val DEGREE = "\u00b0"

    private var activityMainBinding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun btnOnClick(view: View) {
        val latitude = findViewById<View>(R.id.etLatitude) as EditText
        val longitude = findViewById<View>(R.id.etLongitude) as EditText
        if (!isNetworkAvailable(applicationContext) == true) {
            Toast.makeText(applicationContext, "No Internet, Please connect to a Network", Toast.LENGTH_LONG).show()
        }
        val lat = latitude.text.toString()
        val lon = longitude.text.toString()
        if (lat.equals("", ignoreCase = true) || lon.equals("", ignoreCase = true)) {
            Toast.makeText(applicationContext, "Please Enter latitude and longitude to get the forecast!", Toast.LENGTH_LONG).show()
        } else {
            val URL1 = "http://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&APPID=4f6f268a496ed32149b37570e57bef91"
            GetWeatherData().execute(URL1)
        }
    }


    private inner class GetWeatherData : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg urls: String): String? {
            var result = ""
            val url: URL
            var urlConnection: HttpURLConnection? = null

            try {
                url = URL(urls[0])
                urlConnection = url.openConnection() as HttpURLConnection
                val `in` = urlConnection.inputStream
                val reader = InputStreamReader(`in`)

                var data = reader.read()
                while (data != -1) {
                    val current = data.toChar()
                    result += current
                    data = reader.read()
                }
                return result

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: String?) {


            if (result == null || result.equals("", ignoreCase = true)) {
                Toast.makeText(applicationContext, "Sorry! No such location found", Toast.LENGTH_LONG).show()
            } else {
                try {
                    val jsonObject = JSONObject(result)
                    val jsonArray = jsonObject.getJSONArray("weather")
                    var iconUrl: String? = null
                    var desc: String? = null
                    val description: String
                    for (i in 0 until jsonArray.length()) {
                        iconUrl = jsonArray.getJSONObject(i).getString("icon")
                        desc = jsonArray.getJSONObject(i).getString("description")

                    }
                    val chars = desc!!.toCharArray()
                    chars[0] = Character.toUpperCase(chars[0])
                    description = String(chars)
                    val imgUrl = "http://openweathermap.org/img/w/$iconUrl.png"
                    val tempMin = (java.lang.Float.valueOf(jsonObject.getJSONObject("main").getString("temp_min")) - 272.15).toFloat()
                    val tempMax = (java.lang.Float.valueOf(jsonObject.getJSONObject("main").getString("temp_max")) - 272.15).toFloat()
                    val temp = (java.lang.Float.valueOf(jsonObject.getJSONObject("main").getString("temp")) - 272.15).toFloat()

                    DownloadImageTask().execute(imgUrl)
                    activityMainBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
                    val obj = WeatherInfo(description + " (" + temp + DEGREE + "C)", "Min: " + tempMin.toString() + DEGREE + "C", "Max: " + tempMax.toString() + DEGREE + "C")
                    activityMainBinding!!.weatherInfo = obj

                } catch (x: Exception) {
                    x.printStackTrace()
                }

            }
        }

    }


    private inner class DownloadImageTask : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            try {
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

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }

    public override fun onStart() {
        super.onStart()
        if (!isNetworkAvailable(applicationContext) == true) {
            Toast.makeText(applicationContext, "No Internet, Please connect to a Network", Toast.LENGTH_LONG).show()
        }
        Log.d(tag, "In the onStart() event")
    }

    public override fun onRestart() {
        super.onRestart()
        Log.d(tag, "In the onRestart() event")
    }

    public override fun onResume() {
        super.onResume()
        Log.d(tag, "In the onResume() event")
    }

    public override fun onPause() {
        super.onPause()
        Log.d(tag, "In the onPause() event")
    }

    public override fun onStop() {
        super.onStop()
        Log.d(tag, "In the onStop() event")
    }

    public override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "In the onDestroy() event")
    }
}
