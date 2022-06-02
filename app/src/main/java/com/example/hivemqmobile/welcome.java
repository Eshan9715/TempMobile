package com.example.hivemqmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class welcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        Button welcome =(Button)findViewById(R.id.btn_continue);
        ImageView penguin = (ImageView) findViewById(R.id.penguin);

        Glide.with(this).load(R.drawable.penguin).into(penguin);


        welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSecondActivity =new Intent(welcome.this, Settings.class);
                startActivity(openSecondActivity);

            }
        });

    }
}