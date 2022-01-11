package com.example.blood_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class register_activity extends AppCompatActivity {
    private Button recipient,donar;
    private TextView backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        donar=findViewById(R.id.doner_button);
        recipient=findViewById(R.id.recipient_button);
        backbutton=findViewById(R.id.backbutton);
        recipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(register_activity.this,recepient_registration_activity.class));
            }
        });


        donar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(register_activity.this,donar_registration_activity.class));
            }
        });


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(register_activity.this,loginactivity.class));
            }
        });
    }
    }
