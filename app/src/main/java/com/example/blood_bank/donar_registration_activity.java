package com.example.blood_bank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class donar_registration_activity extends AppCompatActivity {

    private TextView donar_backbutton;
    private Uri resulturi;
    private FirebaseAuth rauth;
    private DatabaseReference userref;
    private ProgressDialog don_loader;
    private CircleImageView profile_image;
    private EditText donar_register_name,donar_register_id,donar_register_phone,donar_register_email,donar_register_password;
    private Button donar_register_button;
    private Spinner donar_bloodgroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_registration);

        donar_backbutton=findViewById(R.id.backbutton);
        donar_register_name=findViewById(R.id.register_name);
        donar_register_id=findViewById(R.id.register_id);
        donar_register_phone=findViewById(R.id.register_phone);
        donar_register_email=findViewById(R.id.register_email);
        profile_image=findViewById(R.id.profile_image);
        donar_register_password=findViewById(R.id.register_password);
        donar_register_button=findViewById(R.id.register_button);
        donar_bloodgroup =findViewById(R.id.blood_group);
        don_loader=new ProgressDialog(this);
        rauth=FirebaseAuth.getInstance();

        donar_backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(donar_registration_activity.this,loginactivity.class));
            }
        });

        donar_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                final String don_name=donar_register_name.getText().toString().trim();
                final String don_id=donar_register_id.getText().toString().trim();
                final String don_ph=donar_register_phone.getText().toString().trim();
                final String don_bloodgroup= donar_bloodgroup.getSelectedItem().toString();
                final String don_email= donar_register_email.getText().toString().trim();
                final String don_password= donar_register_password.getText().toString().trim();
                if(TextUtils.isEmpty(don_email))
                {
                    donar_register_email.setError("enter email");
                    return;
                }
                if(TextUtils.isEmpty(don_name))
                {
                    donar_register_name.setError("enter name");
                    return;
                }

                if(TextUtils.isEmpty(don_id))
                {
                    donar_register_id.setError("enter id");
                    return;
                }

                if(TextUtils.isEmpty(don_ph))
                {
                    donar_register_phone.setError("enter phone no");
                    return;
                }

                if(TextUtils.isEmpty(don_password))
                {
                    donar_register_password.setError("enter password");
                    return;
                }

                if(don_bloodgroup.equals("select blood group"))
                {
                    Toast.makeText(donar_registration_activity.this,"select your blood group",Toast.LENGTH_SHORT).show();
                    return;
                }

                else
                {
               /* don_loader.setMessage("Registering you");
                don_loader.setCancelable(false);
                don_loader.setCanceledOnTouchOutside(false);
                don_loader.show();

                */
                    rauth.createUserWithEmailAndPassword(don_email,don_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(donar_registration_activity.this,"sign up dont successfully due to"+task.getException().toString(),Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                String currentUserId=rauth.getCurrentUser().getUid();
                                userref= FirebaseDatabase.getInstance().getReference().child("user").child(currentUserId);

                                HashMap userinfo=new HashMap();
                                userinfo.put("id",currentUserId);
                                userinfo.put("don_name",don_name);
                                userinfo.put("don_email",don_email);
                                userinfo.put("don_id",don_id);
                                userinfo.put("don_ph",don_ph);
                                userinfo.put("don_blood",don_bloodgroup);
                                userinfo.put("type","donar");
                                userinfo.put("search","don_cus"+don_bloodgroup);
                                userref.updateChildren(userinfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(donar_registration_activity.this,"data is successfully inserted",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(donar_registration_activity.this,"error"+task.getException().toString(),Toast.LENGTH_SHORT).show();

                                        }
                                        finish();

                                    }
                                });

                       if(resulturi!=null)
                       {
                           final StorageReference filepath= FirebaseStorage.getInstance().getReference().child("profile image").
                                   child(currentUserId);
                           Bitmap bitmap =null;
                           try {
                               bitmap= MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resulturi);
                           }catch (IOException e)
                           {
                               e.printStackTrace();

                           }
                           ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                           bitmap.compress(Bitmap.CompressFormat.JPEG, 20,byteArrayOutputStream);
                           byte[] data=byteArrayOutputStream.toByteArray();
                           UploadTask uploadTask=filepath.putBytes(data);


                           uploadTask.addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(donar_registration_activity.this,"image upload failed",Toast.LENGTH_SHORT).show();

                               }
                           }) ;
                           uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                               @Override
                               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                   if(taskSnapshot.getMetadata()!=null && taskSnapshot.getMetadata().getReference()!=null)
                                   {
                                       Task<Uri> result=taskSnapshot.getStorage().getDownloadUrl();
                                       result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                           @Override
                                           public void onSuccess(Uri uri) {
                                              String url=uri.toString();
                                              Map imagemap=new HashMap();
                                              imagemap.put("profilepictureurl",url);
                                              userref.updateChildren(imagemap).addOnCompleteListener(new OnCompleteListener() {
                                                  @Override
                                                  public void onComplete(@NonNull Task task)
                                                  {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(donar_registration_activity.this,"picture url is successfully added",Toast.LENGTH_SHORT).show();

                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(donar_registration_activity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();

                                                    }
                                                  }
                                              });
                                           finish();
                                           }
                                       });
                                   }
                               }
                           });
                           startActivity(new Intent(donar_registration_activity.this,MainActivity.class));




                       }


                            }
                        }
                    });
                }

            }
        });
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null)
        {
            resulturi=data.getData();
            profile_image.setImageURI(resulturi);
        }
    }


    }
