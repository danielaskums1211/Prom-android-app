package com.example.bagrutproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {
   // private EditText username;
    private EditText password;
    private EditText mail;
    private EditText phoneNumber;
    private FirebaseAuth mFireBaseAuth;
    private Button registerBtn;
    private TextView activityLoginText;
    private String strPhoneNumber;
    private String strEmail;
    private String strPassword;

    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        mail = (EditText) findViewById(R.id.mail);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        mFireBaseAuth = FirebaseAuth.getInstance();
        registerBtn = (Button) findViewById(R.id.buttonRegister);
        activityLoginText = (TextView) findViewById(R.id.login);

        activityLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//switch to login activity
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//the function checks if all the fields were filled, saves them to SP and switches to the next activity
                strEmail = mail.getText().toString().trim();
                strPassword = password.getText().toString().trim();
                strPhoneNumber = phoneNumber.getText().toString();

                if (strEmail.isEmpty()) {
                    mail.setError("Please enter your email");
                    mail.requestFocus();

                }
                else if(strPhoneNumber.isEmpty())
                {
                    phoneNumber.setError("Please enter your phone number");
                    phoneNumber.requestFocus();
                }
                else if (strPassword.isEmpty()|| strPassword.length()<6) {
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else if (strEmail.isEmpty() && strPassword.isEmpty()&&strPhoneNumber.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                }
                else if (!(strEmail.isEmpty() && strPassword.isEmpty() &&strPhoneNumber.isEmpty())&&strPassword.length()>=6) {
                    mFireBaseAuth.createUserWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Sign up unsuccessful, please try again", Toast.LENGTH_SHORT).show();
                            } else {
                                // save the email and password in shared preferences
                                sp = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("Email", strEmail);
                                editor.putString("Password", strPassword);
                                editor.putInt("indexImg",0);
                                editor.commit();

                                Intent nextActivity=new Intent(RegisterActivity.this, VerifyPhoneNo.class);
                                nextActivity.putExtra("phoneNo", strPhoneNumber);
                                startActivity(nextActivity);
                            }

                        }
                    });
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }

}












