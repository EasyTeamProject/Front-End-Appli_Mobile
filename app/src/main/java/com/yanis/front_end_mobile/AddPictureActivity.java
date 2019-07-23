package com.yanis.front_end_mobile;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.common.internal.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        if(imageUri != null && !imageUri.equals(Uri.EMPTY)) {
            uploadNewProfilePicture(imageUri);
            Intent intent = new Intent(this, PictureEventActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else {
            Toast.makeText(AddPictureActivity.this, "You have to choose a picture from your gallery", Toast.LENGTH_SHORT).show();
        }
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






    private void uploadNewProfilePicture(Uri profilePicture) {
        Intent intent=getIntent();
        final String event_id = intent.getStringExtra("event_id");

        File file = new File(FileUtils.getPath(this, profilePicture));


        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(this.getContentResolver().getType(profilePicture)),
                        file
                );


        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Retrofit retrofit = NetworkClient.getRetrofitClient(this);
        UserService service = retrofit.create(UserService.class);


        Call<ResponseBody> call = service.setProfilePicture(utils.getToken(this), event_id, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddPictureActivity.this, "Picture added successfuly", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddPictureActivity.this, "Picture not added successfuly", Toast.LENGTH_SHORT).show();
                    Log.e("Error", "onResponse: "+ response.toString());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error", "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());

            }
        });
    }

}
