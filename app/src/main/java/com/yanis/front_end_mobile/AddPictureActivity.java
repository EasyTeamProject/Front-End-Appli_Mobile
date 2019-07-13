package com.yanis.front_end_mobile;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class AddPictureActivity extends AppCompatActivity {

    ImageView imageView;
    Button buttonChoose;
    Button buttonSave;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    public PreferenceUtils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);

        imageView=(ImageView)findViewById(R.id.image_to_add);
        buttonChoose=(Button)findViewById(R.id.button_choose_picture);
        buttonSave=(Button)findViewById(R.id.button_save_picture);

    }

    public void onChooseClick(View view){
        openGallery();
    }

    public void onSaveClick(View view){
        addPicture();
        Intent intent = new Intent(this,PictureEventActivity.class);
        startActivity(intent);
    }

    public void openGallery(){
        Intent gallery= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }

    }



    private void addPicture() {

        final String URL = "http://192.168.43.157:3000/events/5/medias";
        final String Token = utils.getToken(this);

        JSONObject image = new JSONObject();
        try {
            image.put("image",new File(imageUri.getPath()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                image,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(AddPictureActivity.this, "Picture added successfuly", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(AddPictureActivity.this, "Picture not added successfuly", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "bearer");
                headers.put("Content-Type", "application/json");
                headers.put("JWT",Token);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }
}
