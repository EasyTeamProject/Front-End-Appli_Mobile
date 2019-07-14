package com.yanis.front_end_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SuscribeActivity extends AppCompatActivity {

    public EditText editTextEmail;
    public EditText editTextPassword;
    public EditText editTextConfirmPassword;
    public CheckBox checkBoxOne;
    public CheckBox checkBoxTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscribe);

        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);

        checkBoxOne=(CheckBox)findViewById(R.id.checkBox);
        checkBoxTwo=(CheckBox)findViewById(R.id.checkBox2);
    }
    public void onBackToLoginPressed(){
        Intent i =new Intent(this,LoginActivity.class);
        startActivity(i);
    }

    public void onPrivacyPolicyPressed(View view){
        Intent i =new Intent(this,PrivacyPolicyActivity.class);
        startActivity(i);
    }

    public void onTermsPressed(View view){
        Intent i =new Intent(this,TermsUseActivity.class);
        startActivity(i);
    }


    public void subscribe(final View view){
        if(checkBoxTwo.isChecked() && checkBoxOne.isChecked()) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String URL = "http://192.168.43.157:3000/users?email=" + editTextEmail.getText().toString() + "&password=" + editTextPassword.getText().toString();
            if (editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())) {
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        URL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getApplicationContext(), "Account created successfuly", Toast.LENGTH_SHORT).show();
                                onBackToLoginPressed();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error to create an account", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                requestQueue.add(objectRequest);
            } else {
                Toast.makeText(getApplicationContext(), "The password and the confirm password are not similar", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "You have to accept the terms of use and the privacy policy", Toast.LENGTH_SHORT).show();

        }
    }
}
