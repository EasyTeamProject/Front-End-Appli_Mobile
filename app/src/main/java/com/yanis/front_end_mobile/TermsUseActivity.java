package com.yanis.front_end_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

public class TermsUseActivity extends AppCompatActivity {

    public TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_use);

        tv=(TextView)findViewById(R.id.termOfUseTextView);

        Spanned sp = Html.fromHtml(getString(R.string.term_of_use));
        tv.setText(sp);
    }
}
