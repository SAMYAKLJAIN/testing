package com.example.testing;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class profile extends AppCompatActivity {
    TextView fullname,email,phone,location;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private DocumentReference myRef;
String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
          fullname=findViewById(R.id.name);
          email=findViewById(R.id.email);
          phone=findViewById(R.id.contactvalue);
          location=findViewById(R.id.location);
        mAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        userid=mAuth.getCurrentUser().getUid();
        myRef=fstore.collection("users").document(userid);
        myRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                phone.setText(documentSnapshot.getString("phone"));
                fullname.setText(documentSnapshot.getString("Fullname"));
                email.setText(documentSnapshot.getString("email"));
                location.setText(documentSnapshot.getString("location"));


            }
        });


    }
}
