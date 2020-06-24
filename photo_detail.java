package com.example.getrecentphotos;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class photo_detail extends AppCompatActivity {

    ImageView img_detail;
    photoClass newitem1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("New Details");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        img_detail = findViewById(R.id.detail_image);
        newitem1 = new photoClass();
        newitem1 = (photoClass) getIntent().getSerializableExtra("selectednew");

        if (newitem1.getBitmap()== null){

            new ImageDownloadTask(img_detail).execute(newitem1);


        }
        else{
            img_detail.setImageBitmap(newitem1.getBitmap());
        }



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;



        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}

