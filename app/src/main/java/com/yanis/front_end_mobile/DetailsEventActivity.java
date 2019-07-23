package com.yanis.front_end_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(DetailsEventActivity.this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("event_id", event_id);
        editor.commit();

        startActivity(intent);
    }


    public void onInfoPressed(View view){
        Intent intent =new Intent(this, PictureEventActivity.class);
        Intent i=getIntent();
        String event_id = i.getStringExtra("iId");
        intent.putExtra("event_id",event_id);

        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(DetailsEventActivity.this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("event_id", event_id);
        editor.commit();

        startActivity(intent);
    }


    public void onSettingPressed(View view){
        Intent intent =new Intent(this,SettingEventActivity.class);
        Intent i=getIntent();
        String event_id = i.getStringExtra("iId");
        intent.putExtra("event_id",event_id);

        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(DetailsEventActivity.this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("event_id", event_id);
        editor.commit();

        startActivity(intent);
    }


    public void onCashPressed(View view){
        Intent intent =new Intent(this,CashEventActivity.class);
        Intent i=getIntent();
        String event_id = i.getStringExtra("iId");
        intent.putExtra("event_id",event_id);

        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(DetailsEventActivity.this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("event_id", event_id);
        editor.commit();

        startActivity(intent);
    }


    public void onFriendPressed(View view){

        Intent intent=new Intent(this,FriendEventActivity.class);
        Intent i=getIntent();
        String event_name = i.getStringExtra("iName");
        String event_id = i.getStringExtra("iId");
        intent.putExtra("event_id",event_id);
        intent.putExtra("event_name",event_name);

        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(DetailsEventActivity.this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("event_id", event_id);
        editor.commit();

        this.startActivity(intent);
    }


    public void onSurveyPressed(View view){
        Intent intent =new Intent(this,SurveyEventActivity.class);
        Intent i=getIntent();
        String event_id = i.getStringExtra("iId");
        intent.putExtra("event_id",event_id);

        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(DetailsEventActivity.this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("event_id", event_id);
        editor.commit();

        startActivity(intent);
    }
}
