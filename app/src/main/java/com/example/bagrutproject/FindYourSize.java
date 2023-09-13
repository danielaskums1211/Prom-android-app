package com.example.bagrutproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindYourSize extends AppCompatActivity {
private EditText bust;
private EditText waist;
private EditText hips;
private Button showYourSizeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_your_size2);
        bust = (EditText) findViewById(R.id.bustMeasurement);
        waist = (EditText) findViewById(R.id.waistMeasurement);
        hips = (EditText) findViewById(R.id.hipsMeasurement);
        showYourSizeBtn = (Button) findViewById(R.id.showSizeButton);

        showYourSizeBtn.setOnClickListener(new View.OnClickListener(){//when  button is clicked, it shows a message with your fit size
            @Override
            public void onClick(View view) {//sends a toast of the user's fit size and switches to the next activitythe

                ShowYourSize();
                Intent NextPageIntent = new Intent(FindYourSize.this, ChooseHowToDesign.class);
                startActivity(NextPageIntent);

            }


        });

    }
    private void ShowYourSize()//sends a toast message with your fit size according to the measurements you entered.
    {
        String bustString=bust.getText().toString();
        String waistString=waist.getText().toString();
        String hipsString=hips.getText().toString();

        double doubleBust=Double.parseDouble(bustString);
        double doubleWaist=Double.parseDouble(waistString);
        double doubleHips=Double.parseDouble(hipsString);

        if(doubleBust>77.5&&doubleBust<78.5&&doubleWaist>59.5&&doubleWaist<60.5&&doubleHips>83&&doubleHips<84)
        {
            Toast.makeText(this,"Your size is 32 (EU)",Toast.LENGTH_LONG).show();
        }
        if(doubleBust>80&&doubleBust<81&&doubleWaist>62&&doubleWaist<63&&doubleHips>85.5&&doubleHips<86.5)
        {
            Toast.makeText(this,"Your size is 34 (EU)",Toast.LENGTH_LONG).show();
        }
        if(doubleBust>82.5&&doubleBust<83.5&&doubleWaist>64.5&&doubleWaist<65.5&&doubleHips>88&&doubleHips<89)
        {
            Toast.makeText(this,"Your size is 36 (EU)",Toast.LENGTH_LONG).show();
        }
        if(doubleBust>87.5&&doubleBust<88.5&&doubleWaist>69.5&&doubleWaist<70.5&&doubleHips>93&&doubleHips<94)
        {
            Toast.makeText(this,"Your size is 38 (EU)",Toast.LENGTH_LONG).show();
        }
        if(doubleBust>92.5&&doubleBust<93.5&&doubleWaist>74.5&&doubleWaist<75.5&&doubleHips>98&&doubleHips<99)
        {
            Toast.makeText(this,"Your size is 40 (EU)",Toast.LENGTH_LONG).show();
        }
        if(doubleBust>97.5&&doubleBust<98.5&&doubleWaist>79.5&&doubleWaist<80.5&&doubleHips>103&&doubleHips<104)
        {
            Toast.makeText(this,"Your size is 42 (EU)",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)//menu function
    {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)//menu function that uses intents to switch to another activity
    {
        switch(item.getItemId())
        {
            case R.id.createDress:
                Intent NextPageIntent1 = new Intent(FindYourSize.this, ChooseHowToDesign.class);
                this.startActivity(NextPageIntent1);
                return true;
            case R.id.viewDesigns:
                Intent NextPageIntent2 = new Intent(FindYourSize.this, ViewPreviousDesigns.class);
                this.startActivity(NextPageIntent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

}
