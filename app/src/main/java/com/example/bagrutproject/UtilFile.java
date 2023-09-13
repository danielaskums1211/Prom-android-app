package com.example.bagrutproject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class UtilFile {

    public UtilFile(){

    }
    /*
    public void DownloadImageFromFB()
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

// Create a reference with an initial file path and name
        StorageReference pathReference = storageRef.child("images/imagePreview1.jpg");

// Create a reference to a file from a Google Cloud Storage URI
        StorageReference gsReference = storage.getReferenceFromUrl("gs://bagrutproject-ef8a8.appspot.com/images/imagePreview1.jpg");

// Create a reference from an HTTPS URL
// Note that in the URL, characters are URL escaped!
        StorageReference httpszzReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/b/bucket/o/images%imagePreview1.jpg");
    }*/
    public static void writeFileToInternalStorage(Context context, Bitmap bitmap, String filename)
    {

     //   SharedPreferences sp= context.getSharedPreferences("info",Context.MODE_PRIVATE);

        try {
            FileOutputStream os = (context.openFileOutput(filename,Context.MODE_PRIVATE));
            bitmap.compress(Bitmap.CompressFormat.PNG,100,os);
            //write to sharepreferance name of file
      //      SharedPreferences.Editor editor=sp.edit();
       //     editor.putString("filename",filename);
        //    editor.commit();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    public static Bitmap readFileFromInternalStorage(Context context,String filename)//the function reads the file and returns a bitmap of it
    {
        Bitmap b=null;
        try {
            InputStream in = (context.openFileInput(filename));
            b = BitmapFactory.decodeStream(in);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }
    /*
    public static ArrayList<Bitmap> getAllFils(Context context)
    {
        File mydir = context.getFilesDir();
        File lister = mydir.getAbsoluteFile();
        ArrayList<Bitmap>arraylist=new ArrayList<Bitmap>();
        for (String list : lister.list())
        {
            Toast.makeText(context,list, Toast.LENGTH_LONG).show();
            Bitmap b = readFileFromInternalStorage(context,list);
            arraylist.add(b);
        }
        return arraylist;
    }

     */

}

