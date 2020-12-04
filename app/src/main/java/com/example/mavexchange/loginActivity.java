package com.example.mavexchange;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    EditText email_id;
    EditText password_id;
    Button login_btn;
    Button register_btn;
    FirebaseAuth fAuth;
    Button reset_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fAuth = FirebaseAuth.getInstance();
        email_id = (EditText)findViewById(R.id.email_id);
        password_id = (EditText)findViewById(R.id.password_id);
        login_btn = (Button)findViewById(R.id.login_btn);
        register_btn = (Button)findViewById(R.id.register_btn);
        reset_btn = (Button)findViewById(R.id.reset_btn);

        reset_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showRecoverPasswordDialog();

            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(loginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(("Reset Password"));
        LinearLayout linearLayout = new LinearLayout((this));
        EditText emailEt = new EditText(this);
        emailEt.setHint(("Email"));
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailEt.setMinEms(16);
        linearLayout.addView(emailEt);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);



        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = emailEt.getText().toString().trim();
                beginRecovery(email);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();



    }

    private void beginRecovery(String email) {
        fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(loginActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(loginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(loginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void Login(){

        String email = email_id.getText().toString().trim();
        String password = password_id.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            email_id.setError("email is Required.");
            return;
        }

        if(TextUtils.isEmpty(password)){
            password_id.setError("Password is Required.");
            return;
        }

       fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    if(fAuth.getCurrentUser().isEmailVerified()){
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                    }
                    else{
                        Toast.makeText(loginActivity.this, "Please check your email for verification", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(loginActivity.this, "Sign Up failed !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

}