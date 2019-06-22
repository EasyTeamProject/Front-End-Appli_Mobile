package com.yanis.front_end_mobile;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
}
