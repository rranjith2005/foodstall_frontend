package com.saveetha.foodstall;
import com.saveetha.foodstall.USignupActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 3000; // 3 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Optional: move to login/signup screen after splash
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, USignupActivity.class); // change target if needed
            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }


}
