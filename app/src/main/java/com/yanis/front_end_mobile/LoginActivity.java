package com.yanis.front_end_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    public EditText editTextEmailLogin;
    public EditText editTextPasswordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmailLogin=(EditText)findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin=(EditText)findViewById(R.id.editTextPasswordLogin);
    }

    public void onSuscribePressed(View view){
        Intent i =new Intent(this,SuscribeActivity.class);
        startActivity(i);
    }


    public void onLogin(){

        PreferenceUtils.saveEmail(editTextEmailLogin.getText().toString().trim(), this);
        PreferenceUtils.savePassword(editTextPasswordLogin.getText().toString().trim(), this);

        Intent accountsIntent = new Intent(this, HomeActivity.class);
        emptyInputEditText();
        startActivity(accountsIntent);
        finish();
    }

    public void login(final View view){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String URL = "http://192.168.1.12:3000/sessions?email="+editTextEmailLogin.getText().toString().trim()+"&password="+editTextPasswordLogin.getText().toString().trim();
            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    URL,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), "Login successfuly", Toast.LENGTH_SHORT).show();
                            onLogin();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error to login", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            requestQueue.add(objectRequest);

    }

    private void emptyInputEditText(){
        editTextEmailLogin.setText(null);
        editTextPasswordLogin.setText(null);
    }
}
