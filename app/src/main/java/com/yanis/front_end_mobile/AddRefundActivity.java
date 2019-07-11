package com.yanis.front_end_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddRefundActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public PreferenceUtils utils;
    public Spinner spinner;
    public ArrayList<String> arrayList;
    public HashMap<String,String> hashMap;
    public String name;
    public EditText price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_refund);

        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        String str= m.getString("Response", "");
        arrayList = new ArrayList<>();
        hashMap= new HashMap<>();
        price = (EditText)findViewById(R.id.editTextPriceRefund);
        JSONObject jsonObject ;


        try {
            jsonObject = new JSONObject(str);
            JSONArray friendResponse = (JSONArray)jsonObject.get("members");
            for (int i=0; i < friendResponse.length(); i++){
                JSONObject friendObject=friendResponse.getJSONObject(i);
                arrayList.add(friendObject.getString("first_name") + " " + friendObject.getString("last_name"));
                hashMap.put(friendObject.getString("first_name") + " " + friendObject.getString("last_name"),friendObject.getString("user_id"));
            }

        spinner = (Spinner) findViewById(R.id.spinnerUser);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, arrayList);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



    public void onAddPressed(View view) {
        Intent i = new Intent(this, CashEventActivity.class);
        addRefund();
        startActivity(i);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        name = parent.getItemAtPosition(position).toString();

        Toast.makeText(parent.getContext(), name, Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    private void addRefund() {

        final String URL = "http://192.168.43.157:3000/events/5/transactions?from_id=15";
        final String Token = utils.getToken(this);

        JSONObject refund = new JSONObject();
        try {
            refund.put("to_id",hashMap.get(name));
            refund.put("amount",price.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                refund,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(AddRefundActivity.this, "Refund added successfuly", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(AddRefundActivity.this, "Refund not added successfuly", Toast.LENGTH_SHORT).show();
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