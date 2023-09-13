package com.example.bagrutproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MyReceiver extends BroadcastReceiver {
    private ImageView iv;
   private Context c;


    public  MyReceiver(ImageView imageView, Context c)
    {
        iv = imageView;
        this.c = c;

    }

    @Override
    public void onReceive(Context context, Intent intent) {//the function creates a bitmap image and sets the imageview using the bitmap
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //Toast.makeText(c,"reached finally...",Toast.LENGTH_LONG).show();
        try {
         //   File directory = cw.getDir("dirName", Context.MODE_PRIVATE);

            FileInputStream is=(c.openFileInput("filename.jpg"));

        //    File f=new File(imgPath, "imgName.jpg");
            Bitmap b = BitmapFactory.decodeStream(is);
          //  ImageView img=(ImageView)findViewById(R.id.image);
            iv.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
       // Bitmap b = UtilFile.readFileFromInternalStorage(c,"filename.jpg");
       // iv.setImageBitmap(b);

    }
}
