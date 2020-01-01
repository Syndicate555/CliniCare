package com.example.intercrew;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;

public class CheckBooking extends AppCompatActivity {
    Button book, cancel;
    TextView waitingTime;
    private DatabaseReference mDatabase,mDatabase2,mDatabaseProxy;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_booking);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Clinics").child(getIntent().getStringExtra("ClinicID")).child("appointments");
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Clinics").child(getIntent().getStringExtra("ClinicID")).child("waitTime");

        book=findViewById(R.id.BookID);
        cancel = findViewById(R.id.cancelID);
        waitingTime=findViewById(R.id.waitingTimeID);

        mAuth= FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();
        //show the waiting time
        mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                waitingTime.setText("Waiting Time: "+dataSnapshot.getValue(Integer.class).toString()+" mins");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if user has an existing appointment
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            addAppointment(mUser.getUid());
                            Intent intent = new Intent(CheckBooking.this,BookingClinic.class);
                            intent.putExtra("ClinicID",getIntent().getStringExtra("ClinicID"));
                            startActivity(intent);
                            finish();




                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //make a onclick listener on cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckBooking.this,SearchClinics.class));
                finish();

            }
        });
    }


    private void addAppointment(final String id) {

        //expectedTime calculation
        mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                //Date done
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                String day = formatter.format(date);


                //calculating expectedTime
                LocalTime time1 = LocalTime.now().plus(Duration.ofMinutes((long)dataSnapshot.getValue(Integer.class)));;

                //adding it into a string
                String expectedTime=time1.getHour()+":"+time1.getMinute();

                //Adding the appointment to the database
                Appointment appointment = new Appointment(id, day, expectedTime);
                mDatabase.child(mUser.getUid()).setValue(appointment);






            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //updating the total clinic waiting time
                mDatabase2.setValue((15*dataSnapshot.getChildrenCount())+15);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }
}
