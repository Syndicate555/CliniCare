package com.example.intercrew.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intercrew.MainActivity;
import com.example.intercrew.R;
import com.example.intercrew.SignUp;
import com.google.firebase.auth.FirebaseAuth;


public class Signin_Admin extends AppCompatActivity {
    EditText userNameId, password;
    Button btnSignIn;
    TextView tvSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__admin);

        mFirebaseAuth = FirebaseAuth.getInstance();
        userNameId = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.loginId);
        tvSignIn = findViewById(R.id.signInEorP);
        tvSignUp = findViewById(R.id.signUp);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInOnClick(v);
            }
        });




        tvSignIn.setOnClickListener(new View.OnClickListener(){
        	@Override
            public void onClick(View v) {
        		startActivity(new Intent(Signin_Admin.this, MainActivity.class ));
        	}
        });

        tvSignUp.setOnClickListener(new View.OnClickListener(){
        	@Override
            public void onClick(View v) {
        		startActivity(new Intent(Signin_Admin.this, SignUp.class ));
        	}
        });

    }

    public void signInOnClick(View v){

        String userName = userNameId.getText().toString();
        String pwd = password.getText().toString();
        if (userName.isEmpty()){
            userNameId.setError("Please Enter Username Id");
            userNameId.requestFocus();

        }
        else if (pwd.isEmpty()){
            password.setError("Please Enter your Password");
            password.requestFocus();
        }
        else if (userName.isEmpty() && pwd.isEmpty()) {
            Toast.makeText(Signin_Admin.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();

        }
        else if (!(userName.isEmpty() && pwd.isEmpty())){


         if(userName.equals("admin") && pwd.equals("5T5ptQ")){
             startActivity(new Intent(Signin_Admin.this, AdminHomeScreen.class ));

         }
         else{
             Toast.makeText(Signin_Admin.this, "SignIn Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
         }


        }
        else {
            Toast.makeText(Signin_Admin.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
        }

    }
}
