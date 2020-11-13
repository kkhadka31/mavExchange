package com.example.mavexchange;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    EditText email_id;
    EditText password_id;
    Button login_btn;
    Button register_btn;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fAuth = FirebaseAuth.getInstance();
        email_id = (EditText)findViewById(R.id.email_id);
        password_id = (EditText)findViewById(R.id.password_id);
        login_btn = (Button)findViewById(R.id.login_btn);
        register_btn = (Button)findViewById(R.id.register_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register= new Intent(loginActivity.this,RegisterActivity.class);
                startActivity(register);
                finish();
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