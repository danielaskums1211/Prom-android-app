package com.example.bagrutproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {//after the splashscreen is shown for a couple seconds it switches to the login activity
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, LoginActivity.class));

    }
}
