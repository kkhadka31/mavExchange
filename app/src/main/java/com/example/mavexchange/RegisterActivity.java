package com.example.mavexchange;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText full_name;
    EditText password_id;
    EditText email_id;
    EditText confirm_password_id;
    Button login_btn;
    Button register_btn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    String Email_Pattern = "[a-zA-Z0-9]+\\.+[a-zA-Z0-9]+@[mavs]+\\.+[uta]+\\.+[edu]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        full_name = (EditText) findViewById(R.id.full_name);
        email_id = (EditText) findViewById(R.id.email_id);

        password_id = (EditText) findViewById(R.id.password_id);
        confirm_password_id = (EditText) findViewById(R.id.confirm_password);
        register_btn = (Button) findViewById(R.id.register_btn);
        login_btn = (Button) findViewById(R.id.login_btn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }



        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }


        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
                finish();

            }
        });

    }
    private void Register(){
        final String name = full_name.getText().toString().trim();
        final String email = email_id.getText().toString().trim();
        final String password = password_id.getText().toString().trim();
        String confirm_password = confirm_password_id.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            full_name.setError("Name is Required.");
            return;
        }
        if(TextUtils.isEmpty(email)){
            email_id.setError("email is Required.");
            return;
        }

        if(TextUtils.isEmpty(password)){
            password_id.setError("Password is Required.");
            return;
        }
        if(TextUtils.isEmpty(confirm_password)){
            confirm_password_id.setError("Confirm password");
            return;
        }
        if(password.length()<6){
            password_id.setError("Password must be 6 or more characters");
            return;
        }
        if(!password.equals(confirm_password)){
            confirm_password_id.setError("Password not matched");
            return;
        }
        if(!email.matches(Email_Pattern)){
            // if(!isValidEmail(email)){
            email_id.setError("invalid Email");
            return;
        }


        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Successfully Registered. Please check your email for verification", Toast.LENGTH_SHORT).show();
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("full_name", name);
                                user.put("email_id", email);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: Profile has been created for "+ userID);
                                    }
                                });
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "Sign Up failed !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            startActivity(new Intent(getApplicationContext(), loginActivity.class));
                            finish();

                        }
                    });
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Sign Up failed !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}





