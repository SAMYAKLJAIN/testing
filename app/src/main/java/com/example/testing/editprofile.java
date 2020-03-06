package com.example.testing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class editprofile extends AppCompatActivity {
    private Button mButtonChooseImage,save;
    TextView fullname, email, phone, location;
    ImageView im, editim;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private DocumentReference myRef;
    private String image;
    String userid,name;
EditText updatename;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;

    private StorageReference mStorageReference;
    private String userID;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

save=findViewById(R.id.saving);
        fullname = findViewById(R.id.usernames);
        location = findViewById(R.id.location);
        im = findViewById(R.id.profile);
        editim = findViewById(R.id.edit);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(editprofile.this, upload.class));
            }
        });

save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        savedata();
    }
});

        userid=mAuth.getCurrentUser().getUid();
        myRef=fstore.collection("users").document(userid);
        myRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                fullname.setText(documentSnapshot.getString("Fullname"));
                location.setText(documentSnapshot.getString("location"));
                image=documentSnapshot.getString("userImage");

                Glide.with(editprofile.this).load(image).into(im);


            }
        });












    }






    private void savedata() {

String updatename =fullname.getText().toString();
String updatelocation=location.getText().toString();
       /* userID = mAuth.getCurrentUser().getUid();
        DocumentReference dref=fstore.collection("users").document(userID);
        Map<String,String> user =new HashMap<>();
*/


        userID = mAuth.getCurrentUser().getUid();
        DocumentReference dref=fstore.collection("users").document(userID);
        Map<String,String> user =new HashMap<>();

        dref.update("Fullname",updatename,"location",updatelocation).addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override


            public void onComplete(@NonNull Task<Void> task) {


                if (task.isSuccessful()) {


                    Toast.makeText(editprofile.this, "User Data Updated", Toast.LENGTH_LONG).show();


                    Intent mainIntent = new Intent(editprofile.this, profile.class);


                    startActivity(mainIntent);

                    finish();


                } else {


                    String error = task.getException().getMessage();


                    Toast.makeText(editprofile.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();


                }


            }


/*

        firebaseFirestore.collection("users").document(userID).update("Fullname",updatename,"location",updatelocation).addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override


            public void onComplete(@NonNull Task<Void> task) {


                if (task.isSuccessful()) {


                    Toast.makeText(editprofile.this, "User Data is Stored Successfully", Toast.LENGTH_LONG).show();


                    Intent mainIntent = new Intent(editprofile.this, profile.class);


                    startActivity(mainIntent);

                    finish();


                } else {


                    String error = task.getException().getMessage();


                    Toast.makeText(editprofile.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();


                }


            }


        });
  */
    });









}}