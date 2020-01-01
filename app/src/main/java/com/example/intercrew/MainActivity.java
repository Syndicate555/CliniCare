package com.example.intercrew;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intercrew.Admin.Signin_Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//Login Screen
public class MainActivity extends AppCompatActivity {                       
    private Context mMockContext;
    EditText emailId, password;
    Button btnSignIn;
    TextView tvSignUp,tvSignInAdmin;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        emailId = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.loginId);
        tvSignUp = findViewById(R.id.signUp);
        tvSignInAdmin=findViewById(R.id.adminSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInOnClick(v);
            }


        });




        //firebase
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        //initializing database references
        mAuth= FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(emailId.getText().toString(),password.getText().toString());
                signInOnClick(v);
            }
        });


        tvSignUp.setOnClickListener(new View.OnClickListener(){
        	@Override
            public void onClick(View v) {
        		startActivity(new Intent(MainActivity.this, SignUp.class ));
        	}
        });

        tvSignInAdmin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Signin_Admin.class ));
            }
        });

    }

    public void signInOnClick(View v){

        String email = emailId.getText().toString();
        String pwd = password.getText().toString();
        if (email.isEmpty()){
            emailId.setError("Please Enter Email Id");
            emailId.requestFocus();

        }
        else if (pwd.isEmpty()){
            password.setError("Please Enter your Password");
            password.requestFocus();
        }
        else if (email.isEmpty() && pwd.isEmpty()) {
            Toast.makeText(MainActivity.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();

        }
        else if (!(email.isEmpty() && pwd.isEmpty())){

            mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "SignIn Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();

                    }
                    else{

                        mUser=mAuth.getCurrentUser();
                        mDatabase
                                .child(mUser.getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {

                                            role = dataSnapshot.child("role").getValue(String.class);

                                            if (role.equals("a Patient")) {
                                                startActivity(new Intent(MainActivity.this, HomeScreen.class));

                                            } else {

                                                if (dataSnapshot.child("user").child("clinicID").exists()) {
                                                    startActivity(new Intent(MainActivity.this, EmployeeHomePageAssigned.class));

                                                } else {
                                                    startActivity(new Intent(MainActivity.this, EmployeeHomeNotAssigned.class));
                                                }


                                            }


                                        }
                                        else{
                                            Toast.makeText(MainActivity.this, "Account Deleted, Sign in using another account", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }
                   

                }
            });


        }
        else {
            Toast.makeText(MainActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
        }

    }
    

    public String validate(String userName, String password) {
        if(userName.equals("user") && password.equals("password"))
            return "Login was successful";
        else
            return "Invalid login!";
    }


}
