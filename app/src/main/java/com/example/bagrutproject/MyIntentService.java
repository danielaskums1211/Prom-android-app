package com.example.bagrutproject;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.bagrutproject.action.FOO";
    private static final String ACTION_BAZ = "com.example.bagrutproject.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.bagrutproject.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.bagrutproject.extra.PARAM2";
    public static final String NOTIFICATION = "com.daniela.android.service.receiver";
    public static final String URL = "urlpath";
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";


    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method\

    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }
    Bitmap b;
    @Override
    protected void onHandleIntent(Intent intent) {//the function downloads the image that was uploaded to Firebase
        String path = intent.getStringExtra("path");

        try {
// Create a storage reference from our a
            FirebaseStorage storage = FirebaseStorage.getInstance();

            // [START download_create_reference]
            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();

            // Create a reference with an initial file path and name
            StorageReference pathReference = storageRef.child(path);

            File localFile = File.createTempFile("filename", "jpg");



            final long ONE_MEGABYTE = 3*1024 * 1024;
            pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    FileOutputStream os = null;
                    try {
                        os = (getApplicationContext().openFileOutput("filename.jpg", Context.MODE_PRIVATE));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
                    publishResults();

/*
                    // Data for "images/island.jpg" is returns, use this as needed
                    File photo=new File(Environment.getExternalStorageDirectory(), "photo.jpg");

                    if (photo.exists()) {
                        photo.delete();
                    }

                    try {
                        FileOutputStream fos=new FileOutputStream(photo.getPath());
                        fos.write(bytes);
                        fos.close();
                    }
                    catch (java.io.IOException e) {
                        Log.e("PictureDemo", "Exception in photoCallback", e);
                    }
                    */
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

            /*
            pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    publishResults();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle anyב errors
                    Log.d("Failure","failed to receive image");
                }
            });
*/


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void publishResults() {//the function sends a broadcast
    /*    Intent intent = new Intent();
        intent.setAction(NOTIFICATION);
        intent.setComponent(new ComponentName(getPackageName(),"com.example.bagrutproject.MyReceiver"));
        getApplicationContext().sendBroadcast(intent);
*/
        Intent intent = new Intent(MyIntentService.NOTIFICATION);
        sendBroadcast(intent);

        //sendBroadcast(intent);
    }


    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
