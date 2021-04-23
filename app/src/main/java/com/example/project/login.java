package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

//    EditText field_username,field_password;
    private String username,password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();

        final EditText field_username = findViewById(R.id.et_user);
        final EditText field_password = findViewById(R.id.et_pass);
        TextView field_forget = findViewById(R.id.forget);
        TextView field_signup = findViewById(R.id.signup);
        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuser(field_username,field_password);
            }
        });
        field_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, ForgetActivity.class));
            }
        });
        field_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, signin_form.class));
            }
        });

    }
    private void loginuser(EditText field_username, EditText field_password) {
        username = field_username.getText().toString().trim().toLowerCase();
        password = field_password.getText().toString().trim().toLowerCase();
        if(username.isEmpty()){
            field_username.setError("Email is Required!");
            field_username.requestFocus();
        }else if(!isValidEmail(username)){
            field_username.setError("Invalid Email!");
            field_username.requestFocus();
        }else if(password.isEmpty()){
            field_username.setError("Password is required!");
            field_username.requestFocus();
        }else if(password.length()<8){
            field_password.setError("Min password length should be 8 characters!");
            field_password.requestFocus();
        }
        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //user activity
                    startActivity(new Intent(login.this, role_home.class));
                }else{
                    Toast.makeText(login.this,"Login Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.login:
//                loginuser();
//                break;
//
//            case R.id.forget:
//                startActivity(new Intent(login.this, ForgetActivity.class));
//                break;
//
//            case R.id.signup:
//                startActivity(new Intent(login.this, signin_form.class));
//                break;
//
//        }
//    }
}
