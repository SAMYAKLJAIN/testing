package com.example.testing;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class imageupload extends AppCompatActivity {
    private ImageView userImage;
    private Button submit;
    private ProgressDialog progressDialog;
    private Uri imageUri ;
    private StorageReference photoRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String user_id;
    private Bitmap compressed;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageupload);
        mAuth = FirebaseAuth.getInstance();


        userImage = findViewById(R.id.user_image);
        submit=findViewById(R.id.submit);

        user_id = mAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        photoRef= FirebaseStorage.getInstance().getReference();

        userImage.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                 if (ContextCompat.checkSelfPermission(imageupload.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                                                     Toast.makeText(imageupload.this, "Permission Denied", Toast.LENGTH_LONG).show();
                                                     ActivityCompat.requestPermissions(imageupload.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                                                 } else {
                                                   choseImage();

                                                 }

                                             } else {

                                                 choseImage();

                                             }

                                         }

                                     }

        );

        submit.setOnClickListener(new View.OnClickListener() {


            @Override


            public void onClick(View view) {

               // progressDialog.setMessage("Storing Data...");


                //progressDialog.show();







                if(imageUri!=null){

System.out.println(imageUri);
                    File newFile = new File(imageUri.getPath());




                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


                    compressed.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                   // Bitmap bitmap = ((BitmapDrawable) userImage.getDrawable()).getBitmap();

                    byte[] thumbData = byteArrayOutputStream.toByteArray();


                    UploadTask image_path = photoRef.child("user_image").child(user_id + ".jpg").putBytes(thumbData);





                    image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {


                        @Override


                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                            if (task.isSuccessful()) {


                                storeData(task);


                            } else {


                                String error = task.getException().getMessage();


                                Toast.makeText(imageupload.this, "(IMAGE Error) : " + error, Toast.LENGTH_LONG).show();


                                progressDialog.dismiss();


                            }


                        }


                    });




                }

            }
        });
    }




    private void storeData(Task<UploadTask.TaskSnapshot> task) {


        Uri downloadUrl;


        if (task != null) {


            photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Uri downloadUrl = uri;
                    Toast.makeText(getBaseContext(), "Upload success! URL - " + downloadUrl.toString() , Toast.LENGTH_SHORT).show();
                    Map<String, String> userData = new HashMap<>();


                    userData.put("userImage", downloadUrl.toString());


                    firebaseFirestore.collection("Users").document(user_id).set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {


                        @Override


                        public void onComplete(@NonNull Task<Void> task) {


                            if (task.isSuccessful()) {


                                progressDialog.dismiss();


                                Toast.makeText(imageupload.this, "User Data is Stored Successfully", Toast.LENGTH_LONG).show();


                                Intent mainIntent = new Intent(imageupload.this, home.class);


                                startActivity(mainIntent);

                                finish();


                            } else {


                                String error = task.getException().getMessage();


                                Toast.makeText(imageupload.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();


                            }


                            progressDialog.dismiss();


                        }

                    });

                }
            });

        } else {


            downloadUrl = imageUri;


        }


    }
        private void choseImage () {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(imageupload.this);
        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){


            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK ) {

                    imageUri = result.getUri();
                    userImage.setImageURI(imageUri);
System.out.println(imageUri);

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                    Exception error = result.getError();

                }
            }

        }


}













