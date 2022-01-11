package com.example.blood_bank;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blood_bank.adapter.useradapter;
import com.example.blood_bank.model.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SENT_EMAIL_ACTIVITY extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<user> userlist;
    Context context;
    private List<String> idlist;
    private useradapter ud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_email);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("people sent email");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        userlist = new ArrayList<>();
        ud = new useradapter(SENT_EMAIL_ACTIVITY.this, userlist);
        recyclerView.setAdapter(ud);
        idlist = new ArrayList<>();
        getidofuser();

    }

    private void getidofuser() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("emails").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                idlist.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    idlist.add(snapshot1.getKey());
                }
                showuser();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void showuser()
    {

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("emails").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid()) ;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                userlist.clear();
                for(DataSnapshot dataSnapshot1:snapshot.getChildren())
                {
                    user us=dataSnapshot1.getValue(user.class);
                    for(String id:idlist)
                    {
                        if(us.getId().equals(id))
                        {
                            userlist.add(us);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {


            }
        });


    }
}