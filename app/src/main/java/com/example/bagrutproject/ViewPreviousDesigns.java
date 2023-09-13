package com.example.bagrutproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class ViewPreviousDesigns extends AppCompatActivity {
    private ImageView iv;
    private MyReceiver receiver;
    private Button previousBtn;
    private Button nextBtn;
    private SharedPreferences sp;
    private Integer numOfImg;
    private Integer indexImg;
    private String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_previous_designs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iv = findViewById(R.id.imageDesign);
        previousBtn = findViewById(R.id.previousBtn);
        nextBtn = findViewById(R.id.nextBtn);
        indexImg=1;

        sp = getSharedPreferences("MyPref",0);
        numOfImg=sp.getInt("indexImg",0);
        receiver = new MyReceiver(iv,this);
        String email=sp.getString("Email","");
        path=email+"/dressPreview";


        // read index from  SharedPref



        // read index...
        // gety user name
        // start service - put username/images+index
        if(numOfImg!=0)//if there are uploaded images in Firebase a service is started (downloading the images) and default image is set
        {
            Intent intent = new Intent(this, MyIntentService.class);
            intent.putExtra("path", path+indexImg+".jpg");
            startService(intent);
        }
        else
        {
            Toast.makeText(ViewPreviousDesigns.this, "There are no uploaded images to be shown.", Toast.LENGTH_SHORT).show();
        }
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//the function sets the images by their index
                if(indexImg==1)
                {
                    Toast.makeText(ViewPreviousDesigns.this, "No previous images exist.", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    indexImg--;
                    Intent intent=new Intent(ViewPreviousDesigns.this, MyIntentService.class);
                    intent.putExtra("path", path+indexImg+".jpg");
                    startService(intent);
                }

            }

        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//the function sets the images by their index
                if(indexImg==numOfImg)
                {
                    Toast.makeText(ViewPreviousDesigns.this, "There are no more images to be shown.", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    indexImg++;
                    Intent intent=new Intent(ViewPreviousDesigns.this, MyIntentService.class);
                    intent.putExtra("path", path+indexImg+".jpg");
                    startService(intent);
                }

            }

        });

    }

    @Override
    protected void onResume() {//service functions
        super.onResume();
        registerReceiver(receiver, new IntentFilter(MyIntentService.NOTIFICATION));



    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
