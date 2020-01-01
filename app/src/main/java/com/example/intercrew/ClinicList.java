package com.example.intercrew;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.intercrew.Admin.ServiceList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;



public class ClinicList extends ArrayAdapter<Clinic> {
    private Activity context;
    List<Clinic> clinics;
    DatabaseReference mDatabase;

    public ClinicList(Activity context, List<Clinic> clinics) {
        super(context, R.layout.layout_clinic_list, clinics);
        this.context = context;
        this.clinics = clinics;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_clinic_list, null, true);

        TextView textClinicName = (TextView) listViewItem.findViewById(R.id.ClinicNameID);
        TextView textViewAddress= (TextView) listViewItem.findViewById(R.id.AddressID);
        TextView textViewWait= (TextView) listViewItem.findViewById(R.id.waitTimeID);
        ListView servicesView=listViewItem.findViewById(R.id.ServicesView);

        //add listeners later
        Button bookButton=listViewItem.findViewById(R.id.BookID);
        Button rateButton=listViewItem.findViewById(R.id.RateID);

        TextView textViewRating= (TextView) listViewItem.findViewById(R.id.RatingID);

        //updating textFields
        final Clinic clinic = clinics.get(position);
        textClinicName.setText(clinic.getClinicName());
        textViewAddress.setText(clinic.getAddress());
        textViewRating.setText("rating: "+clinic.getRating()+"/"+"5");
        textViewWait.setText("Wait Time: "+clinic.getWaitTime());

        //Filling the service list of the clinic
        ServiceList serviceAdapter=new ServiceList(context,clinic.getServices());
        servicesView.setAdapter(serviceAdapter);

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Rating.class);
                intent.putExtra("ClinicID",clinic.getClinicID());
                context.startActivity(intent);


            }


        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Clinics").child(clinic.getClinicID()).child("appointments");
                final FirebaseAuth mAuth= FirebaseAuth.getInstance();
                final FirebaseUser mUser= mAuth.getCurrentUser();
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean pass=true;
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            if(mUser.getUid().equals(postSnapshot.getKey())) {
                                pass=false;
                                Intent intent = new Intent(context,BookingClinic.class);
                                intent.putExtra("ClinicID",clinic.getClinicID());
                                context.startActivity(intent);

                            }

                            }
                        if(pass){

                            Intent intent = new Intent(context,CheckBooking.class);
                            intent.putExtra("ClinicID",clinic.getClinicID());
                            context.startActivity(intent);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }


        });



        return listViewItem;
    }




}

