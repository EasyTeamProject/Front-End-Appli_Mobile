package com.yanis.front_end_mobile;

import android.content.Intent;
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

        mEventName=(TextView)findViewById(R.id.event_detail_name);

        Intent intent=getIntent();
        String mName = intent.getStringExtra("iName");

        mEventName.setText(mName);
    }


    public void onChatPressed(View view){
        Intent intent =new Intent(this,ChatEventActivity.class);
        Intent i=getIntent();
        String event_id = i.getStringExtra("iId");
        intent.putExtra("event_id",event_id);
        startActivity(intent);
    }


    public void onInfoPressed(View view){
        Intent i =new Intent(this, PictureEventActivity.class);
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

        Intent intent=new Intent(this,FriendEventActivity.class);
        Intent i=getIntent();
        String event_name = i.getStringExtra("iName");
        String event_id = i.getStringExtra("iId");
        intent.putExtra("event_id",event_id);
        intent.putExtra("event_name",event_name);
        this.startActivity(intent);
    }


    public void onSurveyPressed(View view){
        Intent intent =new Intent(this,SurveyEventActivity.class);
        Intent i=getIntent();
        String event_id = i.getStringExtra("iId");
        intent.putExtra("event_id",event_id);
        startActivity(intent);
    }
}
