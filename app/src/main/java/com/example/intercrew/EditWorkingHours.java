package com.example.intercrew;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.intercrew.R.id.buttonUpdateWorkHours;

public class EditWorkingHours extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference clinicReference;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    String clinicref;

    //layout
    String id;
    Button updateBtn;
    Button doneButton;
    ListView workingHoursLView;

    // Array of working hours
    private ArrayList<DayHours> workingHrs = new ArrayList<>();



    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_working_hours);

        //initializing
        workingHoursLView = findViewById(R.id.workingHoursLView);
        updateBtn= findViewById(buttonUpdateWorkHours);
        doneButton=findViewById(R.id.DoneButtonID);

        mAuth= FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();
        clinicReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("user").child("clinicID");
        clinicReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Clinics").child(dataSnapshot.getValue(String.class)).child("workinghours");

                //updating the list
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        workingHrs.clear();
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            DayHours hours = postSnapshot.getValue(DayHours.class);
                            workingHrs.add(hours);
                        }
                        DayHoursList hoursAdapter = new DayHoursList(EditWorkingHours.this, workingHrs);
                        workingHoursLView.setAdapter(hoursAdapter);
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




        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditWorkingHours.this,EmployeeHomePageAssigned.class));
            }
        });










        //showing dialog box when service is clicked
            workingHoursLView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DayHours shift = workingHrs.get(i);
                showUpdateDeleteDialog(shift.getID());
                return true;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        clinicReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Clinics").child(dataSnapshot.getValue(String.class)).child("workinghours");

                //updating the list
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        workingHrs.clear();
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            DayHours hours = postSnapshot.getValue(DayHours.class);
                            workingHrs.add(hours);
                        }
                        DayHoursList hoursAdapter = new DayHoursList(EditWorkingHours.this, workingHrs);
                        workingHoursLView.setAdapter(hoursAdapter);
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


    private void updateWHours(String id,int startHours,int startMinutes,int endHours,int endMinutes){

        DatabaseReference dR =mDatabase.child(id);

        DayHours updateHours= new DayHours(id, startHours, startMinutes, endHours, endMinutes);
        dR.setValue(updateHours);

        Toast.makeText(getApplicationContext(),"Working Hours Updated",Toast.LENGTH_LONG).show();
    }

    //added
    public void showUpdateDeleteDialog(final String id) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_workinghours, null);
        dialogBuilder.setView(dialogView);

        final EditText editStartHr = dialogView.findViewById(R.id.editStartHour);
        final EditText editStartMin  = dialogView.findViewById(R.id.editStartMin);
        final EditText editEndHr = dialogView.findViewById(R.id.editEndHour);
        final EditText editEndMin = dialogView.findViewById(R.id.editEndMin);
        final Button buttonUpdate = dialogView.findViewById(buttonUpdateWorkHours);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startH = editStartHr.getText().toString().trim();
                String endH = editEndHr.getText().toString().trim();
                String startM = editStartMin.getText().toString().trim();
                String endM = editEndMin.getText().toString().trim();

                if (!TextUtils.isEmpty(startM)&& !TextUtils.isEmpty(endM) && TextUtils.isEmpty(startH) && !TextUtils.isEmpty(endH)){
                    editStartHr.setError("Please Enter Start Hours");
                    editStartHr.requestFocus();
                    Toast.makeText(getApplicationContext(),"Updating Hours Unsuccessful",Toast.LENGTH_LONG).show();
                }

                else if (!TextUtils.isEmpty(startM)&& !TextUtils.isEmpty(endM) && !TextUtils.isEmpty(startH) && TextUtils.isEmpty(endH)){
                    editStartMin.setError("Please Enter the Start Minutes");
                    editStartMin.requestFocus();
                    Toast.makeText(getApplicationContext(),"Updating Hours Unsuccessful",Toast.LENGTH_LONG).show();
                }

                else if (!TextUtils.isEmpty(startM)&& !TextUtils.isEmpty(endM) && !TextUtils.isEmpty(startH) && TextUtils.isEmpty(endH)){
                    editEndHr.setError("Please Enter End Hours");
                    editEndHr.requestFocus();
                    Toast.makeText(getApplicationContext(),"Updating Hours Unsuccessful",Toast.LENGTH_LONG).show();
                }

                else if (!TextUtils.isEmpty(startM)&& TextUtils.isEmpty(endM) && !TextUtils.isEmpty(startH) && !TextUtils.isEmpty(endH)){
                    editEndMin.setError("Please Enter the End Minutes");
                    editEndMin.requestFocus();
                    Toast.makeText(getApplicationContext(),"Updating Hours Unsuccessful",Toast.LENGTH_LONG).show();
                }

                else if(!TextUtils.isEmpty(startM) && !TextUtils.isEmpty(endM) && !TextUtils.isEmpty(startH) && !TextUtils.isEmpty(endH)){
                    int startHrs = Integer.parseInt(startH);
                    int endHrs = Integer.parseInt(endH);
                    int startMin = Integer.parseInt(startM);
                    int endMin = Integer.parseInt(endM);

                    updateWHours(id, startHrs, startMin, endHrs, endMin);
                    b.dismiss();
                }
            }
        });

    }

    }
