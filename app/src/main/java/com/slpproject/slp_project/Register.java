package com.slpproject.slp_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Register extends AppCompatActivity {

    EditText uFullName, uEmail, uPhoneNumber, uPassword;
    Button uRegister;
    TextView uTextView, glogin;
    ProgressBar uProgressBar2;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        uFullName = findViewById(R.id.FullName);
        uEmail = findViewById(R.id.email);
        uPhoneNumber = findViewById(R.id.PhoneNumber);
        uPassword =  findViewById(R.id.Password);
        uRegister = findViewById(R.id.register);
        uTextView = findViewById(R.id.textView2);
        uProgressBar2 = findViewById(R.id.progressBar2);
        glogin = findViewById(R.id.textView2);

        //
//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }

        fAuth = FirebaseAuth.getInstance();


        uRegister.setOnClickListener(view -> {
            String email = uEmail.getText().toString().trim();
            String password = uPassword.getText().toString().trim();
            String name = uFullName.getText().toString().trim();

            if(TextUtils.isEmpty(email)) {
                uEmail.setError("Email is Required");
                return;
            }

            if(TextUtils.isEmpty(password)) {
                uPassword.setError("Password is Required");
                return;
            }

            if(password.length() < 6){
                uPassword.setError("Password must be >= 6 characters");
                return;
            }

            uProgressBar2.setVisibility(View.VISIBLE);

//             register the user to firebase

            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this, "Your account has been created", Toast.LENGTH_SHORT).show();

                    DbQuery.createUserData(email,name, new MyCompleteListener(){

                        @Override
                        public void onSuccess() {
                            uTextView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getApplicationContext(),Login.class));
                                }
                            });

                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(Register.this,"Something went wrong ! Please try again later ",Toast.LENGTH_SHORT).show();
                        }
                    });

                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                else{
                    Toast.makeText(Register.this, "Error !" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    uProgressBar2.setVisibility(View.GONE);
                }
            });
        });

        glogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });



    }
}