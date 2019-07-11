package com.yanis.front_end_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingEventActivity extends AppCompatActivity {

    public EditText editTextName;
    public EditText editTextDate;
    public PreferenceUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_event);

        editTextName= (EditText)findViewById(R.id.editTextNameEventDetail);
        editTextDate= (EditText)findViewById(R.id.editTextDateEventDetail);

        getOneEvent();


    }




    public void onEditEventPressed(View view) {
        Intent i = new Intent(this, DetailsEventActivity.class);
        EditEvent();
        startActivity(i);
    }






    private void EditEvent() {

        final String URL = "http://192.168.43.157:3000/events/5";
        final String Token = utils.getToken(this);

        JSONObject Event = new JSONObject();
        try {
            Event.put("name",editTextName.getText().toString().trim());
            Event.put("date",editTextDate.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PATCH,
                URL,
                Event,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(SettingEventActivity.this, "Event edited successfuly", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(SettingEventActivity.this, "Event not edited successfuly", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "bearer");
                headers.put("Content-Type", "application/json");
                headers.put("JWT",Token);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }









    private void getOneEvent() {

        final String URL = "http://192.168.43.157:3000/events/5";
        final String Token = utils.getToken(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String name = null;
                        String Date = null;
                        try {
                            name = response.getString("name");
                            Date = response.getString("date");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        editTextName.setText(name);
                        editTextDate.setText(Date.substring(0,10));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.i("info", "onResponse: KOOO ");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "bearer");
                headers.put("Content-Type", "application/json");
                headers.put("JWT",Token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


}
