package com.yanis.front_end_mobile;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddEventFragment extends Fragment {

    public EditText editTextName;
    public EditText editTextDate;
    public Activity context;
    public PreferenceUtils utils;

    public AddEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity();
        utils = new PreferenceUtils();
        View v= inflater.inflate(R.layout.fragment_add_event, container, false);
        editTextName= (EditText) v.findViewById(R.id.editTextNameAddEvent);
        editTextDate= (EditText) v.findViewById(R.id.editTextDateAddEvent);
        return v;
    }
    public void onStart(){
        super.onStart();
        Button btnAddEvent=(Button)context.findViewById(R.id.buttonAddEvent);
        btnAddEvent.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                postDataWithAccessToken();
            }
        });
    }

    /*


requestQueue.add(objectRequest);*/

    private void postDataWithAccessToken() {

        final String URL = "http://192.168.43.157:3000/events?name="+editTextName.getText().toString().trim()+"&date="+editTextDate.getText().toString().trim();
        final String Token = utils.getToken(context);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Event add successfuly", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(context, "Error to add event", Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);

    }

}
