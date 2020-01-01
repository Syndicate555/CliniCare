package com.example.intercrew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intercrew.Admin.Service;
import com.example.intercrew.Admin.ServiceList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchClinics extends AppCompatActivity {
    EditText postalView,endTime,startTime,nameView;
    Button searchButton, back;
    ListView clinicsView;
    DatabaseReference mDatabase,mDatabase2;
    Spinner spinner,spinnerDays,start,end;
    RadioGroup search;
    RadioButton selected;


     List<Clinic> clinics=new ArrayList<>();
    private ArrayList<Service> services=new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_clinics);
        //new
        mDatabase2= FirebaseDatabase.getInstance().getReference().child("services");
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                spinner=findViewById(R.id.spinnerID);
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Service service=postSnapshot.getValue(Service.class);
                    services.add(service);
                    ServiceList serviceAdapter=new ServiceList(SearchClinics.this,services);
                    serviceAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinner.setAdapter(serviceAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        search=findViewById(R.id.radioGroup);
        //startTime=findViewById(R.id.startTimeID);
        //endTime=findViewById(R.id.endTimeID);
        spinnerDays=findViewById(R.id.spinner3);
        start=findViewById(R.id.spinner4);
        end=findViewById(R.id.spinner5);
        nameView=findViewById(R.id.clinicNameID);


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Days,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.Hours,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(this,R.array.Hours,android.R.layout.simple_spinner_item);
        spinnerDays.setAdapter(adapter);
        start.setAdapter(adapter2);
        end.setAdapter(adapter3);


        mDatabase= FirebaseDatabase.getInstance().getReference().child("Clinics");
        postalView=findViewById(R.id.postalCodeText);
        searchButton=findViewById(R.id.searchButtonID);
        back=findViewById(R.id.back);
        clinicsView=findViewById(R.id.clinicsViewID);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id=search.getCheckedRadioButtonId();
                selected=findViewById(id);

                if(selected.getText().equals("Search by Name")){

                    if(nameView.getText().toString().isEmpty()){
                        nameView.setError("Please Enter Postal Code ");
                        nameView.requestFocus();

                    }

                else{
                        final String clinicName=nameView.getText().toString();
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                clinics.clear();

                                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {


                                    if (postSnapshot.child("clinicName").getValue(String.class).equals(clinicName)) {
                                        List<Service> services = new ArrayList<>();
                                        for (DataSnapshot service : postSnapshot.child("services").getChildren()) {
                                            services.add(service.getValue(Service.class));
                                        }

                                        Clinic clinic = new Clinic(postSnapshot.child("address").getValue(String.class)
                                                ,postSnapshot.child("phone").getValue(Integer.class)
                                                ,postSnapshot.child("clinicName").getValue(String.class)
                                                ,postSnapshot.child("insuranceType").getValue(String.class)
                                                ,postSnapshot.child("paymentMethod").getValue(String.class)
                                                ,services);



                                        if(postSnapshot.child("totalRating").exists()) {
                                            clinic.setRating(postSnapshot.child("totalRating").getValue(float.class));
                                        }
                                        clinic.setClinicID(postSnapshot.child("clinicID").getValue(String.class));
                                        clinic.setWaitTime(postSnapshot.child("waitTime").getValue(Integer.class));

                                        clinics.add(clinic);
                                    }
                                }
                                ClinicList serviceAdapter=new ClinicList(SearchClinics.this,clinics);
                                clinicsView.setAdapter(serviceAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }


                }
                else if(selected.getText().equals("Search by Postal Code")){

                    if(postalView.getText().toString().isEmpty()){
                        postalView.setError("Please Enter Postal Code ");
                        postalView.requestFocus();

                    }

                else{
                        final String postal=postalView.getText().toString();
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                clinics.clear();

                                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {


                                    if (postSnapshot.child("address").getValue(String.class).equals(postal)) {
                                        List<Service> services = new ArrayList<>();
                                        for (DataSnapshot service : postSnapshot.child("services").getChildren()) {
                                            services.add(service.getValue(Service.class));
                                        }

                                        Clinic clinic = new Clinic(postSnapshot.child("address").getValue(String.class)
                                                ,postSnapshot.child("phone").getValue(Integer.class)
                                                ,postSnapshot.child("clinicName").getValue(String.class)
                                                ,postSnapshot.child("insuranceType").getValue(String.class)
                                                ,postSnapshot.child("paymentMethod").getValue(String.class)
                                                ,services);


                                        if(postSnapshot.child("totalRating").exists()) {
                                            clinic.setRating(postSnapshot.child("totalRating").getValue(float.class));
                                        }
                                        clinic.setClinicID(postSnapshot.child("clinicID").getValue(String.class));
                                        clinic.setWaitTime(postSnapshot.child("waitTime").getValue(Integer.class));

                                        clinics.add(clinic);
                                    }
                                }
                                ClinicList serviceAdapter=new ClinicList(SearchClinics.this,clinics);
                                clinicsView.setAdapter(serviceAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }


                }
                else if(selected.getText().equals("Search by Service")){

                    final String service=spinner.getSelectedItem().toString();

                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            clinics.clear();

                            for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                for(DataSnapshot postSnapshot2:postSnapshot.child("services").getChildren()){
                                    if (postSnapshot2.child("serviceName").getValue(String.class).equals(service)) {
                                        List<Service> services = new ArrayList<>();
                                        for (DataSnapshot service : postSnapshot.child("services").getChildren()) {
                                            services.add(service.getValue(Service.class));
                                        }

                                        Clinic clinic = new Clinic(postSnapshot.child("address").getValue(String.class)
                                                ,postSnapshot.child("phone").getValue(Integer.class)
                                                ,postSnapshot.child("clinicName").getValue(String.class)
                                                ,postSnapshot.child("insuranceType").getValue(String.class)
                                                ,postSnapshot.child("paymentMethod").getValue(String.class)
                                                ,services);


                                        if(postSnapshot.child("totalRating").exists()) {
                                            clinic.setRating(postSnapshot.child("totalRating").getValue(float.class));
                                        }
                                        clinic.setClinicID(postSnapshot.child("clinicID").getValue(String.class));
                                        clinic.setWaitTime(postSnapshot.child("waitTime").getValue(Integer.class));

                                        clinics.add(clinic);
                                    }


                                }



                            }
                            ClinicList serviceAdapter=new ClinicList(SearchClinics.this,clinics);
                            clinicsView.setAdapter(serviceAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

                else{


                    String day=spinnerDays.getSelectedItem().toString();

                    final String[] days = {"Monday", "Tuesday", "Wednesday" , "Thursday" , "Friday" , "Saturday" , "Sunday"};
                    int index = 0;
                    for(int i=0;i<days.length;i++){
                        if(day.equals(days[i])){
                            index=i;

                        }
                    }
                    final int finalIndex = index;
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String startTime=start.getSelectedItem().toString();
                            String endTime=end.getSelectedItem().toString();
                            clinics.clear();

                            for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                int count=0;
                                for(DataSnapshot postSnapshot2:postSnapshot.child("workinghours").getChildren()){
                                    if(count== finalIndex) {
                                        int start_time=Integer.parseInt(startTime);
                                        int end_time=Integer.parseInt(endTime);
                                        if ((postSnapshot2.child("startHours").getValue(Integer.class)).compareTo(start_time)>=0 && (postSnapshot2.child("endHours").getValue(Integer.class)).compareTo(end_time)<=0 ){
                                            List<Service> services = new ArrayList<>();
                                            for (DataSnapshot service : postSnapshot.child("services").getChildren()) {
                                                services.add(service.getValue(Service.class));
                                            }

                                            Clinic clinic = new Clinic(postSnapshot.child("address").getValue(String.class)
                                                    , postSnapshot.child("phone").getValue(Integer.class)
                                                    , postSnapshot.child("clinicName").getValue(String.class)
                                                    , postSnapshot.child("insuranceType").getValue(String.class)
                                                    , postSnapshot.child("paymentMethod").getValue(String.class)
                                                    , services);



                                            if(postSnapshot.child("totalRating").exists()) {
                                                clinic.setRating(postSnapshot.child("totalRating").getValue(float.class));
                                            }
                                            clinic.setClinicID(postSnapshot.child("clinicID").getValue(String.class));
                                            clinic.setWaitTime(postSnapshot.child("waitTime").getValue(Integer.class));

                                            clinics.add(clinic);
                                        }
                                    }
                                    count++;


                                }



                            }
                            ClinicList serviceAdapter=new ClinicList(SearchClinics.this,clinics);
                            clinicsView.setAdapter(serviceAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }





                }

        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchClinics.this, HomeScreen.class ));
            }
        });





    }






}
