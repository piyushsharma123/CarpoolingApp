package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class signin_form extends AppCompatActivity {

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String name,email,tid,gender,designation, address, password,retype,phone,role;
    private String verificationCode;
    FirebaseAuth mAuth;
    public static final String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_form);
        mAuth = FirebaseAuth.getInstance();
//        StartFirebaseLogin();

        final EditText field_name = (EditText) findViewById(R.id.et_name);
        final EditText field_mail = (EditText) findViewById(R.id.et_email);
        final EditText field_teacher_id = (EditText) findViewById(R.id.et_teacherid);
        final EditText field_gender = (EditText) findViewById(R.id.et_gender);
        final EditText field_designation = (EditText) findViewById(R.id.et_designation);
        final EditText field_address = (EditText) findViewById(R.id.et_address);
        final EditText field_password = (EditText) findViewById(R.id.et_password);
        final EditText field_retype = (EditText) findViewById(R.id.et_retype);
        final EditText field_phone_no = findViewById(R.id.et_phone);
        final Spinner role_spinner = findViewById(R.id.register_role_spinner);
        List<String> roles = new ArrayList<>();
        roles.add("Choose Roles");
        roles.add("Driver");
        roles.add("Passenger");
        roles.add("Both");

        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role_spinner.setAdapter(roleAdapter);

        role_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String your_role = role_spinner.getSelectedItem().toString();
                if(your_role.equals("Driver")){
                    role = "1";
                }else if(your_role.equals("Passenger")){
                    role = "2";
                }else if(your_role.equals("Both")){
                    role = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btnSignIn = findViewById(R.id.signin);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = field_name.getText().toString().trim().toLowerCase();
                email= field_mail.getText().toString().trim().toLowerCase();
                tid= field_teacher_id.getText().toString().trim().toLowerCase();
                gender= field_gender.getText().toString().trim().toLowerCase();
                designation= field_designation.getText().toString().trim().toLowerCase();
                address= field_address.getText().toString().trim().toLowerCase();
                password= field_password.getText().toString().trim().toLowerCase();
                retype= field_retype.getText().toString().trim().toLowerCase();
                phone= field_phone_no.getText().toString().trim().toLowerCase();

                if(TextUtils.isEmpty(name)) {
                    Log.d(TAG,"name");
                    field_name.setError("Name Required");
                    field_name.requestFocus();
                }else if(!isValidEmail(email)) {
                    Log.d(TAG,"email");
                    field_mail.setError("Email Id Required");
                    field_mail.requestFocus();
                }else if(TextUtils.isEmpty(tid)) {
                    Log.d(TAG,"tid");
                    field_teacher_id.setError("Teacher Id Required");
                    field_teacher_id.requestFocus();
                }else if(TextUtils.isEmpty(gender)) {
                    Log.d(TAG,"gender");
                    field_gender.setError("Gender Required");
                    field_gender.requestFocus();
                }else if(TextUtils.isEmpty(designation)) {
                    Log.d(TAG,"designation");
                    field_designation.setError("Designation Required");
                    field_designation.requestFocus();
                }else if(TextUtils.isEmpty(address)) {
                    Log.d(TAG,"address");
                    field_address.setError("Address Required");
                    field_address.requestFocus();
                }else if(TextUtils.isEmpty(phone)) {
                    Log.d(TAG,"phone");
                    field_phone_no.setError("Phone No. Required");
                    field_phone_no.requestFocus();
                }else if(TextUtils.isEmpty(password)) {
                    Log.d(TAG,"password");
                    field_password.setError("Password Required");
                    field_password.requestFocus();
                }else if(field_password.length()<8) {
                    Log.d(TAG,"retype");
                    field_password.setError("Min password length should be 8 characters!");
                    field_password.requestFocus();
                }else if (!(password.equals(retype))){
                    Log.d(TAG,"retype");
                    field_retype.setError("Password Missmatch");
                    field_retype.requestFocus();;
                }else {
                    Log.d(TAG,"registeruser");
                    registeruser();
                }
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            //already login handle
        }

    }

    private void registeruser() {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(name,email,gender,tid,designation,address,phone,role);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        startActivity(new Intent(signin_form.this,login.class));
                                    }else{
                                        Toast.makeText(signin_form.this,"Registration Failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(signin_form.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


//    private void StartFirebaseLogin() {
//
//        auth = FirebaseAuth.getInstance();
//        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                Toast.makeText(signin_form.this,"verification completed",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                Toast.makeText(signin_form.this,"verification failed",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                super.onCodeSent(s, forceResendingToken);
//                verificationCode = s;
//                Toast.makeText(signin_form.this,"Code sent",Toast.LENGTH_SHORT).show();
//            }
//        };
//    }
//    private void SigninWithPhone(PhoneAuthCredential credential) {
//        auth.signInWithCredential(credential)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            startActivity(new Intent(signin_form.this,mainbody.class));
//                            finish();
//                        } else {
//                            Toast.makeText(signin_form.this,"Incorrect OTP",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
}
