package com.example.familymapclient;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class SettingsActivity extends AppCompatActivity {

    Context here = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        RelativeLayout logoutView = findViewById(R.id.logoutView);

        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(here, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
