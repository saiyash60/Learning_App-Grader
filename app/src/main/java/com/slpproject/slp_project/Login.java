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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {

    EditText uEmail, uPassword;
    Button uLogin;
    TextView uTextView, forgottextlink;
    ProgressBar uProgressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uEmail = findViewById(R.id.username);
        uPassword =  findViewById(R.id.loginpassword);
        uLogin = findViewById(R.id.loginbutton);
        uTextView = findViewById(R.id.textView4);
        uProgressBar = findViewById(R.id.progressBar);
        forgottextlink = findViewById(R.id.forgotpassword);
        fAuth = FirebaseAuth.getInstance();

        uLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = uEmail.getText().toString().trim();
                String password = uPassword.getText().toString().trim();

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

                uProgressBar.setVisibility(View.VISIBLE);

                // Authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(Login.this, "Error !" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            uProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        uTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        forgottextlink.setOnClickListener(view -> {
            EditText resetMail = new EditText(view.getContext());
            AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
            passwordResetDialog.setTitle("Reset Password ?");
            passwordResetDialog.setMessage("Enter your email to receive reset link");
            passwordResetDialog.setView(resetMail);

            passwordResetDialog.setPositiveButton("Yes", (dialogInterface, i) -> {
                //extract the email and reset link

                String mail = resetMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(unused -> Toast.makeText(Login.this, "Reset Link sent to your email", Toast.LENGTH_SHORT).show()).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Error ! Reset Link is not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            });
            passwordResetDialog.setNegativeButton("No", (dialogInterface, i) -> {
                //close the dialog
            });

            passwordResetDialog.create().show();
        });


    }
}