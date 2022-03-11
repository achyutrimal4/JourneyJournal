package com.example.journeyjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    TextInputEditText registerEmail, registerUsername, registerPassword, rePassword, registerPhone;
    MaterialButton btnRegister;
    TextView loginLink;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        registerEmail = findViewById(R.id.registerEditEmail);
        registerUsername = findViewById(R.id.registerEditUsername);
        registerPassword = findViewById(R.id.registerEditPassword);
        rePassword = findViewById(R.id.registerEditPassword2);
        registerPhone = findViewById(R.id.registerEditNumber);
        btnRegister = findViewById(R.id.loginButton);

        btnRegister.setOnClickListener(view -> {

            String email = registerEmail.getText().toString();
            String username = registerUsername.getText().toString();
            String password = registerPassword.getText().toString();
            String re_password = rePassword.getText().toString();
            String phoneNumber = registerPhone.getText().toString();

            boolean validation_check = validation(email, username, password, re_password, phoneNumber);

            if (validation_check) {
                signupAuth();
            } else {
            }
        });

    }

    //    Authenticate user from firebase
    public void signupAuth() {
        String email, username, password, re_password, phoneNumber;
        email = registerEmail.getText().toString();
        username = registerUsername.getText().toString();
        password = registerPassword.getText().toString();
        re_password = rePassword.getText().toString();
        phoneNumber = registerPhone.getText().toString();

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Sign up successful. Login to continue", Toast.LENGTH_SHORT).show();
                            Intent toLogin = new Intent(Register.this, Login.class);
                            startActivity(toLogin);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register.this, "Email id already used. Try another id.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    //validate information
    protected boolean validation(String email, String username, String password, String re_password, String phoneNumber) {

        String emailPattern, usernamePattern, phonePattern;

        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        usernamePattern = "[a-zA-Z0-9]+";
        phonePattern = "[0-9]+";

        if ((email.length() == 0) || !email.matches(emailPattern)) {
            registerEmail.requestFocus();
            registerEmail.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
            return false;
        } else if ((username.length() <= 4)) {
            registerUsername.requestFocus();
            registerUsername.setHintTextColor(ContextCompat.getColor(this, R.color.red));
            return false;
        } else if (!username.matches(usernamePattern)) {
            registerUsername.requestFocus();
            registerUsername.setHintTextColor(ContextCompat.getColor(this, R.color.red));
            return false;
        } else if (password.length() < 8) {
            registerPassword.requestFocus();
            registerPassword.setHintTextColor(ContextCompat.getColor(this, R.color.red));
            return false;
        } else if (re_password.length() < 8) {
            rePassword.requestFocus();
            rePassword.setHintTextColor(ContextCompat.getColor(this, R.color.red));
            return false;
        } else if (!password.equals(re_password)) {
            rePassword.requestFocus();
            rePassword.setHintTextColor(ContextCompat.getColor(this, R.color.red));
            return false;
        } else if (phoneNumber.length() < 10 || !phoneNumber.matches(phonePattern)) {
            registerPhone.requestFocus();
            registerPhone.setHintTextColor(ContextCompat.getColor(this, R.color.red));
            return false;
        } else {
            return true;
        }
    }
}