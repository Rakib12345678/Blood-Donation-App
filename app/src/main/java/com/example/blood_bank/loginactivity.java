package com.example.blood_bank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginactivity extends AppCompatActivity {
    private   TextInputEditText login_email,login_password;
     private Button logIn_button;
     private TextView backbutton,forget_password;
    private FirebaseAuth rauth;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        login_email=findViewById(R.id.login_email);
        login_password=findViewById(R.id.login_password);
        logIn_button=findViewById(R.id.logIn_button);
        forget_password=findViewById(R.id.forget_password);
        backbutton=findViewById(R.id.backbutton);
        rauth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user=rauth.getCurrentUser();
                if(user!=null)
                {
                    Toast.makeText(loginactivity.this, "You are logged in ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(loginactivity.this,MainActivity.class));

                }
            }
        };
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginactivity.this,register_activity.class));
            }
        });

        logIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  String email=login_email.getText().toString().trim();
                final  String password=login_password.getText().toString().trim();
                if(TextUtils.isEmpty(email))
                {
                    login_email.setError("email is required");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    login_password.setError("password is required");
                    return;

                }
                else
                {
                    rauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(loginactivity.this, "You are logged in ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(loginactivity.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(loginactivity.this, "some error"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        rauth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        rauth.removeAuthStateListener(authStateListener);
    }
}