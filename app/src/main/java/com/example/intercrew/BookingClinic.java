package com.example.intercrew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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




public class BookingClinic extends AppCompatActivity {

    Button cancel,goBack;
    private DatabaseReference mDatabase,mDatabase2,mDatabaseProxy;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_clinic);

        final TextView dateView=findViewById(R.id.dateViewID);
        final TextView timeView=findViewById(R.id.expTimeID);

        cancel = findViewById(R.id.CancelAppID);
        goBack=findViewById(R.id.returnID);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Clinics").child(getIntent().getStringExtra("ClinicID")).child("appointments");
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Clinics").child(getIntent().getStringExtra("ClinicID")).child("waitTime");
        mDatabaseProxy = FirebaseDatabase.getInstance().getReference().child("WaitingTime").child(getIntent().getStringExtra("ClinicID")).child("waitingTime");


        mAuth= FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();

        mDatabase.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Appointment appointment=dataSnapshot.getValue(Appointment.class);
                if(appointment!=null) {
                    dateView.setText(appointment.getDay());
                    timeView.setText(appointment.getTime());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //make a onclick listener on cancel appointment button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAppointment(mUser.getUid());
                startActivity(new Intent(BookingClinic.this,SearchClinics.class));

            }
        });


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookingClinic.this,SearchClinics.class));

            }
        });




    }








    private void deleteAppointment(String id){
        //Removing the appointment

        mDatabase.child(mUser.getUid()).removeValue();


        //updating the total clinic waiting time
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mDatabase2.setValue(15*dataSnapshot.getChildrenCount());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Toast.makeText(getApplicationContext(),"Appointment cancelled",Toast.LENGTH_LONG).show();

    }







}
