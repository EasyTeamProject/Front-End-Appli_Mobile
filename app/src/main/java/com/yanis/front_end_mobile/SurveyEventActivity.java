package com.yanis.front_end_mobile;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyEventActivity extends AppCompatActivity {

    public TextView textView;
    public PreferenceUtils utils;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_event);

        PreferenceUtils utils = new PreferenceUtils();
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_survey);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getAllSurveys(recyclerView);


    }







    public void onAddSurveyPressed(View view){
        Intent i =new Intent(this,AddSurveyActivity.class);
        startActivity(i);
    }










    private class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private CardView mCardView;
        private TextView mTextViewPrincipalQuestion;
        private TextView mTextViewAnswerOne;
        private TextView mTextViewNumberOne;
        private TextView mTextViewAnswerTwo;
        private TextView mTextViewNumberTwo;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            mCardView = itemView.findViewById(R.id.card_container_event_survey);
            mTextViewPrincipalQuestion = itemView.findViewById(R.id.principalQuestion);
            mTextViewAnswerOne = itemView.findViewById(R.id.TextViewAnswerOne);
            mTextViewNumberOne = itemView.findViewById(R.id.TextViewNumberOne);
            mTextViewAnswerTwo = itemView.findViewById(R.id.TextViewAnswerTwo);
            mTextViewNumberTwo = itemView.findViewById(R.id.TextViewNumberTwo);
        }
    }










    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
        private Context mCtx;

        private List<Survey> mlist;


        public RecyclerViewAdapter(List<Survey> list,Context context) {
            this.mlist=list;
            this.mCtx=context;
        }

        @NonNull
        @Override
        public SurveyEventActivity.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mCtx);
            View view=inflater.inflate(R.layout.card_view_survey,null);
            final Button buttonOne = view.findViewById(R.id.buttonOne);
            final Button buttonTwo = view.findViewById(R.id.buttonTwo);

            buttonOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (buttonOne.getText().toString().equals("+")) {
                            buttonOne.setText("-");
                            return;
                        }
                        if (buttonOne.getText().toString().equals("-")) {
                            buttonOne.setText("+");
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(v.getContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            } );

            buttonTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (buttonTwo.getText().toString().equals("+")) {
                            buttonTwo.setText("-");
                            return;
                        }
                        if (buttonTwo.getText().toString().equals("-")) {
                            buttonTwo.setText("+");
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(v.getContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            } );

            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SurveyEventActivity.RecyclerViewHolder recyclerViewHolder, int i) {
            recyclerViewHolder.mTextViewPrincipalQuestion.setText(mlist.get(i).getQuestion());
            recyclerViewHolder.mTextViewAnswerOne.setText(mlist.get(i).getAnswerOne());
            recyclerViewHolder.mTextViewNumberOne.setText("0");
            recyclerViewHolder.mTextViewAnswerTwo.setText(mlist.get(i).getAnswerTwo());
            recyclerViewHolder.mTextViewNumberTwo.setText("0");

        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }












    private void getAllSurveys(final RecyclerView recyclerView) {

        Intent i=getIntent();
        String event_id = i.getStringExtra("event_id");
        //TODO change the event
        final String URL = "http://192.168.43.157:3000/events/13/survey";
        final String Token = utils.getToken(this);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString());
                        try {
                            List<Survey> list=new ArrayList<>();
                            for (int i=0; i < response.length(); i++){
                                JSONObject jsonObject=response.getJSONObject(i);

                                JSONArray questions= (JSONArray)jsonObject.get("questions");

                                JSONObject firstObject = questions.getJSONObject(0);

                                JSONArray responses= (JSONArray)firstObject.get("responses");

                                JSONObject answerOne = responses.getJSONObject(0);
                                JSONObject answerTwo = responses.getJSONObject(1);

                                list.add(new Survey(jsonObject.getString("id"),
                                        jsonObject.getString("event_id"),
                                        firstObject.getString("question"),
                                        answerOne.getString("text"),
                                        answerTwo.getString("text")));
                            }
                            recyclerView.setAdapter(new RecyclerViewAdapter(list,SurveyEventActivity.this));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("info", "onResponse: OOOK dans l'exception");
                        }
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
