package com.example.testing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class profile extends AppCompatActivity {
    TextView fullname,email,phone,location;
    ImageView im;
    Button editim;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private DocumentReference myRef;
    private String image;
String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
          fullname=findViewById(R.id.name);
          email=findViewById(R.id.email);
          phone=findViewById(R.id.contactvalue);
          location=findViewById(R.id.location);
          im=findViewById(R.id.profile);
          editim=findViewById(R.id.edit);
        mAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        editim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this,editprofile.class));
            }
        });

        userid=mAuth.getCurrentUser().getUid();
        myRef=fstore.collection("users").document(userid);
        myRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                phone.setText(documentSnapshot.getString("phone"));
                fullname.setText(documentSnapshot.getString("Fullname"));
                email.setText(documentSnapshot.getString("email"));
                location.setText(documentSnapshot.getString("location"));
image=documentSnapshot.getString("userImage");

                Glide.with(profile.this).load(image).into(im);


            }
        });


    }
}
