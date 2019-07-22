package com.yanis.front_end_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class PersonelInformationActivity extends AppCompatActivity {
    public Button buttonName;
    public Button buttonEmail;

    public PreferenceUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personel_information);

        buttonName = (Button)findViewById(R.id.nameProfil);
        buttonEmail = (Button) findViewById(R.id.emailProfil);

        buttonName.setText(utils.getName(this));
        buttonEmail.setText(utils.getEmail(this));
    }
}
