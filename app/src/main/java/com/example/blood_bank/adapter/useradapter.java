package com.example.blood_bank.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blood_bank.R;
import com.example.blood_bank.email_customar.email_option;
import com.example.blood_bank.model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class useradapter extends RecyclerView.Adapter<useradapter.userviewholder> {
   private final  Context context;
   private final  List<user>userList;

    public useradapter(Context context, List<user> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public userviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_holder,parent,false);
        return new userviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userviewholder holder, int position)
    {
     final user userlis=userList.get(position);

       holder.username.setText(userList.get(position).getDon_name());
       holder.usergroup.setText(userList.get(position).getDon_blood());
       holder.userph.setText(userList.get(position).getDon_ph());
       holder.useremail.setText(userList.get(position).getDon_email());
       holder.user_type.setText(userList.get(position).getType());
       if(userList.get(position).getType().equals("donar"))
       {
           holder.userbutton.setVisibility(View.VISIBLE);
       }
       //Glide.with(context).load(userLis.getProfilepictureurl()).into(holder.userprofileimage);
     //Glide.with(context).load(userlis.getProfilepictureurl()).into(holder.userprofileimage);
        final String nameoftherecever=userlis.getDon_name();
       final String idoftherecever=userlis.getId();
       holder.userbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               new AlertDialog.Builder(context).setTitle("send email").setMessage("send email to"+userlis.getDon_name()+"?").setCancelable(false)
                       .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("user")
                                       .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                               databaseReference.addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                                       String nameofsender=snapshot.child("don_name").getValue().toString();
                                       String email=snapshot.child("don_email").getValue().toString();
                                       String phone=snapshot.child("don_ph").getValue().toString();
                                       String blood=snapshot.child("don_blood").getValue().toString();
                                       String memail=userlis.getDon_email();
                                       String msubject="Blood Donation";
                                       String mMessage="hello"+nameoftherecever+","+nameofsender+"would you like blood donation from you.here his/her details:\n"+
                                               "Name:"+nameofsender+"\n"+
                                               "phone No"+phone+"\n"+
                                               "email:"+email+"\n"+
                                               "blood group"+blood+"\n"+
                                               "kindly reach out to him/her thank you";
                                       email_option ep=new email_option(context,memail,msubject,mMessage);
                                       ep.execute();
                                       DatabaseReference senderref=FirebaseDatabase.getInstance().getReference("emails").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                       senderref.child(idoftherecever).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful())
                                           {
                                               DatabaseReference receverref=FirebaseDatabase.getInstance().getReference("emails").child(idoftherecever);
                                               receverref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(true);
                                           }
                                           }
                                       });
                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError error) {

                                   }
                               });
                           }
                       }).setNegativeButton("No",null).show();
           }
       });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public  class userviewholder extends RecyclerView.ViewHolder
    {
       private TextView username,usergroup,useremail,userph,userbutton,user_type;
       private  CircleImageView  userprofileimage;

        public userviewholder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.user_name);
            usergroup=itemView.findViewById(R.id.user_bloodgroup);
            useremail=itemView.findViewById(R.id.user_email);
            userph=itemView.findViewById(R.id.user_phoneno);
            user_type=itemView.findViewById(R.id.user_type);
            userbutton=itemView.findViewById(R.id.user_email_button);
            userprofileimage= itemView.findViewById(R.id.profile_image);

        }
    }

}
