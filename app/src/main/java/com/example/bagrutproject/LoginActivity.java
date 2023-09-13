package com.example.bagrutproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private TextView registerText;
    private String strEmail;
    private String strPassword;

    private FirebaseAuth mFireBaseAuth;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerText = (TextView) findViewById(R.id.register);

        mFireBaseAuth = FirebaseAuth.getInstance();

        sp = getSharedPreferences("MyPref",0);
        String emailFromSP = sp.getString( "Email", "").trim();
        String passwordFromSP = sp.getString( "Password", "").trim();


        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//the function uses intent to switch to the next activity
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }

        });

        if(emailFromSP!=null && passwordFromSP!=null && emailFromSP !="" && passwordFromSP !="")//the function checks if the user already signed up (his mail and password saved to SP) and logs him in. Otherwise the user has to register
        {
            mFireBaseAuth.signInWithEmailAndPassword(emailFromSP, passwordFromSP).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful())
                    {
                        Toast.makeText(LoginActivity.this, "Login error, Please try again!", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "You are logged in!", Toast.LENGTH_SHORT).show();
                        Intent findYourSizeIntent = new Intent(LoginActivity.this, FindYourSize.class);
                        startActivity(findYourSizeIntent);
                    }

                }
            });
        }
        else
        {
            Toast.makeText(LoginActivity.this, "Please register!", Toast.LENGTH_SHORT);

        }

    }

    @Override
    protected void onStart() {//the function checks if there are any existed users in Firebase
        super.onStart();
        FirebaseUser currentUser = mFireBaseAuth.getCurrentUser();
        updateUI(currentUser);

    }
    private void updateUI(FirebaseUser user) {//default messages if there is a user/ if there is not one saved in Firebase
        if (user != null) {
            Toast.makeText(LoginActivity.this, "Welcome to Dress Me!", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(LoginActivity.this, "Please sign in!", Toast.LENGTH_SHORT).show();

        }
    }


}


