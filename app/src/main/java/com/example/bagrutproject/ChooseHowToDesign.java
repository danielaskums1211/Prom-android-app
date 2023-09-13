package com.example.bagrutproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ChooseHowToDesign extends AppCompatActivity {
    private Button uploadBtn;
    private Button designwithDM;
    private Button viewPreviousDesigns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_how_to_design);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        uploadBtn = (Button) findViewById(R.id.uploadPhoto);
        designwithDM = (Button) findViewById(R.id.designWithDM);
        viewPreviousDesigns = (Button) findViewById(R.id.viewPreviousDesigns);

        //3 functions for 3 buttons. Each function creates a different intent in order to switch to another activity
        designwithDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent designIntent = new Intent(ChooseHowToDesign.this, DesignYourDress.class);
                startActivity(designIntent);
            }
        });
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uploadIntent = new Intent(ChooseHowToDesign.this, UploadImage.class);
                startActivity(uploadIntent);
            }
        });
        viewPreviousDesigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewPreviousIntent = new Intent(ChooseHowToDesign.this, ViewPreviousDesigns.class);
                startActivity(viewPreviousIntent);
            }
        });



    }
}
