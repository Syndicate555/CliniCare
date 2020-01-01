package com.example.intercrew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class EmployeeHomeNotAssigned extends AppCompatActivity {
    Button addClinic;
    Button logout;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home_not_assigned);

        addClinic = findViewById(R.id.addClinic);
        logout = findViewById(R.id.logout);

        addClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeHomeNotAssigned.this, EmployeeCompleteProfile.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeHomeNotAssigned.this, MainActivity.class));
            }
        });
    }

    public void sigOutOnClick(View v) {
        mAuth.signOut();
        finish();
        ;
        startActivity(new Intent(this, MainActivity.class));
    }
}