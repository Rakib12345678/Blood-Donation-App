package com.example.blood_bank.email_customar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.blood_bank.R;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class email_option extends AsyncTask<Void,Void,Void>
{
    private Context context;
    private Session session;
    private String email,subject,message;
    public email_option(Context context, String email, String subject, String message) {
        this.context = context;

        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    ProgressDialog progressDialog;

    @Override
    protected void onPreExecute() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("please waith the mail is being send");
        progressDialog.setTitle("sending email to donar");
        progressDialog.show();
        super.onPreExecute();
    }

    /*
        @Override
        protected void onPreExecute(Void unused) {
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("please waith the mail is being send");
            progressDialog.setTitle("sending email to donar");
            progressDialog.show();
            super.onPreExecute();
        }
    */
    @Override
    protected Void doInBackground(Void... voids) {
        Properties properties=new Properties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port",465);
        session=Session.getDefaultInstance(properties, new javax.mail.Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(util.email,util.password);
            }
        }
        );
        MimeMessage mimeMessage=new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(util.email));
            mimeMessage.addRecipients(Message.RecipientType.TO,String.valueOf(new InternetAddress(email)));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
        }catch (MessagingException e)
        {
            e.printStackTrace();
        }



        return null;

    }

    @Override
    protected void onPostExecute(Void unused) {
        progressDialog.dismiss();
        alerdialogfun();
        super.onPostExecute(unused);
    }
    /*
    @Override
    protected void onPostExecute()

    {
        progressDialog.dismiss();
        alerdialogfun();
        super.onPostExecute();
    }

     */

    private void alerdialogfun() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
        View view= LayoutInflater.from(context).inflate(R.layout.output,null);
        dialog.setView(view);
        final AlertDialog alertDialog=dialog.create();
        alertDialog.setCancelable(false);

        Button closebutton=view.findViewById(R.id.close);
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
