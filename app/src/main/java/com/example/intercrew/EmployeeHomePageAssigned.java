package com.example.intercrew;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intercrew.Admin.Service;
import com.example.intercrew.Admin.ServiceList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeHomePageAssigned extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private DatabaseReference clinicReference;
    private TextView clinicName;
    Button logoutButton;
    Button workingHoursButton;
    Button addServicesButton;
    private ArrayList<Service> services=new ArrayList<>();
    ListView servicesView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home_page_assigned);


        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");

        //initializing database references
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        clinicName=(TextView)findViewById(R.id.clinicNameId);
        logoutButton=findViewById(R.id.Logout_Button);
        workingHoursButton=findViewById(R.id.workingHoursBtn);

        addServicesButton=findViewById(R.id.addServicesID);
        servicesView=findViewById(R.id.servicesListId);

        clinicReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("user").child("clinicID");

        //getting storing clinic ref number from the employee
        clinicReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Clinics").child(dataSnapshot.getValue(String.class));
                mDatabase.child("services").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        services.clear();

                        for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                            Service service=postSnapshot.getValue(Service.class);
                            services.add(service);
                        }
                        ServiceList serviceAdapter=new ServiceList(EmployeeHomePageAssigned.this,services);
                        servicesView.setAdapter(serviceAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //getting name of clinic

                    mDatabase
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    String clinicname = dataSnapshot.child("clinicName").getValue(String.class);
                                    clinicName.setText(clinicname);

                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        //Button Listener
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutOnClick(v);
            }
        });

        addServicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeHomePageAssigned.this,AddServicesClinic.class));
            }
        });




        workingHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeHomePageAssigned.this,EditWorkingHours.class));
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        clinicReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Clinics").child(dataSnapshot.getValue(String.class));
                mDatabase.child("services").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        services.clear();
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            Service service=postSnapshot.getValue(Service.class);
                            services.add(service);
                        }
                        ServiceList productsAdapter=new ServiceList(EmployeeHomePageAssigned.this,services);
                        servicesView.setAdapter(productsAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void signOutOnClick(View v){
        mAuth.signOut();
        finish();;
        startActivity(new Intent(this,MainActivity.class));


    }





}
