package com.example.testing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";

    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext;
    private ProgressBar mProgressBar;
    private EditText mEmail, mPassword;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mContext = signin.this;

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
         mProgressBar.setVisibility(ProgressBar.GONE);

        setupFirebaseAuth();
init();

    }
    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


            }
        };
    }
    private boolean isStringNull(String string) {

        if (string.equals("")) {
            return true;
        } else {
            return false;
        }
    }

public  void init() {
    Button btnLogin = (Button) findViewById(R.id.btn_login);
    btnLogin.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String email =mEmail.getText().toString();
                                        String pwd = mPassword.getText().toString();
                                        if (email.isEmpty()) {
                                            mEmail.setError("Please re enter email Id ");
                                            mEmail.requestFocus();
                                        }
                                        if (pwd.isEmpty()) {
                                            mPassword.setError("Enter Password ");
                                            mPassword.requestFocus();

                                        }
                                        if (email.isEmpty() && pwd.isEmpty()) {
                                            Toast.makeText(signin.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                                            ;


                                        }
                                        if (!(email.isEmpty() && pwd.isEmpty())) {
                                            mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(signin.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (!task.isSuccessful()) {

                                                        Toast.makeText(signin.this, "Login error ,please login again", Toast.LENGTH_SHORT).show();

                                                    } else {

                                                        startActivity(new Intent(signin.this, home.class));


                                                    }
                                                }
                                            });

                                        } else {

                                            Toast.makeText(signin.this, "Error Occured", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                }
    );
    TextView linkSignUp = (TextView) findViewById(R.id.link_signup);
    linkSignUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: navigating to register screen");
            Intent intent = new Intent(signin.this, register.class);
            startActivity(intent);
        }
    });
}

   /* private void init() {
        //initialize the button for logging in
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString();
                String pwd = mPassword.getText().toString();
                if (email.isEmpty()) {
                    mEmail.setError("Please re enter email Id ");
                    mEmail.requestFocus();
                }
                if (pwd.isEmpty()) {
                    mPassword.setError("Enter Password ");
                    mPassword.requestFocus();

                }
                if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(signin.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                    ;


                }
                if (!(email.isEmpty() && pwd.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(signin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {

                                Toast.makeText(signin.this, "Login error ,please login again", Toast.LENGTH_SHORT).show();

                            } else {

                                startActivity(new Intent(signin.this, home.class));


                            }
                        }
                    });

                } else {

                    Toast.makeText(signin.this, "Error Occured", Toast.LENGTH_SHORT).show();

                }
            }

        });

                }*/


    /******************************************** firebase *******************************************************/



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void onStop() {
        super.onStop();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

/************************************************************* firebase end *****************************************************************/

}


