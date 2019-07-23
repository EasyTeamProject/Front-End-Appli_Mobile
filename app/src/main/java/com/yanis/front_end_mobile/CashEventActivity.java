package com.yanis.front_end_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashEventActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public PreferenceUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_event);
        PreferenceUtils utils = new PreferenceUtils();
        getAllFriends();
        tabLayout=(TabLayout)findViewById(R.id.tablayout_id);
        viewPager=(ViewPager)findViewById(R.id.viewpager_id);

        ViewPageAdapter viewPageAdapter=new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new RefundFragment(),"Refund");
        viewPageAdapter.addFragment(new ExpensesFragment(),"Expenses");

        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }









    public void onAddRefundPressed(View view){
        Intent intent =new Intent(this,AddRefundActivity.class);
        Intent i=getIntent();
        String event_id = i.getStringExtra("event_id");
        intent.putExtra("event_id",event_id);
        startActivity(intent);
    }

    public void onAddExpensePressed(View view){
        Intent intent =new Intent(this,AddExpenseActivity.class);
        Intent i=getIntent();
        String event_id = i.getStringExtra("event_id");
        intent.putExtra("event_id",event_id);
        startActivity(intent);
    }





    public void getAllFriends() {

        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        String event_id= m.getString("event_id", "");
        Log.e("event", "getAllFriends: "+event_id );


        final String URL = "http://192.168.43.157:3000/events/"+event_id;
        final String Token = utils.getToken(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(CashEventActivity.this);
                        SharedPreferences.Editor editor = m.edit();
                        editor.putString("Response", response.toString());
                        editor.commit();
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
                headers.put("JWT", Token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}
