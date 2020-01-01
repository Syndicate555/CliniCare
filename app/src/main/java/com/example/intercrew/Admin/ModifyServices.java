package com.example.intercrew.Admin;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intercrew.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ModifyServices extends AppCompatActivity {

    //firebase database
    private DatabaseReference mDatabase;

    //layout
    EditText serviceName;
    EditText serviceRole;
    Button serviceAdd;
    ListView servicesView;

    //arrays
    private ArrayList<Service> services=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_services);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("services");

        //initializing
        serviceName=findViewById(R.id.NameId);
        serviceRole=findViewById(R.id.RoleId);
        serviceAdd=findViewById(R.id.AddServiceButton);
        servicesView=findViewById(R.id.ServicesViewID);



        //making a set listener to update listView

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Service service=postSnapshot.getValue(Service.class);
                    services.add(service);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //make a onclick listener on add button
        serviceAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addService(v);
            }
        });


        //showing dialog box when service is clicked
        servicesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                showUpdateDeleteDialog(service.getID(), service.getServiceName());
                return true;
            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Service product=postSnapshot.getValue(Service.class);
                    services.add(product);
                }
                ServiceList productsAdapter=new ServiceList(ModifyServices.this,services);
                servicesView.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //added

    public void showUpdateDeleteDialog(final String serviceId, String serviceName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editServiceName);
        final EditText editTextrole  = (EditText) dialogView.findViewById(R.id.editRole);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonAddService);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteService);

        dialogBuilder.setTitle(serviceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String role= editTextrole.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    updateService(serviceId, name, role);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(serviceId);
                b.dismiss();
            }
        });
    }




    private void addService(View v){
        //getting the values to save
        String name=serviceName.getText().toString().trim();
        String role=serviceRole.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(role)){
            String id=mDatabase.push().getKey();

            Service service=new Service(id,name,role);

            mDatabase.child(id).setValue(service);

            serviceName.setText("");
            serviceRole.setText("");


            Toast.makeText(this,"Service successfully added",Toast.LENGTH_LONG).show();


        }
        else if(TextUtils.isEmpty(name) && !TextUtils.isEmpty(role)){
            serviceName.setError("Please Enter the Service Name");
            serviceName.requestFocus();
            Toast.makeText(this,"Adding Service was unsuccessful",Toast.LENGTH_LONG).show();

        }

        else if(!TextUtils.isEmpty(name) && TextUtils.isEmpty(role)){
            serviceRole.setError("Please Enter the role of employee running the Service");
            serviceRole.requestFocus();
            Toast.makeText(this,"Adding Service was unsuccessful",Toast.LENGTH_LONG).show();

        }
        else{
            serviceName.setError("Please Enter the Service Name");
            serviceName.requestFocus();
            serviceRole.setError("Please Enter the role of employee running the Service");
            serviceRole.requestFocus();
            Toast.makeText(this,"Adding Service was unsuccessful",Toast.LENGTH_LONG).show();
        }

    }

    private void updateService(String id,String name,String role){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("services").child(id);

        Service service= new Service(id,name,role);
        dR.setValue(service);

        Toast.makeText(getApplicationContext(),"Service Updated",Toast.LENGTH_LONG).show();
    }

    private boolean deleteService(String id){
        DatabaseReference dR=FirebaseDatabase.getInstance().getReference("services").child(id);

        dR.removeValue();
        Toast.makeText(getApplicationContext(),"Service Deleted",Toast.LENGTH_LONG).show();
        return true;
    }

}
