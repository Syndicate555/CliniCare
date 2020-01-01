package com.example.intercrew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText firstNameId,lastNameId,emailId,password,confirmPassword;
    Button btnSignUp;
    TextView tvSignIn,signUpPatient;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    RadioGroup employeeOrpatient;
    RadioButton selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initializing Firebase references
        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();

        //initializing radioButtons
        employeeOrpatient=findViewById(R.id.groupID);

        //EditText sections
        firstNameId=findViewById(R.id.firstNameEditText);
        lastNameId=findViewById(R.id.lastNameEditText);
        emailId=findViewById(R.id.emailEditText);
        password=findViewById(R.id.passEditText);
        confirmPassword=findViewById(R.id.confrimPassEditText);
        //sign up button
        btnSignUp=findViewById(R.id.signUpButton);
        //sign up for patients
        //signUpPatient=findViewById(R.id.signUpForPatient);
        //sign in TextView
        tvSignIn=findViewById(R.id.logInTextView);

        //setting click listeners
        //Sign up button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpOnClick(v);
            }
        });
        //End of Click

        //Sign in Text View
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });








    }

    //Method used to SignUp a user
    public void signUpOnClick(View v){
        //Storing all attributes of Patient
        String email = emailId.getText().toString();
        String pwd = password.getText().toString();
        String confirmPwd = confirmPassword.getText().toString();
        String firstName = firstNameId.getText().toString();
        String lastName = lastNameId.getText().toString();
        boolean pass=true;
        //Validating the inputs
        if (email.isEmpty() && pwd.isEmpty() && confirmPwd.isEmpty()&& firstName.isEmpty()&& lastName.isEmpty()) {
            Toast.makeText(SignUp.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
            pass=false;


        }
        if (firstName.isEmpty()) {
            firstNameId.setError("Please enter first name");
            firstNameId.requestFocus();
            pass=false;

        }
        if (lastName.isEmpty()) {
            lastNameId.setError("Please enter last name");
            lastNameId.requestFocus();
            pass=false;

        }

        if (email.isEmpty()) {
            emailId.setError("Please enter email");
            emailId.requestFocus();
            pass=false;

        }
        if (pwd.isEmpty()) {
            password.setError("Please enter password");
            password.requestFocus();
            pass=false;

        }
        if (confirmPwd.isEmpty()) {
            confirmPassword.setError("Please confirm your password");
            confirmPassword.requestFocus();
            pass=false;

        }
        if (!confirmPwd.equals(pwd)) {
            confirmPassword.setError("Passwords do not match");
            confirmPassword.requestFocus();
            pass=false;
        }
        if(pass){


            int id=employeeOrpatient.getCheckedRadioButtonId();
            selected=findViewById(id);
           if(selected.getText().equals("Employee")) {


               //still need to fix this
               final Employee mEmployee = new Employee(firstName, lastName, email, pwd, null);
               final Account mAccount = new Account(mEmployee, "an Employee");
               mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).setValue(mAccount)
                                   .addOnCompleteListener(SignUp.this, new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if (task.isSuccessful()) {
                                               finish();
                                               startActivity(new Intent(SignUp.this, MainActivity.class));
                                               Toast.makeText(SignUp.this, "Account Created Please Sign In", Toast.LENGTH_SHORT).show();


                                           } else {
                                               Toast.makeText(SignUp.this, "Firebase Database Error", Toast.LENGTH_SHORT).show();

                                           }

                                       }
                                   });
                           //End of adding Employee to the Database

                       } else {
                           Toast.makeText(SignUp.this, "Firebase Authentication Error", Toast.LENGTH_SHORT).show();

                       }

                   }
               });
           }

           else{

               final Patient mPatient = new Patient(firstName,lastName,email,pwd);
               mPatient.setId(mAuth.getCurrentUser().getUid());
               final Account mAccount=new Account(mPatient,"a Patient");

               mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).setValue(mAccount)
                                   .addOnCompleteListener(SignUp.this, new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful()){
                                               finish();
                                               startActivity(new Intent(SignUp.this,MainActivity.class));
                                               Toast.makeText(SignUp.this, "Account Created Please Sign In", Toast.LENGTH_SHORT).show();



                                           }
                                           else{
                                               Toast.makeText(SignUp.this, "Firebase Database Error", Toast.LENGTH_SHORT).show();

                                           }

                                       }
                                   });
                           //End of adding Employee to the Database

                       }
                       else{
                           Toast.makeText(SignUp.this, "Firebase Authentication Error", Toast.LENGTH_SHORT).show();

                       }

                   }
               });
           }
        }





    }




}
