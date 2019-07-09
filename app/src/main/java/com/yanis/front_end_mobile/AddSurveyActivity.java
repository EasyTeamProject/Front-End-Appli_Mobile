package com.yanis.front_end_mobile;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AddSurveyActivity extends AppCompatActivity {

    public EditText editTextQuestion;
    public EditText editTextAnswer1;
    public EditText editTextAnswer2;
    public PreferenceUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_survey);

        utils = new PreferenceUtils();

        editTextQuestion = (EditText)findViewById(R.id.editTextQuestionSurvey);
        editTextAnswer1 = (EditText)findViewById(R.id.editTextReponse1Survey);
        editTextAnswer2 = (EditText)findViewById(R.id.editTextReponse2Survey);
    }


    public void PostSurvey(View view) {
        final Intent intent=getIntent();
        String event_id = intent.getStringExtra("event_id");
        final String URL = "http://192.168.43.157:3000/events/"+event_id+"/survey";
        final String Token = utils.getToken(this);

        Log.i("survey", "PostSurvey: "+URL);

        try {
        JSONObject survey = new JSONObject();
        JSONArray questions = new JSONArray();
        JSONObject text = new JSONObject();

            text.put("text","questions 10000 ?");

        JSONArray responses = new JSONArray();
        responses.add("yes");
        responses.add("No");
        questions.add(text);
        questions.add(responses);
        survey.put("questions", questions);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                survey,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Survey", "onResponse: OOOOKKK pour l'ajout  ");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.i("Survey", "onResponse: KKKOOOOOO ");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
