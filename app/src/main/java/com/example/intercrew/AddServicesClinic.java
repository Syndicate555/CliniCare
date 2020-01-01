package com.example.intercrew;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

public class AddServicesClinic extends AppCompatActivity {
    ListView clinicServicesView;
    ListView availableServicesView;
    Button doneButton;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference adminServicesData;
    private DatabaseReference clinicReference;
    private DatabaseReference clinicServicesData;
    private ArrayList<Service> clinicServices=new ArrayList<>();
    private ArrayList<Service> availableServices=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services_clinic);

        //initialize list views
        clinicServicesView=findViewById(R.id.ClinicViewId);

        availableServicesView=findViewById(R.id.ServicesListView);


        //initializing button
        doneButton=findViewById(R.id.doneButton);



        //Initializing fireBase contents
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        adminServicesData = FirebaseDatabase.getInstance().getReference().child("services");
        clinicReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("user").child("clinicID");

        //getting storing clinic ref number from the employee
        clinicReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                clinicServicesData = FirebaseDatabase.getInstance().getReference().child("Clinics").child(dataSnapshot.getValue(String.class)).child("services");
                //add listener to Clinic services
                clinicServicesData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        clinicServices.clear();
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            Service service=postSnapshot.getValue(Service.class);
                            clinicServices.add(service);
                        }
                        ServiceList productsAdapter=new ServiceList(AddServicesClinic.this,clinicServices);
                        clinicServicesView.setAdapter(productsAdapter);

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







        //add listener to Admin services
        adminServicesData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availableServices.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Service service=postSnapshot.getValue(Service.class);
                    availableServices.add(service);
                }
                ServiceList productsAdapter=new ServiceList(AddServicesClinic.this,availableServices);
                availableServicesView.setAdapter(productsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        clinicServicesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Service service = clinicServices.get(position);
                showDeleteDialog(service.getID(), service.getServiceName());
                return true;
            }
        });


        availableServicesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Service service = availableServices.get(position);
                showAddDialog(service.getID(), service.getServiceName());
                return true;
            }
        });



        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddServicesClinic.this,EmployeeHomePageAssigned.class));
            }
        });



    }



    public void showDeleteDialog(final String serviceId, String serviceName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.clinicservicedelete, null);
        dialogBuilder.setView(dialogView);
        final Button buttonDelete  = (Button) dialogView.findViewById(R.id.buttonDeleteService);

        dialogBuilder.setTitle(serviceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteService(serviceId);
                b.dismiss();

            }
        });
    }



    public void showAddDialog(final String serviceId, String serviceName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.clinicserviceadd, null);
        dialogBuilder.setView(dialogView);
        final Button buttonAdd  = (Button) dialogView.findViewById(R.id.buttonAddService);

        dialogBuilder.setTitle(serviceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addService(serviceId);
                b.dismiss();
            }
        });
    }

    //start of onCreate
    @Override
    protected void onStart(){
        super.onStart();
        adminServicesData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availableServices.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Service service=postSnapshot.getValue(Service.class);
                    availableServices.add(service);
                }
                ServiceList productsAdapter=new ServiceList(AddServicesClinic.this,availableServices);
                availableServicesView.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //getting storing clinic ref number from the employee
        clinicReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                clinicServicesData = FirebaseDatabase.getInstance().getReference().child("Clinics").child(dataSnapshot.getValue(String.class)).child("services");
                clinicServicesData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        clinicServices.clear();
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            Service service=postSnapshot.getValue(Service.class);
                            clinicServices.add(service);
                        }
                        ServiceList productsAdapter=new ServiceList(AddServicesClinic.this,clinicServices);
                        clinicServicesView.setAdapter(productsAdapter);
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



    private void addService(final String id){

        DatabaseReference dR=adminServicesData.child(id);
        dR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Service newService=dataSnapshot.getValue(Service.class);


                //getting storing clinic ref number from the employee
                clinicReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        clinicServicesData = FirebaseDatabase.getInstance().getReference().child("Clinics").child(dataSnapshot.getValue(String.class)).child("services");
                        clinicServicesData.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                boolean test=false;
                                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                                    if(postSnapshot.getValue(Service.class).getServiceName().equals(newService.getServiceName())){
                                        test=true;

                                    }

                                }

                                if(test){
                                    Toast.makeText(getApplicationContext(),"Already Have Service",Toast.LENGTH_LONG).show();

                                }
                                else{

                                    final String id2=clinicServicesData.push().getKey();
                                    newService.setID(id2);

                                    clinicServicesData.child(id2).setValue(newService);
                                    Toast.makeText(getApplicationContext(),"Service Added",Toast.LENGTH_LONG).show();

                                }




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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }

    private boolean deleteService(final String id){
        //getting storing clinic ref number from the employee
        clinicReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                clinicServicesData = FirebaseDatabase.getInstance().getReference().child("Clinics").child(dataSnapshot.getValue(String.class)).child("services");
                DatabaseReference dR=clinicServicesData.child(id);

                dR.removeValue();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(getApplicationContext(),"Service Deleted",Toast.LENGTH_LONG).show();
        return true;

    }

    public String validate(String serviceName, String role) {
        if(serviceName.equals("serviceName") && role.equals("role"))
            return "Adding service successful";
        else
            return "Error adding service";
    }

}
