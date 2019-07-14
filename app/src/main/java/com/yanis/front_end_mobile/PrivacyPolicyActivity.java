package com.yanis.front_end_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

public class PrivacyPolicyActivity extends AppCompatActivity {

    public TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        tv=(TextView)findViewById(R.id.privacy_text_view);

        Spanned sp = Html.fromHtml(getString(R.string.some_text));
        tv.setText(sp);
    }
}
