package com.example.bagrutproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class UploadImage extends AppCompatActivity {
    private ImageButton galleryBtn;
    private ImageButton cameraBtn;
    private ImageView chosenPhoto;
    static final int REQUEST_IMAGE_CAPTURE = 0;
    static final int GET_FROM_GALLERY = 1;
    private Uri imageUri;
    private SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        galleryBtn = (ImageButton) findViewById(R.id.gelleryBtn);
        cameraBtn = (ImageButton) findViewById(R.id.cameraBtn);
        chosenPhoto = (ImageView) findViewById(R.id.takenPhoto);
        sp = getSharedPreferences("MyPref",0);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// when the camera button is clicked the camera opens
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//open camera
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }

        });
        galleryBtn.setOnClickListener(new View.OnClickListener() {//open gallery
            @Override
            public void onClick(View view) {//when the gallery button is clicked the gallery opens
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);


            }

        });


    }


    @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)//the function sets the image in the activity, uploads it to Firebase and a process dialog is shown
        {
            super.onActivityResult(requestCode,resultCode,data);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            Integer indexImg=sp.getInt("indexImg",0);
            String email=sp.getString("Email","");

            indexImg++;

            // [START upload_create_reference]
            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();

            // Create a reference to "dressPreview+index.jpg"
            StorageReference dressRef = storageRef.child("dressPreview"+indexImg+".jpg");

            // Create a reference to 'email/dressPreview+index.jpg'
            StorageReference dressImageRef = storageRef.child(email+"/dressPreview"+indexImg+".jpg");
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("indexImg",indexImg);
            editor.commit();

          //  int i = sp.getInt("indexImg",0);
            //Toast.makeText(this, "" +i,Toast.LENGTH_LONG).show();


            // While the file names are the same, the references point to different files
            dressRef.getName().equals(dressImageRef.getName());    // true
            dressRef.getPath().equals(dressImageRef.getPath());    // false
            // [END upload_create_reference]
                //get captured image
            if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
            {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                chosenPhoto.setImageBitmap(imageBitmap);
                chosenPhoto.setDrawingCacheEnabled(true);
                chosenPhoto.buildDrawingCache();
                Bitmap bitmap1 = ((BitmapDrawable) chosenPhoto.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data1 = baos.toByteArray();

                UploadTask uploadTask = dressImageRef.putBytes(data1);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(UploadImage.this, "Upload failed", Toast.LENGTH_SHORT).show();
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        final ProgressDialog dialog = ProgressDialog.show(UploadImage.this, "",
                                "Uploading your image...", true);
                        dialog.show();
                        final Timer t = new Timer();
                        t.schedule(new TimerTask() {
                            public void run() {
                                dialog.dismiss(); // when the task active then close the dialog
                                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                            }
                        }, 2000); // after 2 second (or 2000 miliseconds), the task will be active.
                        Toast.makeText(UploadImage.this, "Uploaded successfully!", Toast.LENGTH_SHORT).show();
                        // ...
                    }
                });

            }
            if(requestCode==GET_FROM_GALLERY && resultCode == RESULT_OK) {
              imageUri=data.getData();
              try {
                  Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                  chosenPhoto.setImageBitmap(bitmap);
                  chosenPhoto.setDrawingCacheEnabled(true);
                  chosenPhoto.buildDrawingCache();
                  Bitmap bitmap2 = ((BitmapDrawable) chosenPhoto.getDrawable()).getBitmap();
                  ByteArrayOutputStream baos = new ByteArrayOutputStream();
                  bitmap2.compress(Bitmap.CompressFormat.PNG, 100, baos);
                  byte[] data2 = baos.toByteArray();

                  UploadTask uploadTask = dressImageRef.putBytes(data2);
                  uploadTask.addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception exception) {
                          Toast.makeText(UploadImage.this, "Upload failed", Toast.LENGTH_SHORT).show();
                          // Handle unsuccessful uploads
                      }
                  }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                          // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                          final ProgressDialog dialog = ProgressDialog.show(UploadImage.this, "",
                                  "Uploading your image...", true);
                          dialog.show();
                          final Timer t = new Timer();
                          t.schedule(new TimerTask() {
                              public void run() {
                                  dialog.dismiss(); // when the task active then close the dialog
                                  t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                              }
                          }, 2000); // after 2 second (or 2000 miliseconds), the task will be active.
                          Toast.makeText(UploadImage.this, "Uploaded successfully!", Toast.LENGTH_SHORT).show();

                      }
                  });
              }
              catch (IOException e)
              {
                  e.printStackTrace();
              }
            }


    }
}
