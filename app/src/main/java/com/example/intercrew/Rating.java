package com.example.intercrew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Rating extends AppCompatActivity {
    Button button;
    RatingBar ratingStars;
    float myRating =0;
    String comments;
    DatabaseReference mDatabase1,mDatabase2;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);


        mDatabase1= FirebaseDatabase.getInstance().getReference().child("Clinics")
                .child(getIntent().getStringExtra("ClinicID"))
                .child("ratings");
        mDatabase2= FirebaseDatabase.getInstance().getReference().child("Clinics").child(getIntent().getStringExtra("ClinicID")).child("totalRating");
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        button = findViewById(R.id.button);
        ratingStars = findViewById(R.id.ratingBar);

        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                myRating = ratingBar.getRating();

            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean pass=true;
                        float total=0;
                        float totalChildren=dataSnapshot.getChildrenCount();

                        for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            if(postSnapshot.getKey().equals(mUser.getUid())){
                                pass=false;


                            }
                            total=total+postSnapshot.child("rating").getValue(float.class);


                        }
                        if(pass){
                            mDatabase1.child(mUser.getUid()).child("rating").push();
                            mDatabase1.child(mUser.getUid()).child("rating").setValue(myRating);
                            total=total+myRating;
                            totalChildren++;
                            float answer=total/totalChildren;
                            mDatabase2.setValue(answer);
                            Toast.makeText(Rating.this, "Thank you for your feedback",Toast.LENGTH_SHORT).show();
                            finish();



                        }
                        else {
                            Toast.makeText(Rating.this, "You have already rated the Clinic",Toast.LENGTH_SHORT).show();
                            finish();

                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }



                });

            }
        });
    }
}
