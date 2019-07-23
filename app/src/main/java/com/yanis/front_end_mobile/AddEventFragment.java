package com.yanis.front_end_mobile;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
    public EditText editTextPlace;
    public EditText editTextInformation;
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
        editTextPlace= (EditText) v.findViewById(R.id.editTextPlace);
        editTextInformation= (EditText) v.findViewById(R.id.editTextInformation);
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


    private void postDataWithAccessToken() {

        final String URL = "http://192.168.43.157:3000/events?name="+editTextName.getText().toString().trim()+"&date="+editTextDate.getText().toString().trim()+"&subject="+editTextPlace.getText().toString().trim()+"&information="+editTextInformation.getText().toString().trim();
        final String Token = utils.getToken(context);

        if(!editTextName.getText().toString().trim().isEmpty() && !editTextDate.getText().toString().trim().isEmpty() && !editTextPlace.getText().toString().trim().isEmpty() && !editTextInformation.getText().toString().trim().isEmpty()) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    URL,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(context, "Event add successfuly", Toast.LENGTH_SHORT).show();
                            Intent i =new Intent(context,HomeActivity.class);
                            startActivity(i);
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
                    headers.put("JWT", Token);
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);
        }else{
            Toast.makeText(context, "You have to complete all the information to create an event", Toast.LENGTH_SHORT).show();
        }

    }

}
