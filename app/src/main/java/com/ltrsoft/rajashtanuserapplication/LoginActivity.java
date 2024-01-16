package com.ltrsoft.rajashtanuserapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.ltrsoft.rajashtanuserapplication.fragments.Login;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        if (pref.getBoolean("flag",false)){
            Intent main_activity_intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(main_activity_intent);
        }
        else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.session_container, new Login())
                    .commit();
        }


    }
}