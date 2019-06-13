package com.yanis.front_end_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onSuscribePressed(View view){
        Intent i =new Intent(this,SuscribeActivity.class);
        startActivity(i);
    }

    public void onLoginPressed(View view){
        Intent i =new Intent(this,HomeActivity.class);
        startActivity(i);
    }
}
