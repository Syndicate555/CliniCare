package com.example.intercrew.Admin;

import com.example.intercrew.Account;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intercrew.Employee;
import com.example.intercrew.Patient;
import com.example.intercrew.R;
import com.example.intercrew.User;
import com.example.intercrew.UserList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ModifyUsers extends AppCompatActivity {

    //FireBase database
    private DatabaseReference mDatabase;

    //layout
    TextView userName;
    TextView userRole;
    TextView userEmail;
    ListView usersView;

    //arrays
    private ArrayList<Account> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_users);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");

        //initializing
        userEmail=findViewById(R.id.textViewUserEmail);
        userName=findViewById(R.id.textViewUserName);
        //userRole = findViewById(R.id.textViewUserRole);
        usersView=findViewById(R.id.UsersViewID);



        //making a set listener to update listView
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    if(postSnapshot.child("role").getValue(String.class).equals("an Employee")){

                        Employee e=new Employee(postSnapshot.child("user").child("firstName").getValue(String.class)
                                ,postSnapshot.child("user").child("lastName").getValue(String.class),postSnapshot.child("user").child("email").getValue(String.class)
                                ,postSnapshot.child("user").child("password").getValue(String.class)

                        );
                        e.setId(postSnapshot.getKey());

                        Account user = new Account(e,"an Employee");
                        users.add(user);
                        UserList productsAdapter=new UserList(ModifyUsers.this,users);
                        usersView.setAdapter(productsAdapter);
                    }
                    else{
                        Patient p=new Patient(postSnapshot.child("user").child("firstName").getValue(String.class)
                                ,postSnapshot.child("user").child("lastName").getValue(String.class),postSnapshot.child("user").child("email").getValue(String.class)
                                ,postSnapshot.child("user").child("password").getValue(String.class));
                        p.setId(postSnapshot.getKey());


                        Account user = new Account(p,"a Patient");
                        users.add(user);
                        UserList productsAdapter=new UserList(ModifyUsers.this,users);
                        usersView.setAdapter(productsAdapter);

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //showing dialog box when user is clicked
        usersView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = users.get(i).getUser();
                showUpdateDeleteDialog(user.getId(), user.getFirstName());
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
                users.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    if(postSnapshot.child("role").getValue(String.class).equals("an Employee")){

                        Employee e=new Employee(postSnapshot.child("user").child("firstName").getValue(String.class)
                                ,postSnapshot.child("user").child("lastName").getValue(String.class),postSnapshot.child("user").child("email").getValue(String.class)
                                ,postSnapshot.child("user").child("password").getValue(String.class)

                        );
                        e.setId(postSnapshot.getKey());

                        Account user = new Account(e,"an Employee");
                        users.add(user);
                        UserList productsAdapter=new UserList(ModifyUsers.this,users);
                        usersView.setAdapter(productsAdapter);
                    }
                    else{
                        Patient p=new Patient(postSnapshot.child("user").child("firstName").getValue(String.class)
                                ,postSnapshot.child("user").child("lastName").getValue(String.class),postSnapshot.child("user").child("email").getValue(String.class)
                                ,postSnapshot.child("user").child("password").getValue(String.class));
                        p.setId(postSnapshot.getKey());


                        Account user = new Account(p,"a Patient");
                        users.add(user);
                        UserList productsAdapter=new UserList(ModifyUsers.this,users);
                        usersView.setAdapter(productsAdapter);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void showUpdateDeleteDialog(final String userId, String userEmail) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_user, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDeleteUser = dialogView.findViewById(R.id.buttonDeleteUser);

        dialogBuilder.setTitle(userEmail);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(userId);
                b.dismiss();
            }
        });
    }

    private boolean deleteUser(final String id){

        mDatabase.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //an Employee with a clinic assigned

                if(dataSnapshot.child("user").child("clinicID").exists()){
                    Toast.makeText(getApplicationContext(),"TESTTEST",Toast.LENGTH_LONG).show();


                    DatabaseReference dR=FirebaseDatabase.getInstance().getReference().child("Clinics").child(dataSnapshot.child("user").child("clinicID").getValue(String.class));
                    dR.removeValue();

                    DatabaseReference dR2=FirebaseDatabase.getInstance().getReference("Users").child(id);
                    dR2.removeValue();



                }
                else{

                    DatabaseReference dR=FirebaseDatabase.getInstance().getReference("Users").child(id);

                    dR.removeValue();
                    Toast.makeText(getApplicationContext(),"User Deleted",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return true;
    }

}
