package com.example.mavexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    Runnable runnable;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.img);
        img.animate().alpha(2000).setDuration(0);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(MainActivity.this,loginActivity.class);
                startActivity(intent);
                finish();
            }
        },  2000);

    }
}