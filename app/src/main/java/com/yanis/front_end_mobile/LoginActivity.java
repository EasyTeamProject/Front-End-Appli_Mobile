package com.yanis.front_end_mobile;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
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
    public FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();

        editTextEmailLogin=(EditText)findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin=(EditText)findViewById(R.id.editTextPasswordLogin);
        initViews();
    }

    public void onSuscribePressed(View view){
        Intent i =new Intent(this,SuscribeActivity.class);
        startActivity(i);
    }


    public void onLogin(String token){
        Toast.makeText(getApplicationContext(), "Login successfuly", Toast.LENGTH_SHORT).show();

        PreferenceUtils.saveEmail(editTextEmailLogin.getText().toString().trim(), this);
        PreferenceUtils.savePassword(editTextPasswordLogin.getText().toString().trim(), this);
        PreferenceUtils.saveToken(token, this);

        Intent accountsIntent = new Intent(this, HomeActivity.class);
        emptyInputEditText();
        startActivity(accountsIntent);
        finish();
    }

    public void login(final View view){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        firebaseAuth.signInWithEmailAndPassword(editTextEmailLogin.getText().toString(),editTextPasswordLogin.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Log.i("success","okkk");
                        }else{
                            Log.e("ERROR",task.getException().toString());
                            Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

        String URL = "http://192.168.43.157:3000/sessions?email="+editTextEmailLogin.getText().toString().trim()+"&password="+editTextPasswordLogin.getText().toString().trim();
            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    URL,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getString("error")!=null){
                                    Toast.makeText(getApplicationContext(), "Error to login : Email or password are incorrect", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                try {
                                    onLogin(response.getString("success"));
                                } catch (JSONException e1) {
                                    Toast.makeText(getApplicationContext(), "Error to login : Email or password are incorrect", Toast.LENGTH_SHORT).show();
                                    e1.printStackTrace();
                                }
                                e.printStackTrace();
                            }
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

    private void initViews(){
        PreferenceUtils utils = new PreferenceUtils();

        if (utils.getEmail(this) != null ){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
