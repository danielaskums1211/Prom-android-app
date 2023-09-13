package com.example.bagrutproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.System.out;

public class PreviewActivity extends AppCompatActivity {
    private ImageView topPreview;
    private ImageView btmPreview;
    private SharedPreferences sp;
    private LinearLayout imageLayout;
    private ImageView view;
    private Button uploadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        topPreview = (ImageView) findViewById(R.id.chosen_top_final);
        btmPreview = (ImageView) findViewById(R.id.chosen_btm_final);
        imageLayout = (LinearLayout) findViewById(R.id.imageLayout);
        view = (ImageView) findViewById(R.id.view);
        uploadBtn = (Button) findViewById(R.id.uploadBtn);
        sp = getSharedPreferences("MyPref", 0);


        SetImages();

        imageLayout.setDrawingCacheEnabled(true);//converting layout to imageView
        // Without it the view will have a dimension of 0,0 and the bitmap will be null

        imageLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        imageLayout.layout(0, 0, imageLayout.getMeasuredWidth(), imageLayout.getMeasuredHeight());

        imageLayout.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(imageLayout.getDrawingCache());
        imageLayout.setDrawingCacheEnabled(false); // clear drawing cache

        view.setImageBitmap(b);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadToFB();
            }
        });//uploading the image when the button is clicked
    }

    private void UploadToFB() {//the function uploads the image to Firebase and a process dialog is shown


        FirebaseStorage storage = FirebaseStorage.getInstance();

        // [START upload_create_reference]
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        Integer indexImg = sp.getInt("indexImg", 0);
        String email = sp.getString("Email", "");
        indexImg++;

        // Create a reference to "mountains.jpg"
        StorageReference dressRef = storageRef.child("dressPreview" + indexImg + ".jpg");

        // Create a reference to 'images/mountains.jpg'
        StorageReference dressImageRef = storageRef.child(email + "/dressPreview" + indexImg + ".jpg");


        // While the file names are the same, the references point to different files
        dressRef.getName().equals(dressImageRef.getName());    // true
        dressRef.getPath().equals(dressImageRef.getPath());    // false
        // [END upload_create_reference]


        // [START upload_memory]
        // Get the data from an ImageView as bytes
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = dressImageRef.putBytes(data);
        final ProgressDialog progressDialog = new ProgressDialog(this);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(PreviewActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.

                final ProgressDialog dialog = ProgressDialog.show(PreviewActivity.this, "",
                        "Uploading your image...", true);
                dialog.show();
                final Timer t1 = new Timer();
                t1.schedule(new TimerTask() {
                    public void run() {
                        dialog.dismiss(); // when the task active then close the dialog
                        t1.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                    }
                }, 2000); // after 2 second (or 2000 miliseconds), the task will be active.
                Toast.makeText(PreviewActivity.this, "Uploaded successfully!", Toast.LENGTH_SHORT).show();


            }

        });
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("indexImg", indexImg);
        editor.commit();

    }


    private void SetImages() //the function sets the images that were chosen (using sp) and fills them with the chosen color
    {
        sp = getSharedPreferences("MyPref", 0);
        Integer intTopResource = sp.getInt("imageTopDesign_name", 0);
        Integer intBtmResource = sp.getInt("imageBtmDesign_name", 0);
        Integer intChosenColor = sp.getInt("chosen_color", 0);


        topPreview.setImageResource(intTopResource);
        btmPreview.setImageResource(intBtmResource);

        topPreview.setBackgroundColor(intChosenColor);
        btmPreview.setBackgroundColor(intChosenColor);


    }

    public boolean onCreateOptionsMenu(Menu menu)//menu function
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)//the function shares the final image design and a description
    {
        int id = item.getItemId();
        if (id == R.id.share) {

/*קוד התחלתי
           Bitmap bm = ((android.graphics.drawable.BitmapDrawable) view.getDrawable()).getBitmap();
           try {
               java.io.File file = new java.io.File(getExternalCacheDir() + "/dressPreview.jpg");
               java.io.OutputStream out = new java.io.FileOutputStream(file);
               bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
               out.flush();
               out.close();
           }
           catch (Exception e) {Toast.makeText(PreviewActivity.this,e.toString(),Toast.LENGTH_LONG).show();
           }
           Intent shareIntent = new Intent();
           shareIntent.setAction(Intent.ACTION_SEND);
           shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out the dress I designed in Dress Me!");
           shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new java.io.File(getExternalCacheDir() + "/dressPreview.jpg")));
           shareIntent.setType("image/jpeg");
           shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
           startActivity(Intent.createChooser(shareIntent, "send"));
           */


            // private void shareImage(Bitmap bitmap){
            // save bitmap to cache directory
            /*
            Bitmap bitmap = ((android.graphics.drawable.BitmapDrawable) view.getDrawable()).getBitmap();

            try {
  File cachePath = new File(this.getCacheDir(), "imageview");
  cachePath.mkdirs(); // don't forget to make the directory
  FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
  stream.close();

 } catch (IOException e) {
  e.printStackTrace();
 }
 File imagePath = new File(this.getCacheDir(), "imageview");
 File newFile = new File(imagePath, "image.png");
 Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", newFile);

 if (contentUri != null) {
  Intent shareIntent = new Intent();
  shareIntent.setAction(Intent.ACTION_SEND);
  shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
  shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
  shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
  shareIntent.setType("image/png");
  startActivity(Intent.createChooser(shareIntent, "Choose an app"));
 }
}






             */




/*working
           Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                   + "/drawable/" + "ic_launcher");
           Intent shareIntent = new Intent();
           shareIntent.setAction(Intent.ACTION_SEND);
           shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello");
           shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
           shareIntent.setType("image/jpeg");
           shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
           startActivity(Intent.createChooser(shareIntent, "send"));


 */


            BitmapDrawable bitmapDrawable = (BitmapDrawable) view.getDrawable();// get the from imageview or use your drawable from drawable folder
            Bitmap bitmap1 = bitmapDrawable.getBitmap();
            String imgBitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap1, "title", null);
            Uri imgBitmapUri = Uri.parse(imgBitmapPath);
            String shareText = "Check out the dress I designed in Dress Me!";
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/jpeg");
            shareIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(shareIntent, "Send"));

        }




        return true;


    }

}



