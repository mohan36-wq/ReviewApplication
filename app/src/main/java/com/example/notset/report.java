package com.example.notset;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class report extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    Button bt_photo, bt_submit;
    EditText ed_title, ed_description;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;

    DatabaseReference mDatabaseRef;
    DatabaseReference mDatabaseRefwi;

    //private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("reports");
        mDatabaseRefwi = FirebaseDatabase.getInstance().getReference("uploads");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        bt_photo = (Button) findViewById(R.id.button_choose_image);
        bt_submit = (Button) findViewById(R.id.button2);
        ed_title = (EditText) findViewById(R.id.editText);
        ed_description = (EditText) findViewById(R.id.editText2);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);

        bt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        bt_submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        addReport();
                }

        });

    }

    private void addReport(){

        String title = ed_title.getText().toString().trim();
        String description = ed_description.getText().toString().trim();

        if(mImageUri != null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
            + "." + getFileExtension(mImageUri));

            //mUploadTask =
                    fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(report.this, "Report Submitted", Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(ed_title.getText().toString().trim(),
                                    ed_description.getText().toString().trim(),
                                    taskSnapshot.getStorage().getDownloadUrl().toString());

                            String uploadId = mDatabaseRefwi.push().getKey();
                            mDatabaseRefwi.child(uploadId).setValue(upload);

                            ed_title.setText("");

                            ed_description.setText("");

                            mImageView.setImageResource(android.R.color.transparent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(report.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }else if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = mDatabaseRef.push().getKey();

            //creating an Artist Object
            allreports allreport = new allreports(id, title, description);

            //Saving the Artist
            mDatabaseRef.child(id).setValue(allreport);

            //setting edittext to blank again
            ed_title.setText("");

            ed_description.setText("");

            //mImageview.setImageResource(android.R.color.transparent);

            //displaying a success toast
            Toast.makeText(this, "Report Submitted", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please provide all details", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(report.this, MainActivity.class));
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() != null){
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
