package com.example.prane.rhymeit;

/**
 * Created by pratap on 4/21/2016.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;

public class weather extends AppCompatActivity {

    double latitude;
    double longitude;
    Context mContext;
    public static String a="0";
    TextView textview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        this.mContext = getBaseContext();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setIcon((int) R.drawable.a01d);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        GetGps getGps = new GetGps(getApplicationContext());
        this.latitude = getGps.GetLatitude();
        this.longitude = getGps.GetLongitude();
        ButtonClick();

    }
    public void ButtonClick() {
        textview2 = (TextView) findViewById(R.id.textViewmonth);
        String Area = "naga";
        new CallAPI(new WeatherServiceListener() {
            @Override
            public void servicesuccess(String str) {
                try {
                    JSONObject data = new JSONObject(str.toString());
                    Results(data);
                    data.getJSONArray("list");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).execute(new String[]{"http://api.openweathermap.org/data/2.5/forecast?lat=" + this.latitude + "&lon=" + this.longitude + "&appid=d0c4f73baa4bbd692bf152282ea0d999"});
    }

//    @Override
//    public void servicesuccess(String str) {
//
//
//    }

    public String Results(JSONObject data) {
        //String a="0";
       // List<ForecastDay> itemlist = new ArrayList();
       // ListView listView = (ListView) findViewById(R.id.listview);
        try {
           // String a="0";
            JSONArray jArr = data.getJSONArray("list");
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject jDayForecast = jArr.getJSONObject(i);
               // ForecastDay df = new ForecastDay();
               // String s = jDayForecast.getString("dt_txt");
               // df.setClouds(jDayForecast.getJSONObject("clouds").getInt("all"));
                //df.setWindspeed(Double.valueOf(jDayForecast.getJSONObject("wind").getDouble("speed")));
                JSONObject jTempObj = jDayForecast.getJSONObject("main");
                a= "1";//jTempObj.getString("temp");
               // df.setPressure(Double.valueOf(jTempObj.getDouble("pressure")));
               // df.setHumidity(Double.valueOf(jTempObj.getDouble("humidity")));
                //JSONObject jWeatherObj = jDayForecast.getJSONArray("weather").getJSONObject(0);
               // df.setDescription(jWeatherObj.getString("description"));
                //df.setMain(jWeatherObj.getString("main"));
                //df.setIcon(jWeatherObj.getString("icon"));
                //df.setDateTime(s);
                //itemlist.add(df);
                //textview2.setText(a);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
       // listView.setAdapter(new CustomListAdapter(this, itemlist));
   return a; }
}

class CallAPI extends AsyncTask<String, String, String>
{
    private WeatherServiceListener listener;
    public CallAPI(WeatherServiceListener listener)
    {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
        InputStream inputStream;
        StringBuilder result = null;

        try
        {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            //return e.getMessage();
        }
        return  result.toString();

    }

    protected void onPostExecute(String result)
    {
        listener.servicesuccess(result);
    }
}


