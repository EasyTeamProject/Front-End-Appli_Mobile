package com.yanis.front_end_mobile;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        private TextView mTextViewUserSurvey;
        private TextView mTextViewPrincipalQuestion;
        private TextView mTextViewAnswerOne;
        private TextView mTextViewNumberOne;
        private TextView mTextViewAnswerTwo;
        private TextView mTextViewNumberTwo;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            mCardView = itemView.findViewById(R.id.card_container_event_survey);
            mTextViewUserSurvey = itemView.findViewById(R.id.TextViewUserSurvey);
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
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SurveyEventActivity.RecyclerViewHolder recyclerViewHolder, int i) {
            recyclerViewHolder.mTextViewUserSurvey.setText(mlist.get(i).getNameUser());
            recyclerViewHolder.mTextViewPrincipalQuestion.setText(mlist.get(i).getQuestion());
            //recyclerViewHolder.mTextViewAnswerOne.setText(mlist.get(i).getAnswerOne().getAnswer());
            //recyclerViewHolder.mTextViewNumberOne.setText(mlist.get(i).getAnswerOne().getNumber_answer());
            //recyclerViewHolder.mTextViewAnswerTwo.setText(mlist.get(i).getAnswerOne().getAnswer());
            //recyclerViewHolder.mTextViewNumberTwo.setText(mlist.get(i).getAnswerOne().getNumber_answer());

        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }












    private void getAllSurveys(final RecyclerView recyclerView) {

        final String URL = "https://api.myjson.com/bins/u0aiz";
        final String Token = utils.getToken(this);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<Survey> list=new ArrayList<>();
                            for (int i=0; i < response.length(); i++){
                                JSONObject jsonObject=response.getJSONObject(i);
                                list.add(new Survey(jsonObject.getString("id"),
                                        jsonObject.getString("id_event"),
                                        jsonObject.getString("nameUser"),
                                        jsonObject.getString("question")
                                ));
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
