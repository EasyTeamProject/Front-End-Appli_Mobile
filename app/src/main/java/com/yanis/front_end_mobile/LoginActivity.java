package com.yanis.front_end_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
       /* StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {

            String apiUrl = "https://api.myjson.com/bins/194g99"; // concatenate uri with base url eg: localhost:8080/ + uri
            URL requestUrl = new URL(apiUrl);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.connect(); // no connection is mad
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        Log.i("API", "onCreate: "+result.toString());
    */

    /*   RequestQueue requestQueue = Volley.newRequestQueue(this);

        String URL = "https://api.myjson.com/bins/194g99";

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("API", "onResponse: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("API", "onResponse: " + error.toString());
                    }
                }
        );

        requestQueue.add(objectRequest);*/
    }

    public void onSuscribePressed(View view){
        Intent i =new Intent(this,SuscribeActivity.class);
        startActivity(i);
    }

    public void onLoginPressed(View view){
        Intent i =new Intent(this,HomeActivity.class);
        startActivity(i);
    }
}
