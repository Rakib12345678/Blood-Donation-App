package com.example.blood_bank;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blood_bank.adapter.useradapter;
import com.example.blood_bank.model.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class category_selected extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private List<user> users;
    private useradapter useradap;
    private String tytle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selected);
        // toolbar=findViewById(R.id.toolbar);
        //  progressBar=findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recyclerview);
        users = new ArrayList<>();
        /*
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.hide();
        }*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        users = new ArrayList<>();
        useradap = new useradapter(category_selected.this, users);
        recyclerView.setAdapter(useradap);

        if (getIntent().getExtras() != null) {
            tytle = getIntent().getStringExtra("group");
            if (tytle.equals("sametype")) {
                same_user();
            } else {
                //getSupportActionBar().setTitle("blood group"+tytle);
                readusers();
            }
        }

    }

    private void same_user() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result;
                String type = snapshot.child("type").getValue().toString();
                if (type.equals("donar")) {
                    result = "recipient_cus";
                } else {
                    result = "don_cus";
                }
                String bloodgroup = snapshot.child("don_blood").getValue().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user");
                Query query = reference.orderByChild("search").equalTo(result + bloodgroup);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        users.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                user us = snap.getValue(user.class);
                                users.add(us);
                            }
                            useradap.notifyDataSetChanged();
                        } else {
                            Toast.makeText(category_selected.this, "data not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readusers() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result;
                String type = snapshot.child("type").getValue().toString();
                if (type.equals("donar")) {
                    result = "recipient_cus";
                } else {
                    result = "don_cus";
                }
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user");
                Query query = reference.orderByChild("search").equalTo(result + tytle);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        users.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                user us = snap.getValue(user.class);
                                users.add(us);
                            }
                            useradap.notifyDataSetChanged();
                        } else {
                            Toast.makeText(category_selected.this, "data not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }


    }
}
