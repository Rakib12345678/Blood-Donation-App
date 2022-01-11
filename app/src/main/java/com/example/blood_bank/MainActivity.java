package com.example.blood_bank;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blood_bank.adapter.useradapter;
import com.example.blood_bank.model.user;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{
  private DrawerLayout drawerLayout;
  private Toolbar toolbar;
  private  RecyclerView recyclerView;
  private ProgressBar progressBar;
  private NavigationView navigationView;
  private CircleImageView nav_user_image;
  private TextView nav_user_fullname,nav_user_email,nav_bloodgroup,nav_user_type;
private DatabaseReference userref;
private List<user>userlist;
private useradapter useradap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       drawerLayout=findViewById(R.id.drawerlayout);
       recyclerView=findViewById(R.id.recyclerview);
       progressBar=findViewById(R.id.progressbar);
        toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        userlist=new ArrayList<>();
        useradap=new useradapter(MainActivity.this,userlist);
        recyclerView.setAdapter(useradap);
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

         ref.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 String type=snapshot.child("type").getValue().toString();
                 if(type.equals("donar"))
                 {
                    readrecepient();
                 }
                 else
                 {
                     readdonar();
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

        navigationView.setNavigationItemSelectedListener(this);
        nav_user_image=navigationView.getHeaderView(0).findViewById(R.id.nav_user_image);
        nav_user_fullname=navigationView.getHeaderView(0).findViewById(R.id.nav_user_fullname);
        nav_user_email=navigationView.getHeaderView(0).findViewById(R.id.nav_user_email);
        nav_user_type=navigationView.getHeaderView(0).findViewById(R.id.nav_user_type);
        nav_bloodgroup=navigationView.getHeaderView(0).findViewById(R.id.nav_user_bloodgroup);

    //    setSupportActionBar(toolbar);
   //     getSupportActionBar().setTitle("Blood donation app");

          userref= FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
          userref.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  if (snapshot.exists()) {
                      String name = snapshot.child("don_name").getValue().toString();
                      nav_user_fullname.setText(name);

                      String email = snapshot.child("don_email").getValue().toString();
                      nav_user_email.setText(email);

                      String type = snapshot.child("type").getValue().toString();
                      nav_user_type.setText(type);

                      String bloodgroup = snapshot.child("don_blood").getValue().toString();
                      nav_bloodgroup.setText(bloodgroup);
                      if(snapshot.hasChild("profilepictureurl")) {
                          String imageur = snapshot.child("profilepictureurl").getValue().toString();
                          Glide.with(getApplicationContext()).load(imageur).into(nav_user_image);
                      }
                      else
                      {
                          nav_user_image.setImageResource(R.drawable.profile_image);
                      }
                      Menu nav_menu=navigationView.getMenu();
                      if(type.equals("donar"))
                      {
                          nav_menu.findItem(R.id.email).setTitle("Received email");
                      }
                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }

          });
    }

    private void readdonar() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user");
        Query query = reference.orderByChild("type").equalTo("donar");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userlist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    user users = dataSnapshot.getValue(user.class);
                    userlist.add(users);

                }
                useradap.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                if (userlist.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No donar", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void readrecepient()
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("user");
        Query query=reference.orderByChild("type").equalTo("recepient");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userlist.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    user users=dataSnapshot.getValue(user.class);
                    userlist.add(users);

                }
                useradap.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                if(userlist.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "No recipients", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.aplus:
                Intent intent1=new Intent(MainActivity.this,category_selected.class);
                intent1.putExtra("group","A+");
                startActivity(intent1);
                break;



            case R.id.aminu:
                Intent intent2=new Intent(MainActivity.this,category_selected.class);
                intent2.putExtra("group","A-");
                startActivity(intent2);
                break;


            case R.id.bplus:
                Intent intent3=new Intent(MainActivity.this,category_selected.class);
                intent3.putExtra("group","B+");
                startActivity(intent3);
                break;

            case R.id.bminus:
                Intent intent4=new Intent(MainActivity.this,category_selected.class);
                intent4.putExtra("group","B-");
                startActivity(intent4);
                break;

            case R.id.abminus:
                Intent intent5=new Intent(MainActivity.this,category_selected.class);
                intent5.putExtra("group","AB-");
                startActivity(intent5);
                break;

            case R.id.ABplus:
                Intent intent6=new Intent(MainActivity.this,category_selected.class);
                intent6.putExtra("group","AB+");
                startActivity(intent6);
                break;

            case R.id.oplus:
                Intent intent7=new Intent(MainActivity.this,category_selected.class);
                intent7.putExtra("group","O+");
                startActivity(intent7);
                break;

            case R.id.ominus:
                Intent intent8=new Intent(MainActivity.this,category_selected.class);
                intent8.putExtra("group","O-");
                startActivity(intent8);
                break;
            case R.id.compatiable:
                Intent intent9=new Intent(MainActivity.this,category_selected.class);
                intent9.putExtra("group","sametype");
                startActivity(intent9);
                break;
            case R.id.email:
                Intent intent10=new Intent(MainActivity.this,SENT_EMAIL_ACTIVITY.class);

                break;

            case R.id.profile:
                startActivity(new Intent(MainActivity.this,Profileactivity.class));
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,loginactivity.class));
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}