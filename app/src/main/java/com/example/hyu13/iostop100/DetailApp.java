package com.example.hyu13.iostop100;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailApp extends AppCompatActivity {

    private static final String TAG = "Detail";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_details);

        retrieveIntent();

    }

    private void retrieveIntent(){
        //check to see if extras exist to prevent app crash
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("title")){

            //If extras exist then get the string extra
            String imageUrlDetail = getIntent().getStringExtra("image_url");
            String titleDetail = getIntent().getStringExtra("title");

            setDetails(imageUrlDetail, titleDetail);
        }
    }

    private void setDetails(String imageUrl, String titleDetail){

        TextView name = findViewById(R.id.titleDetail);
        name.setText(titleDetail);

        ImageView imageView = findViewById(R.id.imageDetail);
        Glide.with(this).asBitmap().load(imageUrl).into(imageView);
    }
}
