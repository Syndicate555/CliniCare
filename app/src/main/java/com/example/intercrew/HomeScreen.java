package com.example.intercrew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// Homescreen for the user after they Sign in
public class HomeScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    //declaring String objects
    private String userID;
    private String role;
    private String firstName;


    //text view declaration
    private TextView firstNameTextView;
    private TextView userTypeTextView;


    //Button declaration
    Button logoutButton,searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //initializing objects
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mDatabase=FirebaseDatabase.getInstance().getReference();

        //initializing Text Views and Button
        firstNameTextView=(TextView)findViewById(R.id.firstTextView);
        userTypeTextView=(TextView)findViewById(R.id.userTypeView);
        logoutButton=findViewById(R.id.Logout_Button);
        searchButton=findViewById(R.id.searchClinicID);



        role="";




        if(mUser==null){
            finish();
            startActivity(new Intent(this,MainActivity.class));//go to login Screen
        }
        else{
            mDatabase.child("Users")
                    .child(mUser.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                                    role = dataSnapshot.child("role").getValue(String.class);


                                    Patient patient = dataSnapshot.child("user").getValue(Patient.class);
                                    firstName = patient.getFirstName();



                            firstNameTextView.setText("Welcome "+firstName);
                            userTypeTextView.setText("You are logged in as "+role);




                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }


        //Button Listener
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sigOutOnClick(v);
            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this,SearchClinics.class));

            }
        });




    }

    public void sigOutOnClick(View v){
        mAuth.signOut();
        finish();;
        startActivity(new Intent(this,MainActivity.class));


    }




}