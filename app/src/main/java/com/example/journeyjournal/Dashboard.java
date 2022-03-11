package com.example.journeyjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    TextView email, id;
    MaterialButton logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        email=findViewById(R.id.userEmail);
        id=findViewById(R.id.userID);
        logout=findViewById(R.id.btnLogout);

        email.setText(getIntent().getStringExtra("email").toString());
        email.setText("User ID"+getIntent().getStringExtra("uid").toString());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Dashboard.this,Login.class));
                finish();
            }
        });

    }

}