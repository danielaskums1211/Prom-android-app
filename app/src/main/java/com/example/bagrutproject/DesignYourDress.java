package com.example.bagrutproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;

import yuku.ambilwarna.AmbilWarnaDialog;

public class DesignYourDress extends AppCompatActivity {
    //private int[]shortDress;
    //private int[]longDress;
    //private int[]sleeveless;
    //private int[]tankTop;
    //private int[]offTheShoulder;
    private SharedPreferences sp;
    private LinearLayout colorPickerLayout;
    private Button colorPickerBtn;
    private Button chosenColorBtn;
    private int defaultColor;
    private boolean chosenTop=false;
    private boolean chosenBtm=false;
    private boolean chosenColor=false;


    private RadioButton tankTopBtn;
    private RadioButton sleevelessBtn;
    private RadioButton offTheShoulderBtn;
    private RadioButton longSkirt;
    private RadioButton shortSkirt;
    private ImageButton imgdesign1Top;
    private ImageButton imgdesign2Top;
    private ImageButton imgdesign3Top;
    private ImageButton imgdesign4Top;

    private ImageButton imgdesign1Btm;
    private ImageButton imgdesign2Btm;
    private ImageButton imgdesign3Btm;
    private ImageButton imgdesign4Btm;
    private ImageButton imgdesign5Btm;
    private ImageButton imgdesign6Btm;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_your_dress);
        tankTopBtn = (RadioButton) findViewById(R.id.radio_tanktop);
        sleevelessBtn = (RadioButton) findViewById(R.id.radio_sleeveless);
        offTheShoulderBtn = (RadioButton) findViewById(R.id.radio_offtheshoulder);
        longSkirt=(RadioButton) findViewById(R.id.radio_long);
        shortSkirt=(RadioButton) findViewById(R.id.radio_short);

        imgdesign1Top = (ImageButton) findViewById(R.id.imageGroup1);
        imgdesign2Top = (ImageButton) findViewById(R.id.imageGroup2);
        imgdesign3Top = (ImageButton) findViewById(R.id.imageGroup3);
        imgdesign4Top = (ImageButton) findViewById(R.id.imageGroup4);

        imgdesign1Btm = (ImageButton) findViewById(R.id.imageGroup1Btm);
        imgdesign2Btm = (ImageButton) findViewById(R.id.imageGroup2Btm);
        imgdesign3Btm = (ImageButton) findViewById(R.id.imageGroup3Btm);
        imgdesign4Btm = (ImageButton) findViewById(R.id.imageGroup4Btm);
        imgdesign5Btm = (ImageButton) findViewById(R.id.imageGroup5Btm);
        imgdesign6Btm = (ImageButton) findViewById(R.id.imageGroup6Btm);

        colorPickerLayout=(LinearLayout) findViewById(R.id.image_container2);
        colorPickerBtn=(Button) findViewById(R.id.btnColorPicker);
        chosenColorBtn=(Button) findViewById(R.id.chosenColorBtn);
        defaultColor= ContextCompat.getColor(this, R.color.colorPrimary);



        tankTopBtn.setOnClickListener(new View.OnClickListener() {//shows the dress tops that are tank tops
            @Override
            public void onClick(View view) {// The function sets the tank top designs of the dress
                imgdesign1Top.setVisibility(view.INVISIBLE);
                imgdesign2Top.setVisibility(view.INVISIBLE);
                imgdesign3Top.setVisibility(view.INVISIBLE);
                imgdesign4Top.setVisibility(view.INVISIBLE);

                imgdesign1Top.setImageResource(R.drawable.dress_top4t);
                imgdesign1Top.setTag(R.drawable.dress_top4t);
                imgdesign1Top.setVisibility(view.VISIBLE);

                imgdesign2Top.setImageResource(R.drawable.dress_top5t);
                imgdesign2Top.setTag(R.drawable.dress_top5t);
                imgdesign2Top.setVisibility(view.VISIBLE);

                imgdesign3Top.setImageResource(R.drawable.dress_top7t);
                imgdesign3Top.setTag(R.drawable.dress_top7t);
                imgdesign3Top.setVisibility(view.VISIBLE);

                imgdesign4Top.setImageResource(R.drawable.dress_top9t);
                imgdesign4Top.setTag(R.drawable.dress_top9t);
                imgdesign4Top.setVisibility(view.VISIBLE);




            }

        });

        offTheShoulderBtn.setOnClickListener(new View.OnClickListener() {// The function sets the off the shoulder designs of the dress
            @Override
            public void onClick(View view) {
                imgdesign1Top.setVisibility(view.INVISIBLE);
                imgdesign2Top.setVisibility(view.INVISIBLE);
                imgdesign3Top.setVisibility(view.INVISIBLE);
                imgdesign4Top.setVisibility(view.INVISIBLE);

                imgdesign1Top.setImageResource(R.drawable.dress_top3t);
                imgdesign1Top.setTag(R.drawable.dress_top3t);
                imgdesign1Top.setVisibility(view.VISIBLE);

                imgdesign2Top.setImageResource(R.drawable.dress_top6t);
                imgdesign2Top.setTag(R.drawable.dress_top6t);
                imgdesign2Top.setVisibility(view.VISIBLE);




            }

        });
        sleevelessBtn.setOnClickListener(new View.OnClickListener() {//shows the dress tops that are sleeveless
            @Override
            public void onClick(View view) {// The function sets the sleeveless designs of the dress
                imgdesign1Top.setVisibility(view.INVISIBLE);
                imgdesign2Top.setVisibility(view.INVISIBLE);
                imgdesign3Top.setVisibility(view.INVISIBLE);
                imgdesign4Top.setVisibility(view.INVISIBLE);


                imgdesign1Top.setImageResource(R.drawable.dress_top1t);
                imgdesign1Top.setTag(R.drawable.dress_top1t);
                imgdesign1Top.setVisibility(view.VISIBLE);

                imgdesign2Top.setImageResource(R.drawable.dress_top2t);
                imgdesign2Top.setTag(R.drawable.dress_top2t);
                imgdesign2Top.setVisibility(view.VISIBLE);

                imgdesign3Top.setImageResource(R.drawable.dress_top8t);
                imgdesign3Top.setTag(R.drawable.dress_top8t);
                imgdesign3Top.setVisibility(view.VISIBLE);

                imgdesign4Top.setImageResource(R.drawable.dress_top10t);
                imgdesign4Top.setTag(R.drawable.dress_top10t);
                imgdesign4Top.setVisibility(view.VISIBLE);



            }

        });

        longSkirt.setOnClickListener(new View.OnClickListener() {//shows the dress bottoms that are long
            @Override
            public void onClick(View view) {// The function sets the long bottom designs of the dress
                imgdesign1Btm.setVisibility(view.INVISIBLE);
                imgdesign2Btm.setVisibility(view.INVISIBLE);
                imgdesign3Btm.setVisibility(view.INVISIBLE);
                imgdesign4Btm.setVisibility(view.INVISIBLE);
                imgdesign5Btm.setVisibility(view.INVISIBLE);
                imgdesign6Btm.setVisibility(view.INVISIBLE);


                imgdesign1Btm.setImageResource(R.drawable.dress_bottom1t);
                imgdesign1Btm.setTag(R.drawable.dress_bottom1t);
                imgdesign1Btm.setVisibility(view.VISIBLE);


                imgdesign2Btm.setImageResource(R.drawable.dress_bottom2t);
                imgdesign2Btm.setTag(R.drawable.dress_bottom2t);
                imgdesign2Btm.setVisibility(view.VISIBLE);

                imgdesign3Btm.setImageResource(R.drawable.dress_bottom3t);
                imgdesign3Btm.setTag(R.drawable.dress_bottom3t);
                imgdesign3Btm.setVisibility(view.VISIBLE);

                imgdesign4Btm.setImageResource(R.drawable.dress_bottom4t);
                imgdesign4Btm.setTag(R.drawable.dress_bottom4t);
                imgdesign4Btm.setVisibility(view.VISIBLE);

                imgdesign5Btm.setImageResource(R.drawable.dress_bottom8t);
                imgdesign5Btm.setTag(R.drawable.dress_bottom8t);
                imgdesign5Btm.setVisibility(view.VISIBLE);

                imgdesign6Btm.setImageResource(R.drawable.dress_bottom9t);
                imgdesign1Btm.setTag(R.drawable.dress_bottom9t);
                imgdesign6Btm.setVisibility(view.VISIBLE);



            }

        });
        shortSkirt.setOnClickListener(new View.OnClickListener() {//shows the dress bottoms that are short
            @Override
            public void onClick(View view) {// The function sets the short bottom designs of the dress

                imgdesign1Btm.setVisibility(view.INVISIBLE);
                imgdesign2Btm.setVisibility(view.INVISIBLE);
                imgdesign3Btm.setVisibility(view.INVISIBLE);
                imgdesign4Btm.setVisibility(view.INVISIBLE);
                imgdesign5Btm.setVisibility(view.INVISIBLE);
                imgdesign6Btm.setVisibility(view.INVISIBLE);

                //imgdesign1Btm.setBackgroundResource(R.drawable.btm5_button_states);
                imgdesign1Btm.setImageResource(R.drawable.dress_bottom5t);
                //imgdesign1Btm.setLayoutParams(new LinearLayout.LayoutParams(200,250));
                //imgdesign1Btm.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.btm5_button_states));
                imgdesign1Btm.setTag(R.drawable.dress_bottom5t);
                imgdesign1Btm.setVisibility(view.VISIBLE);


                imgdesign2Btm.setImageResource(R.drawable.dress_bottom6t);
                imgdesign2Btm.setTag(R.drawable.dress_bottom6t);
                imgdesign2Btm.setVisibility(view.VISIBLE);

                imgdesign3Btm.setImageResource(R.drawable.dress_bottom7t);
                imgdesign3Btm.setTag(R.drawable.dress_bottom7t);
                imgdesign3Btm.setVisibility(view.VISIBLE);

                imgdesign4Btm.setImageResource(R.drawable.dress_bottom10t);
                imgdesign4Btm.setTag(R.drawable.dress_bottom10t);
                imgdesign4Btm.setVisibility(view.VISIBLE);




            }

        });
        colorPickerBtn.setOnClickListener(new View.OnClickListener() {//On click listener of the color picker button
            @Override
            public void onClick(View view) {
                OpenColorPicker(false);
            }

        });

    }
    private void OpenColorPicker(boolean AlphaSupport)//the function opens to color picker, saves the chosen color is SP, shows it and checks if the user finished to design the dress
    {
        AmbilWarnaDialog ambilWarnaDialog=new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override

            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(DesignYourDress.this,"color picker is closed",Toast.LENGTH_LONG).show();
            }


            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                chosenColor=true;
                defaultColor=color;
                sp = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("chosen_color", color);
                editor.commit();
                chosenColorBtn.setBackgroundColor(color);
                NextActivity(chosenTop,chosenBtm,chosenColor);


            }

        });
        ambilWarnaDialog.show();



    }
    public void OnClickTopDesigns(View v)// when ImageButton is clicked the function saves the resource of the top dress image
    {
        chosenTop=true;
        sp = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = sp.edit();
        Integer resource= (Integer)((ImageButton) v).getTag();
        editor.putInt("imageTopDesign_name", resource);
        editor.commit();
        NextActivity(chosenTop,chosenBtm,chosenColor);


    }

    public void OnClickBtmDesigns(View v)// when ImageButton is clicked the function saves the resource of the button dress image+ checks if the user finished to design
    {
        chosenBtm=true;
        sp = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = sp.edit();
        Integer resource= (Integer)((ImageButton) v).getTag();
        editor.putInt("imageBtmDesign_name", resource);
        editor.commit();
        NextActivity(chosenTop,chosenBtm,chosenColor);


    }
    public void OnClickColorButtons(View v)// when one of the color buttons is clicked the function saves the name of chosen color+ checks if the user finished to design
    {
        chosenColor=true;
        sp = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = sp.edit();
        //Integer color= (Integer)((Button) v).getTag();
        String btnColor=(String)((Button)v).getTag();
        Integer color=Color.parseColor(btnColor);
        editor.putInt("chosen_color", color);
        editor.commit();
        chosenColorBtn.setBackgroundColor(color);
        NextActivity(chosenTop,chosenBtm,chosenColor);


    }

    public void NextActivity(boolean chosenTop,boolean chosenBtm, boolean chosenColor)//the function checks if the user chose all of the dress features and then allows him to proceed to the next page
    {


        if(chosenTop==true&&chosenBtm==true&&chosenColor==true)
        {
            Intent NextPageIntent = new Intent(DesignYourDress.this, PreviewActivity.class);
            startActivity(NextPageIntent);
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
                Intent NextPageIntent1 = new Intent(DesignYourDress.this, ChooseHowToDesign.class);
                this.startActivity(NextPageIntent1);
                return true;
            case R.id.viewDesigns:
                Intent NextPageIntent2 = new Intent(DesignYourDress.this, ViewPreviousDesigns.class);
                this.startActivity(NextPageIntent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }











}
