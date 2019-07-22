package com.yanis.front_end_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    public EditText editTextEmailLogin;
    public EditText editTextPasswordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmailLogin=(EditText)findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin=(EditText)findViewById(R.id.editTextPasswordLogin);
        initViews();
    }

    public void onSuscribePressed(View view){
        Intent i =new Intent(this,SuscribeActivity.class);
        startActivity(i);
    }


    public void onLogin(String token, long id, String name){
        Toast.makeText(getApplicationContext(), "Welcome "+name, Toast.LENGTH_SHORT).show();

        PreferenceUtils.saveEmail(editTextEmailLogin.getText().toString().trim(), this);
        PreferenceUtils.saveToken(token, this);
        PreferenceUtils.saveName(name, this);
        PreferenceUtils.saveId(id, this);

        Intent accountsIntent = new Intent(this, HomeActivity.class);
        emptyInputEditText();
        startActivity(accountsIntent);
        finish();
    }

    public void login(final View view){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

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
                                    JSONObject jsonObject = response.getJSONObject("user");
                                    onLogin(response.getString("success"),Long.parseLong(jsonObject.getString("id")),jsonObject.getString("name"));
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
