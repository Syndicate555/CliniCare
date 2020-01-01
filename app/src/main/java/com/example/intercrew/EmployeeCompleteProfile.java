package com.example.intercrew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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


public class EmployeeCompleteProfile extends AppCompatActivity {
    Button completeProfileId;
    EditText addressId,companyId,descriptionId,insuranceId, numberId ;
    AutoCompleteTextView paymentId;
    RadioButton yesId,noId;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private FirebaseUser mUser;
    String[] payment = new String[] {"Paypal", "Cash", "Cheque", "Direct Deposit"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_complete_profile);

        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance().getReference();
        mUser=mAuth.getCurrentUser();


        getSupportActionBar().setTitle("Profile Information");
        addressId = findViewById(R.id.address);
        companyId = findViewById(R.id.company);
        descriptionId = findViewById(R.id.description);
        insuranceId = findViewById(R.id.insurance);
        numberId= findViewById(R.id.number);
        yesId = findViewById(R.id.radioYes);
        noId = findViewById(R.id.radioNo);
        completeProfileId = findViewById(R.id.completeProfile);
        paymentId = findViewById(R.id.payment);
        paymentId.setAdapter(new ArrayAdapter<>(EmployeeCompleteProfile.this, android.R.layout.simple_list_item_1,payment));

        completeProfileId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeProfile(v);

            }
        });

    }

    public void completeProfile(View v){
        String address = addressId.getText().toString();
        String company = companyId.getText().toString();
        String description = descriptionId.getText().toString();
        String insurance = insuranceId.getText().toString();
        String phone = numberId.getText().toString();

        String payment = paymentId.getText().toString();
        boolean pass = true;

        if(address.isEmpty() && company.isEmpty() && description.isEmpty() && insurance.isEmpty() && phone.isEmpty() && payment.isEmpty()){
            Toast.makeText(EmployeeCompleteProfile.this,"Fields are empty", Toast.LENGTH_SHORT).show();
            pass = false;
        }

        if (address.isEmpty()){
            addressId.setError("Please Enter Address ");
            addressId.requestFocus();
            pass = false;
        }
        if (company.isEmpty()) {
            companyId.setError("Please Enter Company Name ");
            companyId.requestFocus();
            pass = false;
        }
        if (description.isEmpty()) {
            descriptionId.setError("Please Enter Description ");
            descriptionId.requestFocus();
            pass = false;
        }
        if (insurance.isEmpty()) {
            insuranceId.setError("Please Enter Insurance ");
            insuranceId.requestFocus();
            pass = false;
        }
        if (phone.isEmpty()) {
            numberId.setError("Please Enter your Phone Number ");
            numberId.requestFocus();
            pass = false;
        }
        if (payment.isEmpty()) {
            paymentId.setError("Please Enter your Payment Method ");
            paymentId.requestFocus();
            pass = false;
        }


        if (pass){
            final Clinic nClinic = new Clinic(address,Integer.parseInt(phone),company,insurance,payment,null,null);
            //first add clinic to "Clinic" branch
            final String key=mDatabase.child("Clinics").push().getKey();

            nClinic.setClinicID(key);
            //add seven default dayHours for clinic
            mDatabase.child("Clinics").child(key).setValue(nClinic);
            String id;
            for(int i=0;i<7;i++){
                id= mDatabase.child("Clinics").child(key).child("workinghours").push().getKey();
                mDatabase.child("Clinics").child(key).child("workinghours").child(id).setValue(new DayHours(id));
            }
            mDatabase.child("Clinics").child(key).child("totalRating").push();
            mDatabase.child("Clinics").child(key).child("totalRating").setValue(nClinic.getRating());
            mDatabase.child("Clinics").child(key).child("waitTime").push();
            mDatabase.child("Clinics").child(key).child("totalRating").setValue(nClinic.getWaitTime());



            Toast.makeText(getApplicationContext(),"Service Added",Toast.LENGTH_LONG).show();


            //add Clinic id to the Employee
            mDatabase.child("Users")
                    .child(mUser.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Employee emp = dataSnapshot.child("user").getValue(Employee.class);
                            emp.setClinicID(key);

                            mDatabase.child("Users").child(mUser.getUid()).child("user").setValue(emp);


                            startActivity(new Intent(EmployeeCompleteProfile.this, EmployeeHomePageAssigned.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


        }



    }
}
