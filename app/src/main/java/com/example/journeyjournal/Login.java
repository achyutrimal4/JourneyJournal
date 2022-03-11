package com.example.journeyjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private TextView register;
    TextInputEditText email, password;
    MaterialButton login;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getSupportActionBar().hide();

        register = findViewById(R.id.tvRegister);
        email = findViewById(R.id.loginEditEmail);
        password = findViewById(R.id.loginEditPassword);
        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegister = new Intent(getApplicationContext(), Register.class);
                startActivity(toRegister);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                String email_id = email.getText().toString();
                String pwd = password.getText().toString();

                boolean login_validation=validate_login(email_id, pwd);

                if (login_validation) {
                    signIn();
                }else{

                }
            }

        });
    }

    public void signIn() {
        String mailID = email.getText().toString();
        String pwd = password.getText().toString();

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(mailID, pwd)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(Login.this, Dashboard.class);
                            intent.putExtra("email", mAuth.getCurrentUser().getEmail());
                            intent.putExtra("uid", mAuth.getCurrentUser().getUid());
                            startActivity(intent);
                            finish();

                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            email.setText("");
                            password.setText("");
                            Toast.makeText(getApplicationContext(), "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public boolean validate_login(String email_id, String pword) {
        if ((email_id.length() == 0)) {
            progressBar.setVisibility(View.GONE);
            email.requestFocus();
            email.setError("");
            return false;
        } else if (pword.length() == 0) {
            progressBar.setVisibility(View.GONE);
            password.requestFocus();
            password.setError("");
            return false;
        }
        return true;
    }
}