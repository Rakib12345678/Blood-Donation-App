package com.example.blood_bank;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profileactivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView type,phoneno,idno,bloodgroup,name,email;
    private CircleImageView profileimage;
    Button backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileactivity);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }
        toolbar=findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("my profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        type=findViewById(R.id.type);
        phoneno=findViewById(R.id.phoneno);
        idno=findViewById(R.id.idno);
        bloodgroup=findViewById(R.id.bloodgroup);
        email=findViewById(R.id.email);
        name=findViewById(R.id.name);
        backbutton=findViewById(R.id.backbutton);
        profileimage=findViewById(R.id.profile_image);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()) ;

     databaseReference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             if(snapshot.exists())
             { if(snapshot.hasChild("profilepictureurl")) {
                 Glide.with(getApplicationContext()).load(snapshot.child("profilepictureurl").getValue().toString()).into(profileimage);
             }
            type.setText(snapshot.child("don_name").getValue().toString());
            phoneno.setText(snapshot.child("don_ph").getValue().toString());
            idno.setText(snapshot.child("don_id").getValue().toString());
            bloodgroup.setText(snapshot.child("don_blood").getValue().toString());
            email.setText(snapshot.child("don_email").getValue().toString());
            name.setText(snapshot.child("don_name").getValue().toString());

             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });
     backbutton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             startActivity(new Intent(Profileactivity.this,MainActivity.class));
         }
     });
    }
    /*

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return  true;
            default:
                return  super.onOptionsItemSelected(item);

        }
    }

     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pro,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this,"clicked"+item.getTitle(),Toast.LENGTH_SHORT).show();
        return true;
    }
}