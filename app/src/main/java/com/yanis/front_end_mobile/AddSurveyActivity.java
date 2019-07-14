package com.yanis.front_end_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddSurveyActivity extends AppCompatActivity {

    DatabaseReference firebaseDatabase;
    EditText questionSurvey;
    EditText answerOneSurvey;
    EditText answerTwoSurvey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_survey);

        questionSurvey= (EditText)findViewById(R.id.editTextQuestionSurvey);
        answerOneSurvey= (EditText)findViewById(R.id.editTextReponse1Survey);
        answerTwoSurvey= (EditText)findViewById(R.id.editTextReponse2Survey);
    }



    public void onAddSurvey(View view){
        Intent i=getIntent();
        String event_id = i.getStringExtra("event_id");
        System.out.println(event_id);

        FirebaseDatabase.getInstance().getReference().child("survey").push().setValue(new Survey(event_id,questionSurvey.getText().toString(),answerOneSurvey.getText().toString(),0,answerTwoSurvey.getText().toString(),0));

        Intent intent =new Intent(this,SurveyEventActivity.class);
        startActivity(intent);
        finish();

    }

}
