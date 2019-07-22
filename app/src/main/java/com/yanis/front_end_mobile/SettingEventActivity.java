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
    public EditText editTextPlace;
    public EditText editTextInformation;
    public PreferenceUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_event);

        editTextName= (EditText)findViewById(R.id.editTextNameEventDetail);
        editTextDate= (EditText)findViewById(R.id.editTextDateEventDetail);
        editTextPlace= (EditText)findViewById(R.id.editTextPlaceEventDetail);
        editTextInformation= (EditText)findViewById(R.id.editTextSubjectEventDetail);

        getOneEvent();


    }




    public void onEditEventPressed(View view) {
        EditEvent();
    }






    private void EditEvent() {
        Intent i=getIntent();
        String event_id = i.getStringExtra("event_id");

        final String URL = "http://192.168.43.157:3000/events/"+event_id;
        final String Token = utils.getToken(this);
        String name = editTextName.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String place = editTextPlace.getText().toString().trim();
        String information = editTextInformation.getText().toString().trim();
        if(!name.isEmpty() && !date.isEmpty() && !place.isEmpty() && !information.isEmpty()) {
            JSONObject Event = new JSONObject();
            try {
                Event.put("name", name);
                Event.put("date", date);
                Event.put("subject", place);
                Event.put("information", information);
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
                    headers.put("JWT", Token);
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        }else{
            Toast.makeText(SettingEventActivity.this, "You have to complete all the information", Toast.LENGTH_SHORT).show();
        }

    }









    private void getOneEvent() {
        Intent i=getIntent();
        String event_id = i.getStringExtra("event_id");

        final String URL = "http://192.168.43.157:3000/events/"+event_id;
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
                        String Place = null;
                        String Information = null;
                        try {
                            name = response.getString("name");
                            Date = response.getString("date");
                            Place = response.getString("subject");
                            Information = response.getString("information");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        editTextName.setText(name);
                        editTextDate.setText(Date.substring(0,10));
                        editTextPlace.setText(Place);
                        editTextInformation.setText(Information);
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
