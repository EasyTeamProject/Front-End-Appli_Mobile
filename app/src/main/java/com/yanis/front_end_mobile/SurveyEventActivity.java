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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_event);

        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("survey");

        PreferenceUtils utils = new PreferenceUtils();
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_survey);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getAllSurveys(recyclerView);


    }



    public void onAddSurveyPressed(View view){
        Intent intent =new Intent(this,AddSurveyActivity.class);
        Intent i=getIntent();
        String event_id = i.getStringExtra("event_id");
        intent.putExtra("event_id",event_id);
        startActivity(intent);
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

        private ArrayList<Survey> mlist;


        public RecyclerViewAdapter(ArrayList<Survey> list,Context context) {
            this.mlist=list;
            this.mCtx=context;
        }

        @NonNull
        @Override
        public SurveyEventActivity.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
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
                            FirebaseDatabase.getInstance().getReference().child("survey").child(mlist.get(i).getId()).child("numberAnswerOne").setValue(mlist.get(i).getNumberAnswerOne()+1);
                            return;
                        }
                        if (buttonOne.getText().toString().equals("-")) {
                            buttonOne.setText("+");
                            FirebaseDatabase.getInstance().getReference().child("survey").child(mlist.get(i).getId()).child("numberAnswerOne").setValue(mlist.get(i).getNumberAnswerOne()-1);
                            return;
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
                            FirebaseDatabase.getInstance().getReference().child("survey").child(mlist.get(i).getId()).child("numberAnswerTwo").setValue(mlist.get(i).getNumberAnswerTwo()+1);
                            return;
                        }
                        if (buttonTwo.getText().toString().equals("-")) {
                            buttonTwo.setText("+");
                            FirebaseDatabase.getInstance().getReference().child("survey").child(mlist.get(i).getId()).child("numberAnswerTwo").setValue(mlist.get(i).getNumberAnswerTwo()-1);
                            return;
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
            recyclerViewHolder.mTextViewNumberOne.setText(Long.toString(mlist.get(i).getNumberAnswerOne()));
            recyclerViewHolder.mTextViewAnswerTwo.setText(mlist.get(i).getAnswerTwo());
            recyclerViewHolder.mTextViewNumberTwo.setText(Long.toString(mlist.get(i).getNumberAnswerTwo()));

        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }












    private void getAllSurveys(final RecyclerView recyclerView) {

        Intent i=getIntent();
        final String event_id = i.getStringExtra("event_id");

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Survey> list=new ArrayList<>();
                for(DataSnapshot s :dataSnapshot.getChildren()){

                    Survey survey = s.getValue(Survey.class);

                    if(survey.getId_event().equals(event_id)){
                        list.add(new Survey(s.getKey(),survey.getId_event(),survey.getQuestion(),survey.answerOne,survey.getNumberAnswerOne(),survey.getAnswerTwo(),survey.getNumberAnswerTwo()));
                    }

                }
                recyclerView.setAdapter(new SurveyEventActivity.RecyclerViewAdapter(list,SurveyEventActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
