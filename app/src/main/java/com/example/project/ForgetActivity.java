package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {

    private String email;
    private Button field_reset;
    private EditText field_email;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget);

        mAuth = FirebaseAuth.getInstance();

        field_email = findViewById(R.id.email_reset);
        field_reset = findViewById(R.id.reset);

        field_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }
    private void resetPassword(){
        email = field_email.getText().toString().trim().toLowerCase();
        if(email.isEmpty()){
            field_email.setError("Email is Required");
            field_email.requestFocus();
        }else if (!isValidEmail(email)){
            field_email.setError("Invalid Email!");
            field_email.requestFocus();
        }
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetActivity.this,"Check your email to reset your password",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ForgetActivity.this,"Signin button pressed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}