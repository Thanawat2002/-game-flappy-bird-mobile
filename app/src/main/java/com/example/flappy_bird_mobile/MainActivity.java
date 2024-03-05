package com.example.flappy_bird_mobile;

import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new FlappyBird(this));
    }
}