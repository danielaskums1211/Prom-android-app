package com.example.bagrutproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;



public class VerifyPhoneNo extends AppCompatActivity {

    private EditText codeED;
    private Button verifyBtn;
    private ProgressBar progressBar;
    private String verificationCodeBySystem;
    private String strPhoneNo;
    //private IncomingSms incomingSms;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_no);
        progressBar=(ProgressBar) findViewById((R.id.progressBar));
        verifyBtn = (Button) findViewById(R.id.verifyBtn);
        codeED = (EditText) findViewById(R.id.codeED);
        progressBar.setVisibility(View.GONE);
        strPhoneNo=getIntent().getStringExtra("phoneNo");
        //IncomingSms incomingSms=new IncomingSms();


        SendVerificationCode(strPhoneNo);

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// if the user typed a valid code, a progress bar is shown and another function checks the code
                String code= codeED.getText().toString();
                if(code.isEmpty() || code.length()<6)
                {
                    codeED.setError("Wrong verification code!");
                    codeED.requestFocus();
                    return;

                }
                progressBar.setVisibility(View.VISIBLE);
                VerifyCode(code);

            }

        });


    }
    /*
    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(incomingSms, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(incomingSms);


    }

     */


    private void SendVerificationCode(String phoneNo)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+972" + phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {//the function gets the code and checks the entered code

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem= s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String strCode= phoneAuthCredential.getSmsCode();
            if(strCode!=null)
            {
                progressBar.setVisibility(View.VISIBLE);
                VerifyCode(strCode);

            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {//fail toast message
            Toast.makeText(VerifyPhoneNo.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };
    private void VerifyCode(String codeByUser)//verificaton of the code the user entred
    {
         PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationCodeBySystem,codeByUser);
         SignInTheUserByCredentials(credential);
    }

    private void SignInTheUserByCredentials(PhoneAuthCredential credential) {//if the verification was successful, the user goes to the next page, otherwise a fail toast message is shown
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(VerifyPhoneNo.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent nextActivity = new Intent(VerifyPhoneNo.this, FindYourSize.class);
                    startActivity(nextActivity);

                }
                else
                {
                    Toast.makeText(VerifyPhoneNo.this, "Error!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

}
