package com.example.intercrew.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intercrew.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminHomeScreen extends AppCompatActivity {
    Button modifyServices, modifyUsers, signOut;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);

        mAuth= FirebaseAuth.getInstance();

        modifyServices=findViewById(R.id.modifyServicesId);
        modifyUsers= findViewById(R.id.usersId);
        signOut=findViewById(R.id.signOut);


        modifyServices.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeScreen.this, ModifyServices.class ));
            }
        });

        modifyUsers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeScreen.this, ModifyUsers.class ));
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutOnClick(v);
            }
        });
    }

    public void signOutOnClick(View v){
        mAuth.signOut();
        finish();;
        startActivity(new Intent(this,Signin_Admin.class));
    }
}
