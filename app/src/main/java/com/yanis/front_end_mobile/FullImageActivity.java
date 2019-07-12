package com.yanis.front_end_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FullImageActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        imageView = (ImageView)findViewById(R.id.full_image);
        String image = getIntent().getExtras().getString("image");
        Picasso.get().load(image).resize(500, 500).centerCrop().into(imageView);

    }
}
