package com.yanis.front_end_mobile;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailsEventActivity extends AppCompatActivity {

    TextView mEventId,mEventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_event);

        mEventId=(TextView)findViewById(R.id.event_detail_id);
        mEventName=(TextView)findViewById(R.id.event_detail_name);

        Intent intent=getIntent();
        String mId = intent.getStringExtra("iId");
        String mName = intent.getStringExtra("iName");

        mEventId.setText(mId);
        mEventName.setText(mName);
    }


    public void onChatPressed(View view){
        Intent i =new Intent(this,ChatEventActivity.class);
        startActivity(i);
    }


    public void onInfoPressed(View view){
        Intent i =new Intent(this,InfoEventActivity.class);
        startActivity(i);
    }


    public void onSettingPressed(View view){
        Intent i =new Intent(this,SettingEventActivity.class);
        startActivity(i);
    }


    public void onCashPressed(View view){
        Intent i =new Intent(this,CashEventActivity.class);
        startActivity(i);
    }


    public void onFriendPressed(View view){
        Intent i =new Intent(this,FriendEventActivity.class);
        startActivity(i);
    }


    public void onSurveyPressed(View view){
        Intent i =new Intent(this,SurveyEventActivity.class);
        startActivity(i);
    }
}
